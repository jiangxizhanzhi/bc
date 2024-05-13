package com.cyberpeace.cr.evaluation.controller;


import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.service.EvalSceneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "靶场测评-场景")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/scene")
public class EvalSceneController {


    @Autowired
    private EvalSceneService evalSceneService;

    @ApiOperation("暂停场景")
    @PostMapping("/pause")
    public BaseRsp<Boolean> pauseScene(
            @ApiParam(name = "resource_id", value = "模板id", required = true) @RequestParam(name = "resource_id") String resourceId) {

        Boolean b = evalSceneService.pause(resourceId);

        return new BaseRsp<>(b);
    }
    @ApiOperation("恢复场景")
    @PostMapping("/unPause")
    public BaseRsp<Boolean> unpauseScene(
            @ApiParam(name = "resource_id", value = "模板id", required = true) @RequestParam(name = "resource_id") String resourceId) {

        Boolean b =  evalSceneService.unPause(resourceId);


        return new BaseRsp<>(b);
    }
}
