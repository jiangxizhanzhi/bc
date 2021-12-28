package com.cyberpeace.oj.course.dao.entity;

import com.cyberpeace.common.model.BaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomSign extends BaseTable {

	private String resourceId;

	private String classroomId;

	private String userId;

	private Boolean signIn;

	private Date signInTime;

	private Boolean needHelp;

}
