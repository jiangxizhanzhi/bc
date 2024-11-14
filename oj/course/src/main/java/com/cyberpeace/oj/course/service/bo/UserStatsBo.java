package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户统计Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("用户学习统计信息")
public class UserStatsBo extends PublicStatsBo {

	/**
	 * 用户resource_id
	 */
	@ApiModelProperty("用户resource_id")
    private String resourceId;

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
    private String username;

	/**
	 * 用户姓名
	 */
	@ApiModelProperty("用户姓名")
    private String name;

	/**
	 * 用户组织信息
	 */
	@ApiModelProperty("用户组织信息")
    private String organizationData;

	/**
	 * 用户角色
	 */
	@ApiModelProperty("用户角色")
    private String role;

	/**
	 * 用户角色
	 */
	@ApiModelProperty("用户角色")
    private List<JSONObject> roles;

	/**
	 * 用户头像
	 */
	@ApiModelProperty("用户头像")
    private String avatar;


}
