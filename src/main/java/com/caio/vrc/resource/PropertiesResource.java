package com.caio.vrc.resource;

import static com.caio.vrc.util.Patterns.NUMERIC_PATTERN;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.caio.vrc.domain.Property;
import com.caio.vrc.domain.PropertyFilter;
import com.caio.vrc.resource.domain.PropertiesResponse;
import com.caio.vrc.resource.domain.PropertyFilterParam;
import com.caio.vrc.resource.domain.PropertyRequest;
import com.caio.vrc.resource.domain.PropertyResponse;
import com.caio.vrc.service.PropertiesService;
import com.caio.vrc.util.ApiUtil;

@Singleton
@Resource
@Path("/v1/properties")
@Produces(APPLICATION_JSON)
public class PropertiesResource implements PropertiesApi {

	private final PropertiesService propertiesService;

	@Inject
	public PropertiesResource(PropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}

	@GET
	@Path("/{id}")
	@Override
	public PropertyResponse get(
			@Pattern(regexp = NUMERIC_PATTERN, message = "id must be numeric") @PathParam("id") final String id) {
		final Property property = propertiesService.get(Integer.valueOf(id));
		return ApiUtil.propertyResponseFrom(property);
	}

	@POST
	@Consumes(APPLICATION_JSON)
	@Override
	public PropertyResponse add(@NotNull @Valid final PropertyRequest request) {
		final Property property = ApiUtil.propertyFrom(request);
		return ApiUtil.propertyResponseFrom(propertiesService.add(property));
	}

	@GET
	@Override
	public PropertiesResponse find(@NotNull @BeanParam @Valid final PropertyFilterParam propertyFilterParam) {
		final PropertyFilter propertyFilter = ApiUtil.propertyFilterFrom(propertyFilterParam);
		return ApiUtil.propertiesResponseFrom(propertiesService.find(propertyFilter));
	}

}
