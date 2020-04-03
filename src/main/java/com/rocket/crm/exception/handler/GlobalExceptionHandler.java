package com.rocket.crm.exception.handler;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rocket.crm.config.Translator;
import com.rocket.crm.exception.GenricException;
import com.rocket.crm.exception.UserRequestException;
import com.rocket.crm.utility.ResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final String EXCEPTION_OCCURED = "Exception Occured:: {}";
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseMessage<String> handleUsernameNotFoundException(HttpServletRequest request,
			UsernameNotFoundException ex) {
		LOGGER.info(EXCEPTION_OCCURED, ex.getMessage());
		return new ResponseMessage<>(HttpStatus.FORBIDDEN.value(), ex.getMessage());
	}

	@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(GenricException.class)
	public ResponseMessage<String> handleGenricException(HttpServletRequest request, GenricException ex) {
		LOGGER.info(EXCEPTION_OCCURED, ex.getMessage());
		return new ResponseMessage<>(HttpStatus.EXPECTATION_FAILED.value(), ex.getMessage());
	}

	@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseMessage<String> handleMissingServletRequestParameterException(HttpServletRequest request,
			MissingServletRequestParameterException ex) {
		LOGGER.info(EXCEPTION_OCCURED, ex.getMessage());
		return new ResponseMessage<>(HttpStatus.EXPECTATION_FAILED.value(), Translator.toLocale("REQUIRED_STRING_PARAM")
				+ ex.getParameterName() + Translator.toLocale("IS_NOT_PRESENT"));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseMessage<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		LOGGER.info(EXCEPTION_OCCURED, ex.getMessage());
		BindingResult bindingResult = ex.getBindingResult();
		Set<String> errors = new HashSet<>();
		for (ObjectError objectError : bindingResult.getAllErrors()) {
			if (!errors.contains(objectError.getDefaultMessage())) {
				errors.add(objectError.getDefaultMessage());
			}
		}
		return new ResponseMessage<>(HttpStatus.BAD_REQUEST.value(), errors.toString());
	}

	@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseMessage<String> handleMethodNotAllowedException(HttpServletRequest request,
			HttpRequestMethodNotSupportedException ex) {
		LOGGER.info(EXCEPTION_OCCURED, ex.getMessage());
		return new ResponseMessage<>(HttpStatus.EXPECTATION_FAILED.value(), ex.getMessage());
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserRequestException.class)
	public ResponseMessage<String> handleUserRequestException(HttpServletRequest request, UserRequestException ex) {
		LOGGER.info(EXCEPTION_OCCURED, ex.getMessage());
		return new ResponseMessage<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseMessage<String> httpMessageNotReadableException(HttpMessageNotReadableException ex,
			HttpServletRequest request) {
		LOGGER.info(EXCEPTION_OCCURED, ex.getMessage());
		return new ResponseMessage<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseMessage<String> handleMethodNotAllowedException(HttpServletRequest request, Exception ex) {
		LOGGER.info(EXCEPTION_OCCURED, ex.getMessage());
		return new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ResponseMessage<String> handleMethodNotAllowedException(HttpServletRequest request,
			AccessDeniedException ex) {
		LOGGER.info(EXCEPTION_OCCURED, ex.getMessage());
		return new ResponseMessage<>(HttpStatus.FORBIDDEN.value(), ex.getMessage());
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseMessage<String> handleValidationException(ValidationException ex, HttpServletRequest request) {
		LOGGER.info(EXCEPTION_OCCURED, ex.getMessage());
		return new ResponseMessage<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

}
