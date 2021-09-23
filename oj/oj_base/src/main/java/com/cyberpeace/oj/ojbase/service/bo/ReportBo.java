package com.cyberpeace.oj.ojbase.service.bo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class ReportBo extends NoteBo implements Serializable {

    private Date submitTime;

    private Double score;

    private Boolean isPass;

    private String teacher;

}
