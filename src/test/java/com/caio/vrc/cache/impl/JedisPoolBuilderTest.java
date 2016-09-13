package com.caio.vrc.cache.impl;

import static com.caio.vrc.VrcProperty.CONNECTION_POOL_MAX_WAIT_MS;
import static com.caio.vrc.VrcProperty.CONNECTION_POOL_TEST_ON_BORROW;
import static com.caio.vrc.VrcProperty.CONNECT_TIMEOUT_MS;
import static com.caio.vrc.VrcProperty.MASTER_CONNECTION_POOL_SIZE;
import static com.caio.vrc.VrcProperty.MASTER_SERVER;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import redis.clients.jedis.JedisPool;

public class JedisPoolBuilderTest {

	private static final Integer DEFAULT_REDIS_PORT = 1234;
	private final JedisPoolBuilder poolBuilder = new JedisPoolBuilder();

	@Test
	public void shouldCreateJedisPool() {
		JedisPool pool = poolBuilder.withMinIdle(MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxIdle(MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxActive(MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxWait(CONNECTION_POOL_MAX_WAIT_MS.getIntegerValue()) //
				.withTestOnBorrow(CONNECTION_POOL_TEST_ON_BORROW.getBooleanValue()) //
				.withConnectionTimeoutMs(CONNECT_TIMEOUT_MS.getIntegerValue()) //
				.withServer(MASTER_SERVER.getStringValue()) //
				.withPort(DEFAULT_REDIS_PORT) //
				.build();
		assertNotNull(pool);
	}

}
