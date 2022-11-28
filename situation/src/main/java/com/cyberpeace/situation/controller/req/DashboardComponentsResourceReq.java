package com.cyberpeace.situation.controller.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 28/11/2022 上午11:15
 */
@Data
public class DashboardComponentsResourceReq implements Serializable {

    private String resourceId;

    private String customParams;
}
