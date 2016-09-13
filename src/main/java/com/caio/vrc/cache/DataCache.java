package com.caio.vrc.cache;

import java.io.Serializable;

/**
 * Interface which define cache operations methods.
 * 
 * @author caio.silva
 *
 */
public interface DataCache {

	/**
	 * Get serializable object from cache
	 * 
	 * @param key
	 *            to search in cache
	 * @return object which extends Serializable
	 * 
	 */
	<T extends Serializable> T get(String key);

	/**
	 * Remove object from cache
	 * 
	 * @param key
	 *            to remove from cache
	 */
	void remove(String key);

	/**
	 * Put serializable object in cache
	 * 
	 * @param key
	 *            to put in redis
	 * @param value
	 *            serializable object
	 * @param seconds
	 *            time to expire
	 * 
	 * @return boolean - true if operation was successful
	 * 
	 */
	boolean put(String key, Serializable value, int seconds);

}
