package com.cyberpeace.oj.person.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.oj.person.dao.entity.*;
import com.cyberpeace.oj.person.service.PersonCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "oj-前台个人中心api")
@RestController
@RequestMapping("/oj/person")
@RequiresAuthentication
public class PersonController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);


    @Autowired
    private PersonCenterService personCommentService;


    @ApiOperation("当前用户所有评论")
    @GetMapping("/comments")
    public ListRsp<PersonComment> listComments(@ApiParam @Validated PageReqBase pageReq) {
        Page page = pageReq.toPageParam();
        String userId = UserInfoContext.getUserContext().getUserId();

        IPage<PersonComment> comments = personCommentService.pageComment(page, userId);
        return PageUtil.getListResult(comments);

    }

    @ApiOperation("当前用户所有回复")
    @GetMapping("/replies")
    public ListRsp<PersonComment> listReplies(@ApiParam @Validated PageReqBase pageReq) {
        Page page = pageReq.toPageParam();
        String userId = UserInfoContext.getUserContext().getUserId();

        IPage<PersonComment> comments = personCommentService.pageReplies(page, userId);
        return PageUtil.getListResult(comments);

    }

    @ApiOperation("当前用户所有笔记")
    @GetMapping("/notes")
    public ListRsp<PersonNote> listNotes(@ApiParam @Validated PageReqBase pageReq) {
        Page page = pageReq.toPageParam();
        String userId = UserInfoContext.getUserContext().getUserId();

        IPage<PersonNote> comments = personCommentService.pageNotes(page, userId);
        return PageUtil.getListResult(comments);

    }

    @ApiOperation("当前用户所有收藏")
    @GetMapping("/collections")
    public ListRsp<PersonCollection> listCollections(@ApiParam @Validated PageReqBase pageReq, @ApiParam @Validated String type) {
        Page page = pageReq.toPageParam();
        String userId = UserInfoContext.getUserContext().getUserId();

        IPage<PersonCollection> collections = personCommentService.pageCollections(page, userId, type);
        return PageUtil.getListResult(collections);

    }

    @ApiOperation("当前用户学习课程和练习记录")
    @GetMapping("/study_records")
    public BaseRsp<StudyRecord> courseAndPracticeRecords() {
        String userId = UserInfoContext.getUserContext().getUserId();

        return personCommentService.studyRecords(userId);

    }

    @ApiOperation("当前用户30天内课程、实验报告数量")
    @GetMapping("/study_period_records")
    public BaseRsp<BrokenLine> courseAndPracticeRecordsByMonth(@RequestParam("start_time") String startTime,
                                                               @RequestParam("end_time") String endTime) {

        boolean startValidate = TimeUtil.isValidDate(startTime);
        boolean endValidate = TimeUtil.isValidDate(endTime);
        if (!startValidate || !endValidate) {
            throw new InvalidParamException("时间格式不正确！");
        }
        String userId = UserInfoContext.getUserContext().getUserId();

        return personCommentService.studyPeriodRecords(userId,TimeUtil.toDate1(startTime), TimeUtil.toDate1(endTime));

    }


}