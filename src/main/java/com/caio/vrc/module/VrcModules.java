package com.caio.vrc.module;

import com.caio.vrc.resource.PropertiesResource;
import com.google.inject.Binder;
import com.google.inject.Module;

public class VrcModules implements Module {

	@Override
	public void configure(final Binder binder) {
		// general modules
		binder.install(new RepositoryModule());
		binder.install(new ServiceModule());

		// resources
		binder.bind(PropertiesResource.class);
		// binder.bind(ConstraintViolationExceptionMapper.class);
	}

}
