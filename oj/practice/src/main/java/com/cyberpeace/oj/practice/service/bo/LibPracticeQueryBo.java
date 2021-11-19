package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LibPracticeQueryBo implements Serializable {

    private List<String> resourceIds;

    private String practiceSetId;

    private List<String> categoryIds;

    private Integer questionType;

    private String pattern;

}
