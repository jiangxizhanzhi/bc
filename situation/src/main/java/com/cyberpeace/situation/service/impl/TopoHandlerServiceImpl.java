package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.constant.Color;
import com.cyberpeace.situation.constant.LineEffect;
import com.cyberpeace.situation.constant.LineType;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TopoHandlerServiceImpl implements TopoHandlerService {

    @Override
    public CategorieMap getCategorieMap(JSONObject deviceCategorie) {

        List<DeviceType> firstDeviceTypes = JsonUtil.toJsonObjectArray(deviceCategorie.getJSONArray("first_type_list").toString(), DeviceType.class);
        List<DeviceType> secondDeviceTypes = JsonUtil.toJsonObjectArray(deviceCategorie.getJSONArray("second_type_list").toString(), DeviceType.class);

        List<DeviceRole> roles = JsonUtil.toJsonObjectArray(deviceCategorie.getJSONArray("role").toString(), DeviceRole.class);

        // 处理靶标一级分类映射
        Map<Integer, String> firstCategorie = Maps.newHashMap();
        for (DeviceType firstDeviceType : firstDeviceTypes) {
            firstCategorie.put(firstDeviceType.getId(), firstDeviceType.getName());
        }

        // 处理靶标二级分类映射
        Map<Integer, String> secondCategorie = Maps.newHashMap();
        for (DeviceType secondDeviceType : secondDeviceTypes) {
            secondCategorie.put(secondDeviceType.getId(), secondDeviceType.getName());
        }

        // 处理靶标角色映射
        Map<Integer, String> role = Maps.newHashMap();
        for (DeviceRole deviceRole : roles) {
            role.put(deviceRole.getId(), deviceRole.getName());
        }

        CategorieMap categorieMap = new CategorieMap();
        categorieMap.setFirstCategorie(firstCategorie);
        categorieMap.setSecondCategorie(secondCategorie);
        categorieMap.setRole(role);

        return categorieMap;
    }

    @Override
    public String getLineColor() {

        String color = Color.getRandomValue(Color.values());

        return color;

    }

    @Override
    public Integer getLineWidth() {

        Integer lineWidth = 1;

        return lineWidth;

    }

    @Override
    public String getLineType() {

        String lineType = LineType.getRandomValue(LineType.values());

        return lineType;

    }

    @Override
    public LineStyle getLineStyle() {
        LineStyle lineStyle = new LineStyle();
        lineStyle.setLineType(getLineType());
        lineStyle.setWidth(getLineWidth());
        lineStyle.setColor(getLineColor());

        return lineStyle;
    }

    @Override
    public List<String> getLineEffect() {

        List<String> effect = LineEffect.getRandomEffect(LineEffect.values());

        return effect;
    }

    @Override
    public List<PlaneCoordinate> getSceneScope(List<Float> xList, List<Float> yList) {

        // 获取x、y坐标的极值
        Float xMax = Collections.max(xList);
        Float xMin = Collections.min(xList);
        Float yMax = Collections.max(yList);
        Float yMin = Collections.min(yList);

        // 构造拓扑最大坐标范围
        List<PlaneCoordinate> sceneScope = Lists.newArrayList();
        sceneScope.add(new PlaneCoordinate(xMin, yMin));
        sceneScope.add(new PlaneCoordinate(xMin, yMax));
        sceneScope.add(new PlaneCoordinate(xMax, yMax));
        sceneScope.add(new PlaneCoordinate(xMax, yMin));

        return sceneScope;

    }

    @Override
    public String getAreaShape(JSONObject area) {
        return "rectangle";
    }

    @Override
    public String getAreaStatus(JSONObject area) {
        return "normal";
    }

    @Override
    public String getElementStatus(JSONObject element) {
        return "running";
    }

    @Override
    public String getElementActionStatus(JSONObject element) {
        return "normal";
    }

    @Override
    public List<String> getElementAreas(Float x, Float y, Map<String, AreaInfo> areasInfo) {

        List<String> elementAreas = Lists.newArrayList();

        for (AreaInfo areaInfo : areasInfo.values()) {

            Float xMin = areaInfo.getScope().get(0).getX();
            Float yMin = areaInfo.getScope().get(0).getY();
            Float xMax = areaInfo.getScope().get(2).getX();
            Float yMax = areaInfo.getScope().get(2).getY();

            if (xMin <= x && x <= xMax && yMin <= y && y <= yMax) {
                elementAreas.add(areaInfo.getAreaId());
            }
        }

        return elementAreas;
    }
}