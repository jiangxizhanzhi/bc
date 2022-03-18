package com.cyberpeace.situation.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.dao.entity.SituationConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class SceneData implements Serializable {

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("场景名称")
    private String sceneName;

    @ApiModelProperty("场景描述")
    private String sceneDesc;

    @ApiModelProperty("区域")
    private JSONArray areas;

    @ApiModelProperty("节点数据")
    private JSONArray elements;

    @ApiModelProperty("连线")
    private JSONArray connections;

    @ApiModelProperty("场景补充信息")
    private JSONObject extendElementInfo;

    @ApiModelProperty("X坐标列表")
    private List<Float> xList;

    @ApiModelProperty("Y坐标列表")
    private List<Float> yList;

    @ApiModelProperty("外部网络列表")
    private List<String> externalNetwork;

    @ApiModelProperty("端口、节点映射")
    private Map<String, String> portElementMap;

    @ApiModelProperty("靶标类型映射")
    private CategorieMap categorieMap;

    @ApiModelProperty("态势配置列表")
    private Map<Integer, Map<String, List<SituationConfig>>> situationConfigMap;

}
