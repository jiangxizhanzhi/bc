package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class WebOperationBo extends OperationBo {

    private Boolean solved;

    private Boolean isCollection;

    private JSONObject sceneData;

}
