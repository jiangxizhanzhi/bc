package com.cyberpeace.oj.person.controller;

import com.cyberpeace.common.annoations.AdminRequired;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.person.controller.req.IndexReq;
import com.cyberpeace.oj.person.service.IndexInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "首页")
@RestController
@RequestMapping("/index")
@Slf4j
public class IndexInfoController {

    @Autowired
    IndexInfoService indexInfoService;

    @ApiOperation("获取首页信息")
    @GetMapping("/info")
    public BaseRsp<IndexReq> info() {

        return new BaseRsp<>(indexInfoService.getIndexInfo());
    }

    @ApiOperation("设置首页信息")
    @PostMapping("/setInfo")
    @AdminRequired
    public BaseRsp<Void> setInfo(@RequestParam("level_file_name") String levelFileName){
        indexInfoService.setIndexInfo(levelFileName);
        return new BaseRsp<>();
    }
}
