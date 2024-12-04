package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 保存群信息请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class SaveGroupInfoReq implements Serializable {

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂编号")
	private String classroomId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("分组模板名称")
	@NotBlank
	private String name;

	/**
	 * 数据
	 *
	 * @see List<GroupInfoBo>
	 */
	@ApiModelProperty("分组信息")
	@NotEmpty
	private List<GroupInfoBo> data;

}
