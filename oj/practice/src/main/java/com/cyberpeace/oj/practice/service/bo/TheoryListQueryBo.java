package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TheoryListQueryBo implements Serializable {

    private String practiceSetId;

    private List<String> categoryIds;

    private String pattern;

    private Boolean solved;

    private Integer difficulty;

}
