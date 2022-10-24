package com.cyberpeace.oj.practice.service.externalbo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TheorySimpleBo implements Serializable {

    private String resourceId;

    private Integer theoryType;

}
