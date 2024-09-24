package com.cyberpeace.cr.evaluation.dao.mode;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class Plugins {
    private Cpcloud cpcloud;
    private JSONObject cpcollection;
    private JSONObject cpflow;
}
