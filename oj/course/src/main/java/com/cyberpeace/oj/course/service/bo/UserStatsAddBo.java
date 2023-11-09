package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("用户学习统计信息")
public class UserStatsAddBo implements Serializable {


    @ApiModelProperty("用户resource_id")
    private String resourceId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户姓名")
    private String name;

    @ApiModelProperty("用户组织信息")
    private String organizationData;

    @ApiModelProperty("用户角色")
    private List<JSONObject> roles;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("完成课时数量")
    private Integer lessonRecordCount = 0;

    @ApiModelProperty("做过理论题数")
    private Integer finishTheoryCount = 0;


}
