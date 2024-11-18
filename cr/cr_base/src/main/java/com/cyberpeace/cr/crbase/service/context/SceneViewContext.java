package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景展示模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneViewContext implements Serializable {

    /**
     * 房间id
     *
     * @see String
     */
    @ApiModelProperty("房间id")
	private String roomId;

    /**
     * 当地场景id
     *
     * @see String
     */
    @ApiModelProperty("场景管理器id")
	private String localSceneId;

    /**
     * 中台场景id
     *
     * @see String
     */
    @ApiModelProperty("场景id")
	private String mpSceneId;

    /**
     * 拓扑id
     *
     * @see String
     */
    @ApiModelProperty("拓扑id")
	private String topoId;

    /**
     * 拓扑数据
     *
     * @see JSONObject
     */
    @ApiModelProperty("拓扑模板数据")
	private JSONObject topoData;

    /**
     * 场景数据
     *
     * @see JSONObject
     */
    @ApiModelProperty("场景数据")
	private JSONObject sceneData;
}
