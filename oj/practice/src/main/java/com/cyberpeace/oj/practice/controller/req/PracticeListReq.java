package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class PracticeListReq extends PageReqBase {
    private Integer practiceType;
}
