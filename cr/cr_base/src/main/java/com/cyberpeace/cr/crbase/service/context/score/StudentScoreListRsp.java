package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 学员得分列表响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class StudentScoreListRsp implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "学员id")
	private String userId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "学员姓名")
	private String name;

	/**
	 * 用户头像
	 */
	@ApiModelProperty(value = "学员头像")
	private String userAvatar;

	/**
	 * 岗位id
	 */
	@ApiModelProperty(value = "角色id")
	private String roleId;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;

	/**
	 * 岗位群
	 */
	@ApiModelProperty(value = "角色类型， 2:团队学员，4：红方，5：蓝方")
	private Integer roleGroup;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "学员评分")
	private Integer score;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "是否已被评分")
	private Boolean scored;

	/**
	 * 得分时间
	 */
	@ApiModelProperty(value = "评分时间")
	private Date scoreTime;

}
