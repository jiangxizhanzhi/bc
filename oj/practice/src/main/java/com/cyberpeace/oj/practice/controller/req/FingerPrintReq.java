package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FingerPrintReq extends PageReqBase {
    private String product;

    private String company;

    private String parentCategory;

    private String category;

    private String rules;
}
