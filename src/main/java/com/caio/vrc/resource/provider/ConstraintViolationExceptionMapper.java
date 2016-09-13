package com.caio.vrc.resource.provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		return Response.status(BAD_REQUEST).entity(violations(exception)).type(APPLICATION_JSON).build();
	}

	private ViolentionsResponse violations(ConstraintViolationException exception) {
		final List<String> violations = new ArrayList<>();
		for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
			violations.add(violation.getMessage());
		}
		return new ViolentionsResponse(violations);
	}

	public static class ViolentionsResponse {

		private final List<String> violations;

		public ViolentionsResponse(List<String> violations) {
			this.violations = violations;
		}

		public List<String> getViolations() {
			return Collections.unmodifiableList(violations);
		}

	}

}
