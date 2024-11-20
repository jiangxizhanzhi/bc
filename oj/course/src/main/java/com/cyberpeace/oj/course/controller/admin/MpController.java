package com.cyberpeace.oj.course.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.bo.ClusterImagePageReq;
import com.cyberpeace.integratation.service.bo.TargetListReq;
import com.cyberpeace.integratation.service.bo.TopoTemplatePageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "转发mp接口api")
@RestController
@RequestMapping("/oj/mp")
@RequiresAuthentication
public class MpController {

    /**
     * 远程终端Service
     */
    @Autowired
    private RemoteTerminalService remoteTerminalService;

    /**
     * 远程拓扑service
     */
    @Autowired
    private RemoteTopoService remoteTopoService;

    @ApiOperation("获取镜像列表")
    @GetMapping("/images")
    public BaseRsp getImagePage(@ApiParam ClusterImagePageReq req){
        JSONObject res = remoteTerminalService.pageImages(req);
        return new BaseRsp(res);
    }

    @ApiOperation("获取镜像分类")
    @GetMapping("/image_categories")
    public BaseRsp getImageCategories() {
        return new BaseRsp(remoteTerminalService.getAllCategorie());
    }

    @ApiOperation("查询dynamips相关的信息")
    @GetMapping("/dynamic_image_info")
    public BaseRsp getDynamicImageInfo() {
        return new BaseRsp(remoteTerminalService.getDynamicImageInfo());
    }

    @ApiOperation("查询靶标对应的探针系统分类")
    @GetMapping("/system_type_to_agent_support")
    public BaseRsp systemTypeToAgentSupport() {
        return new BaseRsp(remoteTerminalService.getAgentSupport());
    }

    @ApiOperation("查询常用靶标信息")
    @GetMapping("/common-targets")
    public BaseRsp commonTargets() {
        return new BaseRsp(remoteTerminalService.getCommonTargets());
    }

    @ApiOperation("场景模板列表")
    @GetMapping("/cscenes")
    public BaseRsp cscenes(@ApiParam TopoTemplatePageReq topoTemplatePageReq) {
        return new BaseRsp(remoteTopoService.getTopoTemplatePage(topoTemplatePageReq));
    }

    @ApiOperation("查询所有靶标信息")
    @GetMapping("/target/listAll")
    public BaseRsp targetListAll(@ApiParam TargetListReq targetListReq) {
        return new BaseRsp(remoteTerminalService.targetList(targetListReq));
    }

    @ApiOperation("查询所有二级分类")
    @GetMapping("/l2_categories")
    public BaseRsp l2Ccategories(@ApiParam Integer firstTypeId) {
        return new BaseRsp(remoteTerminalService.getL2categories(firstTypeId));
    }

    @ApiOperation("根据镜像名称或快照名称查询镜像名称和快照历史信息")
    @GetMapping("/snapshot/by_name")
    public BaseRsp snapshotByName(@ApiParam String name) {
        return new BaseRsp(remoteTerminalService.snapshotByName(name));
    }

}
