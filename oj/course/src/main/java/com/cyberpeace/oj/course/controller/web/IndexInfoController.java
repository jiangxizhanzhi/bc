package com.cyberpeace.oj.course.controller.web;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.oj.course.controller.req.IndexReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;

@Api(tags = "首页")
@RestController
@RequestMapping("/index")
@Slf4j
public class IndexInfoController {

    private static final String filePath = "/cpms/index/IndexInfo.json";
    private static final String systemFilePath = "classpath:static/index/IndexInfo.json";
    @ApiOperation("获取首页信息")
    @GetMapping("/info")
    public BaseRsp<IndexReq> info() throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            file = ResourceUtils.getFile(systemFilePath);
        }
        String content = FileUtil.getContent(file);
        IndexReq indexReq = JSON.parseObject(content, IndexReq.class);
        return new BaseRsp<>(indexReq);
    }
}
