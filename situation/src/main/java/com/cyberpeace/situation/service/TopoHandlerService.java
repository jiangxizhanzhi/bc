package com.cyberpeace.situation.service;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.model.AreaInfo;
import com.cyberpeace.situation.model.CategorieMap;
import com.cyberpeace.situation.model.LineStyle;
import com.cyberpeace.situation.model.PlaneCoordinate;

import java.util.List;
import java.util.Map;

/**
 * 5.3版本场景拓扑数据到3D拓扑格式转换
 */
public interface TopoHandlerService {

    /**
     *
     */

    CategorieMap getCategorieMap(JSONObject deviceCategorie);

    String getLineColor();

    Integer getLineWidth();

    String getLineType();

    LineStyle getLineStyle();

    List<String> getLineEffect();

    List<PlaneCoordinate> getSceneScope(List<Float> xList, List<Float> yList);

    String getAreaShape(JSONObject area);

    String getAreaStatus(JSONObject area);

    String getElementStatus(JSONObject element);

    String getElementActionStatus(JSONObject element);

    List<String> getElementAreas(Float x, Float y, Map<String, AreaInfo> areasInfo);
}
