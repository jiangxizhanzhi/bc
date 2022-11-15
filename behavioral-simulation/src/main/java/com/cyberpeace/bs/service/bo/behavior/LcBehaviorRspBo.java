package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LcBehaviorRspBo implements Serializable {

	private String id;

	private String name;

	private Integer tag;

	private String creatorName;

	private Date createTime;
}
