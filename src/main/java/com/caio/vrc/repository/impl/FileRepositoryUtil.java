package com.caio.vrc.repository.impl;

import static com.caio.vrc.VrcProperty.FILE_PROPERTIES;
import static com.caio.vrc.VrcProperty.FILE_PROVINCES;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caio.vrc.domain.Property;
import com.caio.vrc.exception.VrcInternalErrorException;
import com.caio.vrc.repository.impl.FilePropertyRepositoryImpl.Properties;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class FileRepositoryUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileRepositoryUtil.class);

	private static final Gson gson = new Gson();
	private static final Type PROPERTY_TYPE = new TypeToken<Properties>() {
	}.getType();
	private static final Type PROVINCES_TYPE = new TypeToken<Map<String, Object>>() {
	}.getType();

	public static AtomicLong getSequence() {
		long maxId = getProperties().getProperties().parallelStream().mapToLong(Property::getId).max().getAsLong();
		return new AtomicLong(maxId);
	}

	public static Properties getProperties() {
		return (Properties) getProperties(FILE_PROPERTIES.getStringValue(), PROPERTY_TYPE);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getProvinces() {
		return (Map<String, Object>) getProperties(FILE_PROVINCES.getStringValue(), PROVINCES_TYPE);
	}

	public static Object getProperties(String filePath, Type type) {
		JsonReader reader = null;
		try {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(new FileReader(filePath));
			return gson.fromJson(element, type);
		} catch (FileNotFoundException e) {
			String message = String.format("problem reading file %s", filePath);
			LOGGER.error(e.getMessage());
			throw new VrcInternalErrorException(e, message);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
				throw new VrcInternalErrorException(e);
			}
		}
	}

}
