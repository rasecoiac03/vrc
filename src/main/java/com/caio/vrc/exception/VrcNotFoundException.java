package com.caio.vrc.exception;

import com.caio.vrc.util.HttpStatus;

public class VrcNotFoundException extends VrcException {

	private static final long serialVersionUID = 4785353053246486011L;

	public VrcNotFoundException(Throwable e, String message) {
		super(e, HttpStatus.NOT_FOUND, "not found");
	}

	public VrcNotFoundException(Throwable e) {
		super(e, HttpStatus.NOT_FOUND);
	}

	public VrcNotFoundException(String message) {
		super(message);
	}

}
