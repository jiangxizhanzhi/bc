package com.cyberpeace.oj.crbase.controller.web;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.crbase.controller.res.CommentRes;
import com.cyberpeace.oj.crbase.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "oj-评论相关api")
@RestController
@RequestMapping("/oj/comment/")
@RequiresAuthentication
public class WebCommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("查询所有评论")
    @GetMapping("/list")
    public BaseRsp<List<CommentRes>> listComment() {

        return null;
    }

}
