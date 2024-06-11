package com.cyberpeace.cr.evaluation.dao.entity;

import com.cyberpeace.common.model.PageReqBase;
import lombok.Data;

@Data
public class AlarmPageReq extends PageReqBase {

    private String templateId;

    private String mpSceneId;
}
