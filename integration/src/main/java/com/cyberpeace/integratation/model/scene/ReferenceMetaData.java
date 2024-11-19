package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

/**
 * 引用元数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
public class ReferenceMetaData implements Serializable {

	/**
	 * 业务id
	 *
	 * @see String
	 */
	private String business_id;

	/**
	 * 业务名称
	 *
	 * @see String
	 */
	private String business_name;

	/**
	 * 1xx=实训；2xx=靶场
	 *
	 * @see Integer
	 */
	private Integer business_code;

	/**
	 * oj专用，暂时不用
	 *
	 * @see Boolean
	 */
	private Boolean is_web;

	/**
	 * 创建用户id
	 *
	 * @see String
	 */
	private String create_user_id;

	public String getBusiness_id() {
		return business_id;
	}

	public void setBusinessId(String business_id) {
		this.business_id = business_id;
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusinessName(String business_name) {
		this.business_name = business_name;
	}

	public Integer getBusiness_code() {
		return business_code;
	}

	public void setBusinessCode(Integer business_code) {
		this.business_code = business_code;
	}

	public Boolean getIs_web() {
		return is_web;
	}

	public void setFromWeb(Boolean from_web) {
		this.is_web = from_web;
	}

	public String getCreate_user_id() {
		return create_user_id;
	}

	public void setCreateUserId(String create_user_id) {
		this.create_user_id = create_user_id;
	}
}
