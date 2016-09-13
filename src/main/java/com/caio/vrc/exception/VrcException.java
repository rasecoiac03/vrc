package com.caio.vrc.exception;

public class VrcException extends RuntimeException {

	private static final long serialVersionUID = 4785353053246486011L;

	private final int statusCode;
	private final String message;

	public VrcException(String message) {
		this(null, 0, message);
	}

	public VrcException(Throwable e) {
		this(e, 0);
	}

	public VrcException(Throwable e, int statusCode) {
		this(e, statusCode, "generic error");
	}

	public VrcException(Throwable e, int statusCode, String message) {
		super(message, e);
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
