package com.cyberpeace.oj.course.dao.context;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ClassRoomListQuery implements Serializable {

	private Date startTime;

	private Date endTime;

	private String orgId;

	private String userId;

	private List<String> childrenOrg;

}
