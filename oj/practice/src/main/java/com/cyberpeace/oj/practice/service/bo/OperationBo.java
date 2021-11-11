package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class OperationBo extends PracticeOperation {

    private String fileName;

    private String fileUrl;

    private JSONObject sceneConfigData;

}
