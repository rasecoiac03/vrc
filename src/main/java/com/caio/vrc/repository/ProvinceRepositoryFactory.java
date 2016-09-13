package com.caio.vrc.repository;

import static com.caio.vrc.VrcProperty.MODE_IMPL_PROPERTIES;

import com.caio.vrc.repository.impl.FileProvinceRepositoryImpl;

public class ProvinceRepositoryFactory {

	public static ProvinceRepository create() {
		final ProvinceRepository provinceRepository;
		switch (MODE_IMPL_PROPERTIES.getStringValue()) {
		case "MONGODB": // TODO
		case "FILE":
		default:
			provinceRepository = new FileProvinceRepositoryImpl();
			break;
		}
		return provinceRepository;
	}

}
