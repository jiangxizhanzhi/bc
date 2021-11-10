package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryListQuery implements Serializable {

    private Integer practiceType;

    private String pattern;

}
