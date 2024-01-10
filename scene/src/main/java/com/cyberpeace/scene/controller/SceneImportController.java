package com.cyberpeace.scene.controller;

import javax.annotation.Resource;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.scene.controller.req.SceneImportRequest;
import com.cyberpeace.scene.service.ImportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author maojun
 * @Date 2021/10/11
 */
@Api(value = "场景导入相关接口", tags = "场景导入相关接口")
@Slf4j
@RestController
@RequestMapping( "/cscenes")
public class SceneImportController {

    @Resource
    private ImportService importService;

    @ApiOperation("导入资源")
    @ApiImplicitParam(name = "request", value = "请求", dataTypeClass = SceneImportRequest.class)
    @PostMapping("/import_resource_center")
    public BaseRsp<String> importResourceCenter(@RequestBody SceneImportRequest request) {
        if (StringUtils.isEmpty(request.getPath())) {
            return new BaseRsp<>("");
        }
        String topoId = importService.importByYmlConfig(request.getPath(), request.getImagePath(), request.getSceneFilePath());
        return new BaseRsp<>(topoId);
    }

}
