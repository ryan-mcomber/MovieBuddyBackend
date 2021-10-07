package com.revature.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.revature.errorhandling.ApiError;
import com.revature.errorhandling.ApiValidationError;

// Tell Spring that this Advice is going to intercept all HTTP requests that hit our Controller

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	// This is a custom method that's going to help use send back the ApiError AS a reponse entity
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		
		return ResponseEntity.status(apiError.getStatus()).body(apiError);
	}

	/**
	 * Intercept exceptions caused by JHibernate Validation
	 * 
	 * WHat happens if a User uses a POST request to send and INVALID User object
	 * which defies some Validationthat we set up in the model?
	 * 
	 * This is designed to capture any exception that might occur when a controller 
	 * method takes in a not-valid object
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		String error = "Request failed validation";
		
		// instantiate an ApiError object
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex);
		
		// Next we can find out exactly WHAT went wrong? Was the password not valid? Was it the firstName length?
		
		// 1. capture the MethodArgumentNotValidException, and iterate over all the field errors
		for(FieldError e : ex.getFieldErrors()) {
			apiError.addSubError(new ApiValidationError(e.getObjectName(), e.getDefaultMessage(), e.getField(), e.getRejectedValue()));
		}
		// send back the api error as the response entity
		return buildResponseEntity(apiError);
	}
	
	
	/**
	 * Intercepts exceptions that are caused by Invalid JSON
	 * Send back a 4XX indicating Client-Side Error
	 */
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		// PEBKAC or maybe your front end isn't properly formating the json objects to be sent back
		String error = "Malformed JSON request!";
		
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}
	
	
	
	
	
	
}
