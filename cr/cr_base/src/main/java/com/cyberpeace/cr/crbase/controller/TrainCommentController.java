package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.dao.entity.TrainComment;
import com.cyberpeace.cr.crbase.service.TrainCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "训练评论")
@Slf4j
@RequiresAuthentication
@RestController
@RequestMapping("/cr/train-comment")
public class TrainCommentController {
    @Autowired
    private TrainCommentService trainCommentService;

    @GetMapping("/list")
    @ApiOperation("训练评论列表")
    public BaseRsp<List<TrainComment>> list(@RequestParam("template_id") String templateId) {
        return new BaseRsp<>(trainCommentService.fullList(templateId));
    }
}
