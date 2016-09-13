package com.caio.vrc.resource.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.perf4j.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@WebFilter("/*")
public class LogFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final HttpServletRequest request = (HttpServletRequest) servletRequest;

		String path = request.getPathInfo();
		MDC.put("flow", path);

		final StopWatch timer = new StopWatch();
		try {
			chain.doFilter(request, response);
			timer.stop();
			LOGGER.info("path: {}, elapsed time: {}", path, timer.getElapsedTime());
		} finally {
			MDC.remove("flow");
		}

	}

	@Override
	public void destroy() {
	}

}
