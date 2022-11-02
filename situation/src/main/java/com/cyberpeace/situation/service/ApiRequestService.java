package com.cyberpeace.situation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.ApiConnectReq;
import com.cyberpeace.situation.controller.req.ApiRequestPageReq;
import com.cyberpeace.situation.controller.res.ApiRequestInfoRes;
import com.cyberpeace.situation.controller.res.ApiRequestRes;

import java.util.List;

/**
 * @Author xuxz
 * @Date 31/10/2022 上午9:53
 */
public interface ApiRequestService {
    /**
     * 添加api数据
     *
     * @param apiConnectReq
     * @return
     */
    String createApiRequest(ApiConnectReq apiConnectReq);

    /**
     * 更新api数据
     *
     * @param apiConnectReq
     * @return
     */
    String updateApiConnect(ApiConnectReq apiConnectReq);

    /**
     * 获取api详情
     * @param resourceId
     * @return
     */
    ApiRequestInfoRes getApiConnectInfo(String resourceId);

    /**
     * 批量删除api
     * @param idList
     * @return
     */
    Integer deleteApiConnect(List<String> idList);

    /**
     * api连接测试
     * @param apiConnectReq
     * @return
     */
    String apiConnectTest(ApiConnectReq apiConnectReq);

    /**
     * 分页查询api
     * @param apiRequestPageReq
     * @return
     */
    ListRsp<ApiRequestRes> getApiConnectPage(ApiRequestPageReq apiRequestPageReq);
}
