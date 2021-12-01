package com.cyberpeace.cs.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SceneMetaData implements Serializable {

    /** 业务数据编号*/
    private String businessInstanceId;

    /** 场景名称*/
    private String sceneName;

}
