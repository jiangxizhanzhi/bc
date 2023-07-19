package com.cyberpeace.situation.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.situation.controller.req.ApiConnectReq;
import com.cyberpeace.situation.controller.req.ApiRequestPageReq;
import com.cyberpeace.situation.controller.res.ApiRequestInfoRes;
import com.cyberpeace.situation.controller.res.ApiRequestRes;
import com.cyberpeace.situation.dao.entity.ApiRequest;
import com.cyberpeace.situation.dao.entity.ApiRequestHeaders;
import com.cyberpeace.situation.dao.entity.ApiRequestParams;
import com.cyberpeace.situation.dao.entity.ApiResponseParams;
import com.cyberpeace.situation.dao.mapper.ApiRequestMapper;
import com.cyberpeace.situation.service.ApiRequestService;

import lombok.extern.slf4j.Slf4j;


/**
 * @Author xuxz
 * @Date 31/10/2022 上午9:54
 */
@Service
@Slf4j
public class ApiRequestServiceImpl extends ServiceImpl<ApiRequestMapper, ApiRequest> implements ApiRequestService {

    @Autowired
    private ApiRequestMapper apiRequestMapper;

    @Autowired
    private static RestTemplate restTemplate = new RestTemplate();

    @Override
    public String createApiRequest(ApiConnectReq apiConnectReq) {
        ApiRequest apiRequest = apiConnectReqToApiRequest(apiConnectReq);
        String userId = UserInfoContext.getUserContext().getUserId();
        apiRequest.setResourceId(IDGeneratorUtil.getID());
        apiRequest.setCreateUserId(userId);
        apiRequest.setConnectStatus(Boolean.TRUE);
        apiRequestMapper.insert(apiRequest);
        return apiRequest.getResourceId();
    }

    @Override
    public String updateApiConnect(ApiConnectReq apiConnectReq) {
        String resourceId = apiConnectReq.getResourceId();
        if (StringUtils.isEmpty(resourceId)) {
            log.error("API不存在");
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        ApiRequest apiRequest = apiRequestMapper.selectById(resourceId);
        if (apiRequest == null) {
            log.error("API不存在");
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        apiRequestMapper.updateById(apiConnectReqToApiRequest(apiConnectReq));
        return resourceId;
    }

    @Override
    public ApiRequestInfoRes getApiConnectInfo(String resourceId) {
        ApiRequest apiRequest = apiRequestMapper.selectById(resourceId);
        if (apiRequest == null || StringUtils.isEmpty(apiRequest.getResourceId())) {
            log.error("API不存在");
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        ApiConnectReq apiConnectReq = apiRequestToApiConnectReq(apiRequest);
        ApiRequestInfoRes apiRequestRes = BeanUtils.copy(apiRequest, ApiRequestInfoRes.class);
        apiRequestRes.setDescription(apiConnectTest(apiConnectReq));
        String relyOn = apiRequest.getRelyOn();
        if (relyOn != null) {
            apiRequestRes.setRelyOnRes(JsonUtil.toJsonObjectArray(relyOn, String.class));
        }
        return apiRequestRes;
    }

    @Override
    public Integer deleteApiConnect(List<String> idList) {
        return apiRequestMapper.deleteBatchIds(idList);
    }

    @Override
    public String apiConnectTest(ApiConnectReq apiConnectReq) {
        String requestMethod = apiConnectReq.getRequestMethod();
        HttpHeaders headers = getHeaders(apiConnectReq.getRequestHeaders());
        String oldRequestUrl = apiConnectReq.getRequestUrl();
        JSONObject body = getBody(apiConnectReq);
        HttpEntity httpEntity = new HttpEntity(body, headers);
        String requestUrl = apiConnectReq.getRequestUrl();
        String resourceId = apiConnectReq.getResourceId();
        ApiRequest apiRequest = apiConnectReqToApiRequest(apiConnectReq);
        String message = null;
        try {
            if( body != null && ("demo".equals(body.getString("dashboardId")) || ("demo".equals(body.getString("taskId")) && "demo".equals(body.getString("subjectId"))))) {
                message = apiRequestMapper.selectById(apiConnectReq.getResourceId()).getResponseDemo();
            } else {
                ResponseEntity response = restTemplate.exchange(requestUrl, Objects.requireNonNull(HttpMethod.resolve(requestMethod)), httpEntity, String.class);
                log.info("测试远程接口" + requestUrl);
                if (response.getBody() != null) {
                    message = response.getBody().toString();
                }
            }

            apiRequest.setConnectStatus(Boolean.TRUE);
        } catch (Exception e) {
            log.info(e.getMessage());
            apiRequest.setConnectStatus(Boolean.FALSE);
            message = e.getMessage();
        } finally {
            if (StringUtils.isNotBlank(resourceId)) {
                apiRequest.setRequestUrl(oldRequestUrl);
                apiRequestMapper.updateById(apiRequest);
            }
        }
        return message;
    }

    @Override
    public ListRsp<ApiRequestRes> getApiConnectPage(ApiRequestPageReq apiRequestPageReq) {
        Page page = apiRequestPageReq.toPageParam();
        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
                CommonConstant.FALSE);
        return PageUtil.getListResult(this.getBaseMapper().selectPage(page, null, apiRequestPageReq));
    }

    @Override
    public List<ApiRequest> getAllApi(){

        return apiRequestMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public String apiConnectTest(String resourceId) {
        ApiRequest apiRequest = apiRequestMapper.selectById(resourceId);
        if (apiRequest == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        ApiConnectReq apiConnectReq = apiRequestToApiConnectReq(apiRequest);
        return apiConnectTest(apiConnectReq);
    }

    private ApiRequest apiConnectReqToApiRequest(ApiConnectReq apiConnectReq) {
        ApiRequest apiRequest = BeanUtils.copy(apiConnectReq, ApiRequest.class);
        List<ApiRequestHeaders> requestHeaders = apiConnectReq.getRequestHeaders();
        List<ApiResponseParams> responseParams = apiConnectReq.getResponseParams();
        List<ApiRequestParams> requestParams = apiConnectReq.getRequestParams();
        List<String> relyOn = apiConnectReq.getRelyOn();

        try {
            if (CollectionUtils.isNotEmpty(requestHeaders)) {
                apiRequest.setRequestHeaders(JsonUtil.toJsonString(requestHeaders));
            }

            if (CollectionUtils.isNotEmpty(requestParams)) {
                apiRequest.setRequestParams(JsonUtil.toJsonString(requestParams));
            }

            if (CollectionUtils.isNotEmpty(responseParams)) {
                apiRequest.setResponseParams(JsonUtil.toJsonString(responseParams));
            }

            if (relyOn != null) {
                apiRequest.setRelyOn(JsonUtil.toJsonString(relyOn));
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
        }
        return apiRequest;
    }

    private ApiConnectReq apiRequestToApiConnectReq(ApiRequest apiRequest) {
        ApiConnectReq apiConnectReq = BeanUtils.copy(apiRequest, ApiConnectReq.class);
        String requestHeaders = apiRequest.getRequestHeaders();
        String responseParams = apiRequest.getResponseParams();
        String requestParams = apiRequest.getRequestParams();
        try {
            if (StringUtils.isNotBlank(requestHeaders)) {
                apiConnectReq.setRequestHeaders(JsonUtil.toJsonObjectArray(requestHeaders, ApiRequestHeaders.class));
            }

            if (StringUtils.isNotBlank(requestParams)) {
                apiConnectReq.setRequestParams(JsonUtil.toJsonObjectArray(requestParams, ApiRequestParams.class));
            }

            if (StringUtils.isNotBlank(responseParams)) {
                apiConnectReq.setResponseParams(JsonUtil.toJsonObjectArray(responseParams, ApiResponseParams.class));
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
        }

        return apiConnectReq;
    }

    private HttpHeaders getHeaders(List<ApiRequestHeaders> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (ApiRequestHeaders apiRequestHeader : headers) {
            httpHeaders.set(apiRequestHeader.getName(), apiRequestHeader.getValue());
        }
        return httpHeaders;
    }

    private JSONObject getBody(ApiConnectReq apiConnectReq) {
        String requestUrl = apiConnectReq.getRequestUrl();
        String requestMethod = apiConnectReq.getRequestMethod();
        List<ApiRequestParams> requestParams = apiConnectReq.getRequestParams();
        if (CollectionUtils.isNotEmpty(requestParams)) {
            if (Objects.equals(requestMethod, "GET")) {
                StringBuilder sb = new StringBuilder();
                sb.append(requestUrl).append("?");
                for (ApiRequestParams requestParam : requestParams) {
                    String value = requestParam.getValue().toString();
                    if (StringUtils.isNotBlank(value)) {
                        sb.append(requestParam.getName()).append("=").append(value).append("&");
                    }else {
                        sb.append(requestParam.getName()).append("=").append("&");
                    }
                }
                sb.delete(sb.lastIndexOf("&"), sb.length());
                apiConnectReq.setRequestUrl(sb.toString());
                return null;
            } else {
                JSONObject body = new JSONObject();
                for (ApiRequestParams requestParam : requestParams) {
                    body.put(requestParam.getName(), requestParam.getValue());
                }
                return body;
            }
        }
        return null;
    }

}
