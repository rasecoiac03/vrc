package com.caio.vrc;

public enum VrcProperty {

	// GENERAL
	MODE_IMPL_PROPERTIES("vrc.mode.impl.properties", "FILE"), // FILE, MONGODB
	FILE_PROPERTIES("vrc.file.properties", "src/main/resources/properties.json"), //
	FILE_PROVINCES("vrc.file.provinces", "src/main/resources/provinces.json"), //
	NUMERIC_PATTERN("vrc.numeric.pattern", "[0-9]+"), //

	// CACHE
	CACHE_ENABLED("vrc.cache.enabled", "true"), //
	MASTER_SERVER("vrc.cache.master.server", "localhost"), //
	MASTER_CONNECTION_POOL_SIZE("vrc.cache.master.connection.pool.size", "50"), //
	SLAVE_SERVERS("vrc.cache.slave.servers", "localhost"), //
	SLAVE_CONNECTION_POOL_SIZE("vrc.cache.slave.connection.pool.size", "50"), //
	CONNECT_TIMEOUT_MS("vrc.cache.connect.timeout", "300"), //
	DEFAULT_TTL_SECONDS("vrc.cache.default.ttl", "300"), //
	CONNECTION_POOL_MAX_WAIT_MS("vrc.cache.connection.pool.max.wait", "100"), //
	CONNECTION_POOL_TEST_ON_BORROW("vrc.cache.connection.pool.test.on.borrow", "false"), //

	// MongoDB
	MONGODB_HOST("vrc.mongodb.host", "127.0.0.1"), //
	MONGODB_PORT("vrc.mongodb.port", "27017"), //
	MONGODB_DATABASE("vrc.mongodb.database", "vrc"), //
	;

	private final String key;
	private final String defaultValue;

	private VrcProperty(String key, String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}

	public String getKey() {
		return key;
	}

	public String getStringValue() {
		return System.getProperty(key, defaultValue);
	}

	public Integer getIntegerValue() {
		return Integer.valueOf(getStringValue());
	}

	public Long getLongValue() {
		return Long.valueOf(getStringValue());
	}

	public Boolean getBooleanValue() {
		return Boolean.valueOf(getStringValue());
	}

}
