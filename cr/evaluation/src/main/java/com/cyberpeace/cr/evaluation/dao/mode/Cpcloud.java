package com.cyberpeace.cr.evaluation.dao.mode;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class Cpcloud {
    private JSONObject element_ports;
    private JSONObject elements;

    public void setScene() {
        JSONObject scene = new JSONObject();
        scene.put("dns_nameservers", new JSONArray());
        this.scene = scene;
    }

    private JSONObject scene;
    private JSONObject cpcollection;

    public void setCpcollection(JSONObject cpcollection) {
        this.cpcollection = cpcollection;
    }

    public void setCpflow(JSONObject cpflow) {
        this.cpflow = cpflow;
    }

    private JSONObject cpflow;
}
