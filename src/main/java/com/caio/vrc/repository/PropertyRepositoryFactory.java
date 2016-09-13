package com.caio.vrc.repository;

import static com.caio.vrc.VrcProperty.MODE_IMPL_PROPERTIES;

import com.caio.vrc.repository.impl.FilePropertyRepositoryImpl;
import com.caio.vrc.repository.impl.MongoDBPropertiesRepositoryImpl;

public class PropertyRepositoryFactory {

	public static PropertyRepository create() {
		PropertyRepository propertiesRepository;
		switch (MODE_IMPL_PROPERTIES.getStringValue()) {
		case "MONGODB":
			propertiesRepository = new MongoDBPropertiesRepositoryImpl();
			break;
		case "FILE":
		default:
			propertiesRepository = new FilePropertyRepositoryImpl();
			break;
		}
		return propertiesRepository;
	}

}
