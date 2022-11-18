package com.cyberpeace.situation.controller.res;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 18/11/2022 上午10:34
 */
@Data
public class DashboardTemplateEchartsData implements Serializable {

    private JSONArray columns;

    private JSONArray rows;
}
