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
 * @version 1.0.0
 * @date 2024/11/14
 * @see PublicStatsBo
 * @since 1.0.0
 */
@Data
@ApiModel("用户学习统计信息")
public class UserStatsBo extends PublicStatsBo {

	/**
	 * 用户resource_id
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
	 * 用户姓名
	 *
	 * @see String
	 */
	@ApiModelProperty("用户姓名")
    private String name;

	/**
	 * 用户组织信息
	 *
	 * @see String
	 */
	@ApiModelProperty("用户组织信息")
    private String organizationData;

	/**
	 * 用户角色
	 *
	 * @see String
	 */
	@ApiModelProperty("用户角色")
    private String role;

	/**
	 * 用户角色
	 *
	 * @see List<JSONObject>
	 */
	@ApiModelProperty("用户角色")
    private List<JSONObject> roles;

	/**
	 * 用户头像
	 *
	 * @see String
	 */
	@ApiModelProperty("用户头像")
    private String avatar;


}
