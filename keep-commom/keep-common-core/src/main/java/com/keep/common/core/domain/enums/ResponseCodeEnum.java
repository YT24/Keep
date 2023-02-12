package com.keep.common.core.domain.enums;

/**
 * @author FrozenWatermelon
 * @date 2020/7/9
 */
public enum ResponseCodeEnum {

	/**
	 * ok
	 */
	OK(200, "SUCCESS"),


	/**
	 * 方法参数没有校验，内容由输入内容决定
	 */
	METHOD_ARGUMENT_NOT_VALID(10001, "方法参数没有校验，内容由输入内容决定"),


	/**
	 * 未授权
	 */
	UNAUTHORIZED(401, "Unauthorized"),

	/**
	 * 服务器出了点小差
	 */
	EXCEPTION(555, "服务器出了点小差"),

	;
    private final Integer code;

	private final String msg;

	public Integer value() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	ResponseCodeEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ResponseCodeEnum{" + "code='" + code + '\'' + ", msg='" + msg + '\'' + "} " + super.toString();
	}

}
