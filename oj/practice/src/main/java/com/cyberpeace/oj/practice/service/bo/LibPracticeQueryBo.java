package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LibPracticeQueryBo implements Serializable {

    private String practiceSetId;

    private List<String> categoryIds;

    private Integer questionType;

    private String pattern;

}
