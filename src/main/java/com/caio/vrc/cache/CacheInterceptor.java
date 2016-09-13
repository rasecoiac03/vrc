package com.caio.vrc.cache;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.inject.Inject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.caio.vrc.VrcProperty;

/**
 * Interceptor which checks if method is cacheable or not and try to cache answer if so.
 * 
 * @author caio.silva
 *
 */
public class CacheInterceptor implements MethodInterceptor {

	@Inject
	private DataCache cache;

	public CacheInterceptor() {
	}

	@Inject
	public CacheInterceptor(DataCache cache) {
		this.cache = cache;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object object = null;
		if (VrcProperty.CACHE_ENABLED.getBooleanValue()) {
			try {
				Method method = invocation.getMethod();
				final String methodName = method.toString();
				final String arguments = arguments(invocation.getArguments());
				final String key = String.format("%s-%s", methodName, arguments);
				final Serializable cached = cache.get(key);
				if (cached != null) {
					return cached;
				}
				object = invocation.proceed();
				final Serializable serializable = (Serializable) object;
				if (serializable != null) {
					cache.put(key, serializable, method.getAnnotation(KeepCached.class).expire());
				}
			} catch (Exception e) {
				// do nothing, return the object or invoke the method
			}
		}
		return object != null ? object : invocation.proceed();
	}

	private String arguments(Object[] objects) {
		final StringBuilder sb = new StringBuilder();
		for (Object obj : objects) {
			sb.append(obj.toString());
		}
		return sb.toString();
	}

}
