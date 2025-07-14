package com.tenco.blog._core.common;

import lombok.Data;

/**
 * REST API 공통 응답 형식을 위한 클래스 설계
 * 모든 API 응답을 통일된 형태로 관리하기 위해 설계
 * 응답 형식
 * {
 *  "status" : 200,
 *  "msg" : "Success",
 *  "body" : {실제 데이터}
 * }
 *
 * Integer status;
 * String msg;
 * <T> body;
 */
@Data
public class ApiUtil<T> {

	private Integer status; // HTTP status code
	private String msg; // Response msg
	private T body; // Real response Data

	/**
	 * 성공응답 생성자
	 */
	public ApiUtil(T body) {
		this.status = 200;
		this.msg = "성공";
		this.body =  body;
	}

	/**
	 * 실패 응답 생성자
	 */
	public ApiUtil(Integer status, String msg) {
		this.status = status;
		this.msg = msg;
		this.body = null;
	}

}







