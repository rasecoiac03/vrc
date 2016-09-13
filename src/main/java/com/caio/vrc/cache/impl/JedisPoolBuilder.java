package com.caio.vrc.cache.impl;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Class responsible for building pool {@link redis.clients.jedis.JedisPool}
 * 
 * @author caio.silva
 *
 */
public class JedisPoolBuilder {

	private Integer minIdle;
	private Integer maxIdle;
	private Integer maxActive;
	private Integer maxWait;
	private boolean testOnBorrow;
	private Integer connectionTimeoutMs;
	private String server;
	private Integer port;
	private String password;

	/**
	 * Build {@link JedisPool} instance
	 * 
	 * @return {@link redis.clients.jedis.JedisPool}
	 */
	public JedisPool build() {
		final JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMaxTotal(maxActive);
		poolConfig.setMaxWaitMillis(maxWait);
		poolConfig.setTestOnBorrow(testOnBorrow);
		poolConfig.setJmxEnabled(false);
		return new JedisPool(poolConfig, server, port, connectionTimeoutMs,
				password);
	}

	public JedisPoolBuilder withMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
		return this;
	}

	public JedisPoolBuilder withMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
		return this;
	}

	public JedisPoolBuilder withMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
		return this;
	}

	public JedisPoolBuilder withMaxWait(int maxWait) {
		this.maxWait = maxWait;
		return this;
	}

	public JedisPoolBuilder withTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
		return this;
	}

	public JedisPoolBuilder withConnectionTimeoutMs(Integer connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
		return this;
	}

	public JedisPoolBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public JedisPoolBuilder withServer(String server) {
		this.server = server;
		return this;
	}

	public JedisPoolBuilder withPort(Integer port) {
		this.port = port;
		return this;
	}

}
