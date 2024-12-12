package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用户统计 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PublicStatsBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("用户学习统计信息")
public class UserStatsBo extends PublicStatsBo {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("用户resource_id")
    private String resourceId;

    /**
     * 用户名
     *
     * @see String
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("用户姓名")
    private String name;

    /**
     * 组织数据
     *
     * @see String
     */
    @ApiModelProperty("用户组织信息")
    private String organizationData;

    /**
     * 角色
     *
     * @see List<JSONObject>
     */
    @ApiModelProperty("用户角色")
    private List<JSONObject> roles;

    /**
     * 化身
     *
     * @see String
     */
    @ApiModelProperty("用户头像")
    private String avatar;


}
