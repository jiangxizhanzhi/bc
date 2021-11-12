package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("oj_local_scene")
public class OjLocalScene extends BaseTable {

    private String resourceId;

    private String userId;

    private Boolean shared;

    private Integer fromWeb;

    private String flags;

    private String businessId;

    private Integer businessType;

    private String localSceneId;

}
