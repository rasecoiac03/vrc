package com.caio.vrc.resource.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caio.vrc.exception.VrcException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebFilter("/*")
public class GeneralExceptionFilter implements Filter {

	private final Gson gson = new GsonBuilder().create();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final HttpServletRequest request = (HttpServletRequest) servletRequest;

		try {
			chain.doFilter(request, response);
		} catch (Throwable e) {
			if (e.getCause() instanceof VrcException) {
				VrcException ex = (VrcException) e.getCause();
				setResponse(response, ex.getStatusCode(), ex.getMessage());
				return;
			}
			throw e;
		}

	}

	private void setResponse(final HttpServletResponse response, final int statusCode, final String message)
			throws IOException {
		response.setStatus(statusCode);
		response.getWriter().write(gson.toJson(Arrays.asList(message)));
		response.setContentType("application/json");
	}

	@Override
	public void destroy() {
	}

}
