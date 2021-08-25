package com.cyberpeace.oj.rtks.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class KnowledgeUnderCourseBo implements Serializable {

    private String courseId;

    private String courseName;

    private String eBook;

    private Integer weight;

}
