package com.neueda.atm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Joseph Keenan
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class IDNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IDNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
