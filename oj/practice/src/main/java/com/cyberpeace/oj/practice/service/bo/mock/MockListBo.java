package com.cyberpeace.oj.practice.service.bo.mock;

import java.util.Date;

import lombok.Data;

@Data
public class MockListBo {

	private String resourceId;

	private String title;

	private Integer count;

    private Integer status;

	private Date createTime;

	private String logo;
}
