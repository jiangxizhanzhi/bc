package com.cyberpeace.cr.evaluation.dao.mode;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class SceneTopology {
    private List<TopoElement> elements;
    private List<Connections> connections;
    private JSONArray areas;
    private JSONArray pieces;
    private JSONObject scene;
}
