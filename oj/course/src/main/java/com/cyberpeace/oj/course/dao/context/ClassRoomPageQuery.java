package com.cyberpeace.oj.course.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;
import lombok.Data;

@Data
public class ClassRoomPageQuery extends PageReqBase {


	private String userId;

	private String trainPlanId;

	private String organizationId;

	private List<String> childrenOrg;

}
