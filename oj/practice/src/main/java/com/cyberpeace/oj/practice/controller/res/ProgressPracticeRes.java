package com.cyberpeace.oj.practice.controller.res;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProgressPracticeRes implements Serializable {

    private String categoryName;

    private Integer count;

    private Integer sum;

    public ProgressPracticeRes(Integer count, Integer sum) {
        this.count = count;
        this.sum = sum;
    }

    public ProgressPracticeRes() {
    }
}
