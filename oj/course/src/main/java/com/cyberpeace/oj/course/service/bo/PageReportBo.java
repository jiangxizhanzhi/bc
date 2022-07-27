package com.cyberpeace.oj.course.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class PageReportBo implements Serializable {

    private List<ClassroomReportBo> data;

    private Long total;

}
