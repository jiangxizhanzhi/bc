package com.cyberpeace.situation.controller.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午4:55
 */
@Data
public class DashboardTemplateDelReq implements Serializable {
    private List<String> dashboardTemplateIdList;
}
