package com.cyberpeace.situation.service.context;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import netscape.javascript.JSObject;

@Data
public class BoundContext implements Serializable {

	private String type;

	private List<JSONObject> features;

}
