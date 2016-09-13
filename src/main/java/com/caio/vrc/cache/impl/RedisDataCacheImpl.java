package com.caio.vrc.cache.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caio.vrc.cache.DataCache;

import redis.clients.jedis.Jedis;

/**
 * Redis cache implementation of {@link com.caio.vrc.cache.DataCache} which provides few redis operations
 * 
 * @author caio.silva
 * @see {@link DataCache}
 */
@Singleton
public class RedisDataCacheImpl implements DataCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisDataCacheImpl.class);
	private static final String SET_SUCCESS = "OK";
	private final Redis redis;

	@Inject
	public RedisDataCacheImpl(Redis redis) {
		this.redis = redis;
	}

	@Override
	public <T extends Serializable> T get(String key) {
		final Jedis slave = redis.getJedisRead();
		if (slave == null) {
			return null;
		}
		try {
			LOGGER.debug("getting value with key {}", key);
			final byte[] value = slave.get(key.getBytes());
			if (value == null) {
				return null;
			}
			return Redis.Util.deserialize(value);
		} catch (Exception e) {
			LOGGER.error("error getting value, message {}", e.getMessage());
			return null;
		} finally {
			slave.close();
		}
	}

	@Override
	public void remove(String key) {
		final Jedis jedis = redis.getJedisWrite();
		if (jedis == null) {
			return;
		}
		try {
			jedis.del(key);
		} catch (Exception e) {
			LOGGER.error("error removing value, message: {}", e.getMessage());
		} finally {
			jedis.close();
		}
	}

	@Override
	public boolean put(String key, Serializable value, int seconds) {
		final Jedis jedis = redis.getJedisWrite();
		if (jedis == null) {
			return false;
		}
		boolean success = false;
		try {
			final String result = jedis.set(key.getBytes(), Redis.Util.serialize(value), "NX".getBytes(),
					"EX".getBytes(),
					seconds);
			success = (SET_SUCCESS.equals(result));
			LOGGER.debug("key: {}, value: {}, seconds: {}, success: {}", key, value, seconds, success);
		} catch (Exception e) {
			LOGGER.error("error putting key: {}, error: {}", key, e.getMessage());
		} finally {
			jedis.close();
		}
		return success;
	}

}
