package com.caio.vrc.cache;

import static com.caio.vrc.VrcProperty.CACHE_ENABLED;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.caio.vrc.service.impl.PropertiesServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CacheInterceptorTest {

	@Mock
	private DataCache cache;

	@Mock
	private MethodInvocation invocation;

	private CacheInterceptor interceptor;

	@Before
	public void setup() {
		interceptor = new CacheInterceptor(cache);
	}

	@Test
	public void shouldNotTryCachingMethodResponse() throws Throwable {
		System.setProperty(CACHE_ENABLED.getKey(), "false");

		interceptor.invoke(invocation);

		verify(cache, never()).get(anyString());
		verify(cache, never()).put(anyString(), any(Serializable.class), anyInt());
		verify(invocation, times(1)).proceed();
	}

	@Test
	public void shouldReturnCachedObject() throws Throwable {
		System.setProperty(CACHE_ENABLED.getKey(), "true");

		Method method = PropertiesServiceImpl.class.getMethod("get", int.class);
		when(invocation.getMethod()).thenReturn(method);
		when(invocation.getArguments()).thenReturn(new Integer[] { 123 });
		when(cache.get(anyString())).thenReturn("YO");

		interceptor.invoke(invocation);

		verify(cache).get(anyString());
		verify(cache, never()).put(anyString(), any(Serializable.class), anyInt());
		verify(invocation, never()).proceed();
	}

	@Test
	public void shouldPutObjetInCache() throws Throwable {
		System.setProperty(CACHE_ENABLED.getKey(), "true");

		Method method = PropertiesServiceImpl.class.getMethod("get", int.class);
		when(invocation.getMethod()).thenReturn(method);
		when(invocation.getArguments()).thenReturn(new Integer[] { 123 });
		when(cache.get(anyString())).thenReturn(null);
		when(invocation.proceed()).thenReturn("Alou");

		interceptor.invoke(invocation);

		verify(cache).get(anyString());
		verify(cache).put(anyString(), any(Serializable.class), any(int.class));
		verify(invocation, times(1)).proceed();
	}

	@Test
	public void shouldReturnIfCacheGetFail() throws Throwable {
		System.setProperty(CACHE_ENABLED.getKey(), "true");
		final String value = "Alou";

		Method method = PropertiesServiceImpl.class.getMethod("get", int.class);
		when(invocation.getMethod()).thenReturn(method);
		when(invocation.getArguments()).thenReturn(new Integer[] { 123 });
		doThrow(new RuntimeException()).when(cache).get(anyString());
		when(invocation.proceed()).thenReturn(value);

		Object result = interceptor.invoke(invocation);

		assertEquals(value, result);
		verify(cache).get(anyString());
		verify(cache, never()).put(anyString(), any(Serializable.class), any(int.class));
		verify(invocation, times(1)).proceed();
	}

}
