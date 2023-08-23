package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeCreateReq;
import com.cyberpeace.cr.crbase.controller.req.CrNoticePageReq;
import com.cyberpeace.cr.crbase.controller.req.CrNoticeUpdateReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeDetailRsp;
import com.cyberpeace.cr.crbase.controller.rsp.CrLabelGroup;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeListRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import com.cyberpeace.cr.crbase.service.CrNoticeService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "训练公告")
@RestController
@Slf4j
@RequestMapping("/cr/notice")
@RequiresAuthentication
public class CrNoticeController {

    @Autowired
    private CrNoticeService crNoticeService;

    @Autowired
    private CrTaskService crTaskService;

    @ApiOperation("训练公告管理公告对象查询")
    @GetMapping("/group")
    public BaseRsp<List<CrLabelGroup>> group(@ApiParam("训练实例ID") @RequestParam("room_id") String roomId) {
        return new BaseRsp<>(crNoticeService.noticeGroup(roomId));
    }

    @ApiOperation("训练公告管理分页列表")
    @GetMapping("/admin/list")
    public BaseRsp<ListRsp<CrNoticeListRsp>> adminList(CrNoticePageReq crNoticePageReq) {
        return new BaseRsp<>(crNoticeService.pageAdmin(crNoticePageReq.getRoomId(), crNoticePageReq.toPageParam()));
    }

    @ApiOperation("训练公告列表")
    @GetMapping("/list")
    public BaseRsp<List<CrNoticeListRsp>> list(@ApiParam("训练实例ID") @RequestParam("room_id") String roomId) {
        return new BaseRsp<>(crNoticeService.page(roomId));
    }

    @ApiOperation("训练公告详情")
    @GetMapping("")
    public BaseRsp<CrNoticeDetailRsp> get(@ApiParam("公告ID") @RequestParam("notice_id") String noticeId) {
        return new BaseRsp<>(crNoticeService.get(noticeId));
    }

    @ApiOperation("公告管理（发布公告、公告管理）")
    @PostMapping("")
    public BaseRsp<CrNoticeDetailRsp> create(@RequestBody CrNoticeCreateReq crNoticeReq) {
        return new BaseRsp<>(crNoticeService.create(crNoticeReq));
    }

    @ApiOperation("训练公告更新")
    @PatchMapping("")
    public BaseRsp<CrNoticeDetailRsp> update(@RequestBody CrNoticeUpdateReq crNoticeReq) {
        return new BaseRsp<>(crNoticeService.update(crNoticeReq));
    }

    @ApiOperation("训练公告批量删除")
    @DeleteMapping("")
    public BaseRsp<Boolean> delete(@RequestBody BatchDeleteReq batchDeleteReq) {
        List<CrNotice> crNoticeList = crNoticeService.listByIds(batchDeleteReq.getIds());
        String roomId;
        if (!crNoticeList.isEmpty()) {
            roomId = crNoticeList.get(0).getRoomId();
        } else {
            return new BaseRsp<>(Boolean.FALSE);
        }
        Boolean result = crNoticeService.removeByIds(batchDeleteReq.getIds());
        crTaskService.pushMissionWS(roomId, MissionWsCode.RELEASE_NOTICE);

        return new BaseRsp<>(result);
    }

    @ApiOperation("训练公告置顶")
    @PostMapping("/top")
    public BaseRsp<Boolean> top(@RequestBody BatchDeleteReq batchReq) {
        return new BaseRsp<>(crNoticeService.updateStatus(batchReq.getIds(), CrNotice.Status.TOP.getCode()));
    }

    @ApiOperation("训练公告取消置顶")
    @PostMapping("/top/cancel")
    public BaseRsp<Boolean> topCancel(@RequestBody BatchDeleteReq batchReq) {
        return new BaseRsp<>(crNoticeService.updateStatus(batchReq.getIds(), CrNotice.Status.NORMAL.getCode()));
    }

}
