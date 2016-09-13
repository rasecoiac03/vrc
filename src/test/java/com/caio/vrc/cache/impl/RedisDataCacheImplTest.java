package com.caio.vrc.cache.impl;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.Assert;
import redis.clients.jedis.Jedis;

@RunWith(MockitoJUnitRunner.class)
public class RedisDataCacheImplTest {

	private static final String DEFAULT_KEY = "key";

	@Mock
	private Redis redis;

	@Mock
	private Jedis jedis;

	private RedisDataCacheImpl impl;

	@Before
	public void setup() {
		impl = new RedisDataCacheImpl(redis);
	}

	@Test
	public void shouldGetNullWhenHasNoJedisObject() {
		when(redis.getJedisRead()).thenReturn(null);

		Serializable serializable = impl.get(DEFAULT_KEY);

		assertNull(serializable);

		verify(jedis, never()).get(any(byte[].class));
		verify(jedis, never()).close();
	}

	@Test
	public void shouldGetNullWhenHasNoObjectInRedis() {
		when(redis.getJedisRead()).thenReturn(jedis);
		when(jedis.get(any(byte[].class))).thenReturn(null);

		Serializable serializable = impl.get(DEFAULT_KEY);

		assertNull(serializable);

		verify(jedis).get(any(byte[].class));
		verify(jedis).close();
	}

	@Test
	public void shouldReturnNullWhenJedisThrowAnExceptionTryingGetObjectFromRedis() {
		when(redis.getJedisWrite()).thenReturn(jedis);
		doThrow(new RuntimeException()).when(jedis).get(any(byte[].class));

		Serializable serializable = impl.get(DEFAULT_KEY);

		assertNull(serializable);
		verify(jedis, never()).get(any(byte[].class));
		verify(jedis, never()).close();
	}

	@Test
	public void shouldGetObjectFromRedis() {
		when(redis.getJedisRead()).thenReturn(jedis);
		when(jedis.get(any(byte[].class))).thenReturn(Redis.Util.serialize("YO"));

		Serializable serializable = impl.get(DEFAULT_KEY);

		assertNotNull(serializable);

		verify(jedis).get(any(byte[].class));
		verify(jedis).close();
	}

	@Test
	public void shouldDoNothingWhenTryingRemoveAndHasNoJedisObject() {
		when(redis.getJedisWrite()).thenReturn(null);

		impl.remove(DEFAULT_KEY);

		verify(jedis, never()).del(any(String.class));
		verify(jedis, never()).close();
	}

	@Test
	public void shouldDoNothingWhenJedisThrowAnExceptionTryingRemoveObjectFromRedis() {
		when(redis.getJedisWrite()).thenReturn(jedis);
		doThrow(new RuntimeException()).when(jedis).del(any(String.class));

		impl.remove(DEFAULT_KEY);

		verify(jedis).del(any(String.class));
		verify(jedis).close();
	}

	@Test
	public void shouldRemoveObjectFromRedis() {
		when(redis.getJedisWrite()).thenReturn(jedis);

		impl.remove(DEFAULT_KEY);

		verify(jedis).del(any(String.class));
		verify(jedis).close();
	}

	@Test
	public void shouldDoNothingWhenTryingPutObjectAndHasNoJedisObject() {
		when(redis.getJedisWrite()).thenReturn(null);

		boolean put = impl.put(DEFAULT_KEY, new String("123"), 1);

		verify(jedis, never()).set(any(byte[].class), any(byte[].class), any(byte[].class), any(byte[].class),
				anyInt());
		assertFalse(put);
	}

	@Test
	public void shouldDoNothingWhenJedisThrowAnExceptionTryingPutObjectInRedis() {
		when(redis.getJedisWrite()).thenReturn(jedis);
		doThrow(new RuntimeException()).when(jedis).set(any(byte[].class), any(byte[].class), any(byte[].class),
				any(byte[].class), anyInt());

		boolean put = impl.put(DEFAULT_KEY, new String("123"), 1);

		assertFalse(put);
		verify(jedis).set(any(byte[].class), any(byte[].class), any(byte[].class), any(byte[].class), anyInt());
		verify(jedis).close();
	}

	@Test
	public void shouldPutObjectInRedis() {
		when(redis.getJedisWrite()).thenReturn(jedis);
		when(jedis.set(any(byte[].class), any(byte[].class), any(byte[].class),
				any(byte[].class), anyInt())).thenReturn("OK");

		boolean put = impl.put(DEFAULT_KEY, new String("123"), 1);

		assertTrue(put);
		verify(jedis).set(any(byte[].class), any(byte[].class), any(byte[].class), any(byte[].class), anyInt());
		verify(jedis).close();
	}

}
