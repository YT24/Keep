package com.keep.common.core.expection;


import lombok.Data;

/**
 * @prouect_name: framework
 * @class_name: RootRuntimeException
 * @description: 根RuntimeException类
 **/
@Data
public class RootRuntimeException extends RuntimeException {

	public RootRuntimeException(String message) {
		this(500, message);
	}

	public RootRuntimeException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public RootRuntimeException(Integer exceptionCode, String message, Throwable cause) {
		super(message, cause);
		this.code = exceptionCode;
	}

	private Integer code;

	/**
	 * 异常代码
	 *
	 * @return 异常代码
	 */
	public Integer getExceptionCode() {
		return code;
	}

	/**
	 * 异常代码
	 *
	 * @param code 响应码
	 */
	public void setExceptionCode(Integer code) {
		this.code = code;
	}

}
