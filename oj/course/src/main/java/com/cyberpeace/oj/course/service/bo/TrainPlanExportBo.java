package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 不能增加和删除字段 也不能改变字段顺序
 */
@Data
public class TrainPlanExportBo implements Serializable {

	private String trainPLanName;

	private String courseName;

	private String lessonName;

	private Date startTime;

	private Date endTime;

	private String orgPath;

}
