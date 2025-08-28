package com.cyberpeace.oj.practice.controller.req;

import lombok.Data;

@Data
public class FingerPrintDto {
    private String product;
    private String company;
    private String parentCategory;
    private String category;
    private Object rules;
}
