package com.order.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.order.model.BaseResponse;

/**
 * @author Avinash
 *
 */
@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(value = OrderBaseException.class)
	public ResponseEntity<BaseResponse> handleOrderBaseException(OrderBaseException e) {
		return new ResponseEntity<>(new BaseResponse(null, "RESULT_FAILURE", e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// error handle for @Valid
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.BAD_REQUEST);

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	// error handle for @Valid
	@ExceptionHandler(value = ConstraintViolationException.class)
	protected ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.BAD_REQUEST);

		// Get all errors
		List<String> errors = ex.getConstraintViolations().stream().map(x -> x.getMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

}
