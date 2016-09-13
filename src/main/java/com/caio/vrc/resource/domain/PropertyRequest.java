package com.caio.vrc.resource.domain;

import static com.caio.vrc.util.Patterns.NUMERIC_PATTERN;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String x;
	private String y;
	private String title;
	private String price;
	private String description;
	private String beds;
	private String baths;
	private String squareMeters;

	public PropertyRequest() {
	}

	public PropertyRequest(String x, String y, String title, String price, String description, String beds,
			String baths, String squareMeters) {
		this.x = x;
		this.y = y;
		this.title = title;
		this.price = price;
		this.description = description;
		this.beds = beds;
		this.baths = baths;
		this.squareMeters = squareMeters;
	}

	@Min(value = 0, message = "x must be greater than 0")
	@Max(value = 1400, message = "x must be less than 1400")
	@Pattern(regexp = NUMERIC_PATTERN, message = "x must be numeric")
	public String getX() {
		return x;
	}

	@Min(value = 0, message = "y must be greater than 0")
	@Max(value = 1000, message = "y must be less than 1000")
	@Pattern(regexp = NUMERIC_PATTERN, message = "y must be numeric")
	public String getY() {
		return y;
	}

	@NotBlank(message = "title cannot be blank")
	public String getTitle() {
		return title;
	}

	@Pattern(regexp = NUMERIC_PATTERN, message = "price must be numeric")
	public String getPrice() {
		return price;
	}

	@NotBlank(message = "description cannot be blank")
	public String getDescription() {
		return description;
	}

	@Min(value = 1, message = "beds must be greater than 1")
	@Max(value = 5, message = "beds must be less than 5")
	@Pattern(regexp = NUMERIC_PATTERN, message = "beds must be numeric")
	public String getBeds() {
		return beds;
	}

	@Min(value = 1, message = "baths must be greater than 1")
	@Max(value = 4, message = "baths must be less than 4")
	@Pattern(regexp = NUMERIC_PATTERN, message = "baths must be numeric")
	public String getBaths() {
		return baths;
	}

	@Min(value = 20, message = "squareMeters must be greater than 20")
	@Max(value = 240, message = "squareMeters must be less than 240")
	@Pattern(regexp = NUMERIC_PATTERN, message = "squareMeters must be numeric")
	public String getSquareMeters() {
		return squareMeters;
	}

}
