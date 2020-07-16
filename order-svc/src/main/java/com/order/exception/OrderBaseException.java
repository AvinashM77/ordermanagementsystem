/**
 * 
 */
package com.order.exception;

/**
 * @author Avinash
 *
 */
public class OrderBaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderBaseException() {
		super();
	}

	public OrderBaseException(String errorMessage, Throwable t) {
		super(errorMessage, t);
	}

	public OrderBaseException(String errorMessage) {
		super(errorMessage);
	}

	public OrderBaseException(Throwable t) {
		super(t);
	}

}
