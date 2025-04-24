package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 实操博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PracticeOperation
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class OperationBo extends PracticeOperation {

	/**
	 * 文件名称
	 *
	 * @see String
	 */
	@ApiModelProperty("文件名称")
	private String fileName;

	/**
	 * 文件 URL
	 *
	 * @see String
	 */
	@ApiModelProperty("文件地址")
	private String fileUrl;

	/**
	 * 场景配置数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("场景配置信息")
	private JSONObject sceneConfigData;

	/**
	 * 文件
	 *
	 * @see List<PracticeOperationFileBo>
	 */
	@ApiModelProperty("后台实操习题附件 主要针对随机flag")
	private List<PracticeOperationFileBo> files;

	/**
	 * 目标列表
	 *
	 * @see List<TargetBo>
	 */
	@ApiModelProperty("靶标列表")
	private List<TargetBo> targetList;


	@ApiModelProperty("攻击知识点")
	private JSONArray attackTag;

	@ApiModelProperty("防御知识点")
	private JSONArray defenseTag;


	@ApiModelProperty(value = "关联漏洞")
	private String vuln;

}
