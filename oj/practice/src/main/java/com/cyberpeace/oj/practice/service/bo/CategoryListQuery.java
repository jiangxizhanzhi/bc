package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CategoryListQuery implements Serializable {

    private Integer practiceType;

    private String pattern;

}
