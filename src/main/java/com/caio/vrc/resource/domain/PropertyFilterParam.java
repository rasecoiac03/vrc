package com.caio.vrc.resource.domain;

import static com.caio.vrc.util.Patterns.NUMERIC_PATTERN;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.ws.rs.QueryParam;

public class PropertyFilterParam {

	@QueryParam("ax")
	private String ax;

	@QueryParam("ay")
	private String ay;

	@QueryParam("bx")
	private String bx;

	@QueryParam("by")
	private String by;

	public PropertyFilterParam() {
	}

	public PropertyFilterParam(String ax, String ay, String bx, String by) {
		this.ax = ax;
		this.ay = ay;
		this.bx = bx;
		this.by = by;
	}

	@Min(value = 0, message = "x must be greater than 0")
	@Max(value = 1400, message = "x must be less than 1400")
	@Pattern(regexp = NUMERIC_PATTERN, message = "x must be numeric")
	public String getAx() {
		return ax;
	}

	@Min(value = 0, message = "y must be greater than 0")
	@Max(value = 1000, message = "y must be less than 1000")
	@Pattern(regexp = NUMERIC_PATTERN, message = "y must be numeric")
	public String getAy() {
		return ay;
	}

	@Min(value = 0, message = "x must be greater than 0")
	@Max(value = 1400, message = "x must be less than 1400")
	@Pattern(regexp = NUMERIC_PATTERN, message = "x must be numeric")
	public String getBx() {
		return bx;
	}

	@Min(value = 0, message = "y must be greater than 0")
	@Max(value = 1000, message = "y must be less than 1000")
	@Pattern(regexp = NUMERIC_PATTERN, message = "y must be numeric")
	public String getBy() {
		return by;
	}

}
