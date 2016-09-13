package com.caio.vrc.cache.impl;

import static com.caio.vrc.VrcProperty.CONNECTION_POOL_MAX_WAIT_MS;
import static com.caio.vrc.VrcProperty.CONNECTION_POOL_TEST_ON_BORROW;
import static com.caio.vrc.VrcProperty.CONNECT_TIMEOUT_MS;
import static com.caio.vrc.VrcProperty.MASTER_CONNECTION_POOL_SIZE;
import static com.caio.vrc.VrcProperty.MASTER_SERVER;
import static com.caio.vrc.VrcProperty.SLAVE_CONNECTION_POOL_SIZE;
import static com.caio.vrc.VrcProperty.SLAVE_SERVERS;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Class responsible for holding Redis master and slave pools
 * 
 * @author caio.silva
 *
 */
public class Redis {

	private static final Logger LOGGER = LoggerFactory.getLogger(Redis.class);

	private static final int DEFAULT_REDIS_PORT = 6379;
	private static final JedisPool masterJedisPool = createMasterJedisPool();
	private static final JedisPool[] slaveJedisPools = createSlaveJedisPools();
	private static final AtomicInteger indexSlave = new AtomicInteger(-1);

	public static JedisPool createMasterJedisPool() {
		return new JedisPoolBuilder() //
				.withMinIdle(MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxIdle(MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxActive(MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxWait(CONNECTION_POOL_MAX_WAIT_MS.getIntegerValue()) //
				.withTestOnBorrow(CONNECTION_POOL_TEST_ON_BORROW.getBooleanValue()) //
				.withConnectionTimeoutMs(CONNECT_TIMEOUT_MS.getIntegerValue()) //
				.withServer(MASTER_SERVER.getStringValue()) //
				.withPort(DEFAULT_REDIS_PORT) //
				.build();
	}

	public static JedisPool[] createSlaveJedisPools() {
		final String[] slaveServers = SLAVE_SERVERS.getStringValue().split(",");
		final JedisPool[] slavePools = new JedisPool[(slaveServers.length)];
		int i = 0;
		for (String slaveServer : slaveServers) {
			slavePools[i++] = new JedisPoolBuilder() //
					.withMinIdle(SLAVE_CONNECTION_POOL_SIZE.getIntegerValue()) //
					.withMaxIdle(SLAVE_CONNECTION_POOL_SIZE.getIntegerValue()) //
					.withMaxActive(SLAVE_CONNECTION_POOL_SIZE.getIntegerValue()) //
					.withMaxWait(CONNECTION_POOL_MAX_WAIT_MS.getIntegerValue()) //
					.withTestOnBorrow(CONNECTION_POOL_TEST_ON_BORROW.getBooleanValue()) //
					.withConnectionTimeoutMs(CONNECT_TIMEOUT_MS.getIntegerValue()) //
					.withServer(slaveServer) //
					.withPort(DEFAULT_REDIS_PORT) //
					.build();
		}

		return slavePools;
	}

	public Jedis getJedisWrite() {
		try {
			return masterJedisPool.getResource();
		} catch (Exception e) {
			LOGGER.error("error getting redis connection, message: {}", e.getMessage());
			return null;
		}
	}

	public Jedis getJedisRead() {
		try {
			return getSlaveResource();
		} catch (Exception e) {
			LOGGER.error("error getting redis connection, message: {}", e.getMessage());
			return null;
		}
	}

	private Jedis getSlaveResource() {
		try {
			final int i = Math.abs(indexSlave.incrementAndGet() % slaveJedisPools.length);
			return slaveJedisPools[i].getResource();
		} catch (Exception e) {
			LOGGER.error("error getting redis connection from the slave pool, message {}", e.getMessage());
			return null;
		}
	}

	public static class Util {

		public static byte[] serialize(Serializable o) {
			if (o == null) {
				throw new IllegalArgumentException();
			}
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); //
					ObjectOutputStream os = new ObjectOutputStream(bos)) {
				os.writeObject(o);
				return bos.toByteArray();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@SuppressWarnings("unchecked")
		public static <T> T deserialize(byte[] in) {
			if (in == null) {
				throw new IllegalArgumentException();
			}
			try (ByteArrayInputStream bis = new ByteArrayInputStream(in); //
					ObjectInputStream is = new ObjectInputStream(bis)) {
				return (T) is.readObject();
			} catch (IOException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

	}

}
