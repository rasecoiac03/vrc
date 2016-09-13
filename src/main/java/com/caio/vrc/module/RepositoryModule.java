package com.caio.vrc.module;

import com.caio.vrc.repository.PropertyRepository;
import com.caio.vrc.repository.PropertyRepositoryFactory;
import com.caio.vrc.repository.ProvinceRepository;
import com.caio.vrc.repository.ProvinceRepositoryFactory;
import com.google.inject.AbstractModule;

public class RepositoryModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PropertyRepository.class).toInstance(PropertyRepositoryFactory.create());
		bind(ProvinceRepository.class).toInstance(ProvinceRepositoryFactory.create());
	}

}
