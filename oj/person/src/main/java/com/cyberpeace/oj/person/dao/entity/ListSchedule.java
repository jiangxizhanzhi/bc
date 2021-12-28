package com.cyberpeace.oj.person.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ListSchedule implements Serializable {

  @ApiModelProperty("上课时间")
  private String date;

  @ApiModelProperty("待上课程")
  private List<String> classroomInfo;

}
