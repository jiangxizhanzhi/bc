package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课后实操练习数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see TheoryLessonBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class OperationLessonBo extends TheoryLessonBo {

    /**
     * 文件路径
     *
     * @see String
     */
    @ApiModelProperty("文件路径")
	private String filePath;

    /**
     * 文件名称
     *
     * @see String
     */
    @ApiModelProperty("文件名称")
	private String fileName;

    /**
     * 拓扑id
     *
     * @see String
     */
    @ApiModelProperty("拓扑编号")
	private String topoId;

    /**
     * 场景共享模式
     *
     * @see Integer
     */
    @ApiModelProperty("场景共享类型")
	private Integer sceneShareMode;

    /**
     * 场景配置数据
     *
     * @see JSONObject
     */
    @ApiModelProperty("拓扑配置信息")
	private JSONObject sceneConfigData;

    /**
     * 场景数据
     *
     * @see JSONObject
     */
    @ApiModelProperty("场景信息")
	private JSONObject sceneData;
}
