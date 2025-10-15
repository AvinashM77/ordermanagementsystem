/**
 * 
 */
package com.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author amake
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

	private String referenceId;
	private String result;
	private String message;

}
