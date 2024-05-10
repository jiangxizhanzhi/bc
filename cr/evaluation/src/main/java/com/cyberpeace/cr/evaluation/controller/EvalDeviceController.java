package com.cyberpeace.cr.evaluation.controller;


import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.service.EvalDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@Api(tags = "靶场测评-设备清单")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/device")
public class EvalDeviceController {

    @Autowired
    private EvalDeviceService evalDeviceService;


    @ApiOperation("设备列表")
    @GetMapping("/list")
    public BaseRsp<JSONArray> getDeviceList(
            @ApiParam(name = "resource_id", value = "模板id", required = true) @RequestParam(name = "resource_id") String resourceId,
            @ApiParam(name = "category", value = "设备类型", required = false) Integer category,
            @ApiParam(name = "name", value = "搜索关键字", required = false) String name) {
        return new BaseRsp<>(evalDeviceService.getDeviceList(resourceId, category, name));
    }
}
