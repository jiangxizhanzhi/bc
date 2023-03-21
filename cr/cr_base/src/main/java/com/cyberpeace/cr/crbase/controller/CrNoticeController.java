package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.CrNoticePageReq;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeCreateReq;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeUpdateReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeDetailRsp;
import com.cyberpeace.cr.crbase.controller.rsp.NoticeListRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "训练公告")
@RestController
@Slf4j
@RequestMapping("/cr/notice")
@RequiresAuthentication
public class CrNoticeController {

    @ApiOperation("训练公告管理分页列表")
    @GetMapping("/admin/list")
    public BaseRsp<ListRsp<NoticeListRsp>> adminList(CrNoticePageReq crNoticePageReq) {
        return new BaseRsp<>();
    }

    @ApiOperation("训练公告列表")
    @GetMapping("/list")
    public BaseRsp<List<NoticeListRsp>> list(@ApiParam("训练业务ID") @RequestParam("room_id") String roomId) {
        return new BaseRsp<>();
    }

    @ApiOperation("训练公告详情")
    @GetMapping("")
    public BaseRsp<CrNoticeDetailRsp> get(@ApiParam("公告ID") @RequestParam("notice_id") String noticeId) {
        return new BaseRsp<>();
    }

    @ApiOperation("训练公告新增")
    @PostMapping("")
    public BaseRsp<CrNoticeDetailRsp> create(CrNoticeCreateReq crNoticeReq) {
        return new BaseRsp<>();
    }

    @ApiOperation("训练公告更新")
    @PatchMapping("")
    public BaseRsp<CrNoticeDetailRsp> update(CrNoticeUpdateReq crNoticeReq) {
        return new BaseRsp<>();
    }

    @ApiOperation("训练公告批量删除")
    @DeleteMapping("")
    public BaseRsp<Boolean> delete(BatchDeleteReq batchDeleteReq) {
        return new BaseRsp<>();
    }

    @ApiOperation("训练公告置顶")
    @PostMapping("/top")
    public BaseRsp<Boolean> top(BatchDeleteReq batchReq) {
        return new BaseRsp<>();
    }

    @ApiOperation("训练公告取消置顶")
    @PostMapping("/top/cancel")
    public BaseRsp<Boolean> topCancel(BatchDeleteReq batchReq) {
        return new BaseRsp<>();
    }

}
