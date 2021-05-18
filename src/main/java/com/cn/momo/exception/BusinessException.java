package com.cn.momo.exception;

/**
 * 自定义业务异常
 * dongwenmo 2020-08-17
 */
public class BusinessException extends Exception {
	private int stateFlag;

	public BusinessException() {
		this.stateFlag = -1;
	}

	public BusinessException(int stateFlag, String msg) {
		super(msg);
		this.stateFlag = stateFlag;
	}

	public BusinessException(String msg) {
		super(msg);
		this.stateFlag = -1;
	}

	public BusinessException(String msg, String solution) {
		super(msg + solution);
		this.stateFlag = -1;
	}

	public BusinessException(String msg, Throwable cause) {
		super(msg, cause);
		this.stateFlag = -1;
	}

	public BusinessException(Throwable cause) {
		super(cause);
		this.stateFlag = -1;
	}

	public int getStateFlag() {
		return this.stateFlag;
	}
}
