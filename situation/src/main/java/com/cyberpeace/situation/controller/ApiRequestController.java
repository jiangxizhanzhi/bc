package com.cyberpeace.situation.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.constant.ApiRequestType;
import com.cyberpeace.situation.controller.req.ApiConnectDelReq;
import com.cyberpeace.situation.controller.req.ApiConnectReq;
import com.cyberpeace.situation.controller.req.ApiRequestPageReq;
import com.cyberpeace.situation.controller.res.ApiRequestInfoRes;
import com.cyberpeace.situation.controller.res.ApiRequestRes;
import com.cyberpeace.situation.service.ApiRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author xuxz
 * @Date 31/10/2022 上午9:47
 */
@Api(tags = "api管理")
@RestController
@RequestMapping("/api/request/")
@Slf4j
public class ApiRequestController {

    @Autowired
    private ApiRequestService apiRequestService;

    @ApiOperation("新增api请求")
    @PostMapping("message")
    public BaseRsp<String> createApiConnect(@RequestBody @Validated(Create.class) ApiConnectReq apiConnectReq) {
        return new BaseRsp<>(apiRequestService.createApiRequest(apiConnectReq));
    }

    @ApiOperation("修改api信息")
    @PatchMapping("message")
    public BaseRsp<String> updateApiConnect(@RequestBody @Validated(Update.class) ApiConnectReq apiConnectReq) {
        return new BaseRsp<>(apiRequestService.updateApiConnect(apiConnectReq));
    }

    @ApiOperation("获取api信息")
    @GetMapping("message")
    public BaseRsp<ApiRequestInfoRes> getApiConnectInfo(@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id", value = "api资源id")
                                                            String resourceId) {
        return new BaseRsp<>(apiRequestService.getApiConnectInfo(resourceId));
    }

    @ApiOperation("批量删除api信息")
    @DeleteMapping("message")
    public BaseRsp<Integer> deleteApiConnect(@RequestBody @Validated ApiConnectDelReq apiConnectDelReq) {
        return new BaseRsp<>(apiRequestService.deleteApiConnect(apiConnectDelReq.getApiConnectIdList()));
    }

    @ApiOperation("获取api列表")
    @GetMapping("list")
    public BaseRsp<ListRsp<ApiRequestRes>> getApiConnectPage(ApiRequestPageReq apiRequestPageReq) {
        return new BaseRsp<>(apiRequestService.getApiConnectPage(apiRequestPageReq));
    }

    @ApiOperation("api连接测试")
    @PostMapping("test")
    public BaseRsp<String> apiConnectTest(@RequestBody @Validated ApiConnectReq apiConnectReq) {
        return new BaseRsp<>(apiRequestService.apiConnectTest(apiConnectReq));
    }

    @ApiOperation("获取请求类型列表")
    @GetMapping("type/list")
    public BaseRsp<List<String>> getRequestType(){
        return new BaseRsp<>(ApiRequestType.getRequestType());
    }

    @ApiOperation("获取api连接原始数据")
    @PostMapping("source/data")
    public BaseRsp<JSONObject> getConnectSourceData(@RequestBody @Validated ApiConnectReq apiConnectReq){
        return new BaseRsp<>(JSONObject.parseObject(apiRequestService.apiConnectTest(apiConnectReq)));
    }

    @ApiOperation("api列表页测试连接")
    @PostMapping("list/test")
    public BaseRsp<String> apiConnectTest(@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id", value = "api资源id")
                                                                String resourceId) {
        return new BaseRsp<>(apiRequestService.apiConnectTest(resourceId));
    }
}
