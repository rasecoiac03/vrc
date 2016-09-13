package com.caio.vrc.module;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import com.caio.vrc.cache.CacheInterceptor;
import com.caio.vrc.cache.DataCache;
import com.caio.vrc.cache.KeepCached;
import com.caio.vrc.cache.impl.Redis;
import com.caio.vrc.cache.impl.RedisDataCacheImpl;
import com.caio.vrc.service.PropertiesService;
import com.caio.vrc.service.impl.PropertiesServiceImpl;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		// Cache
		bind(Redis.class).toInstance(new Redis());
		bind(DataCache.class).to(RedisDataCacheImpl.class);
		final CacheInterceptor cacheInterceptor = new CacheInterceptor();
		requestInjection(cacheInterceptor);
		bindInterceptor(any(), annotatedWith(KeepCached.class), cacheInterceptor);

		// Service
		bind(PropertiesService.class).to(PropertiesServiceImpl.class);
	}

}
