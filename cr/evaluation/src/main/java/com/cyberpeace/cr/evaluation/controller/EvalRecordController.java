package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.service.EvalRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "靶场测评-评估")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/record")
public class EvalRecordController {
    @Autowired
    private EvalRecordService evalRecordService;

    @ApiOperation("获取靶场测评实例评估（分页）")
    @GetMapping("/list")
    public BaseRsp<ListRsp<EvalRecord>> list(EvalRecordListReq queryParams) {
        return new BaseRsp<>(evalRecordService.pageList(
                queryParams.toPageParam(),
                queryParams.getRoomId(),
                queryParams.getStatus(),
                queryParams.getPattern()
        ));
    }

    @ApiOperation("创建评估")
    @PostMapping("")
    public BaseRsp<EvalRecordRsp> create(@RequestBody EvalRecordAddReq requestData) {
        return new BaseRsp<>(evalRecordService.create(requestData));
    }

    @ApiOperation("更新评估")
    @PatchMapping("")
    public BaseRsp<EvalRecordRsp> update(@RequestBody EvalRecordAddReq requestData) {
        return new BaseRsp<>(evalRecordService.update(requestData));
    }

    @ApiOperation("获取单个评估")
    @GetMapping("")
    public BaseRsp<EvalRecordRsp> get(@RequestParam("resource_id") String recordId) {
        return new BaseRsp<>(evalRecordService.get(recordId));
    }

    @ApiOperation("评估任务删除")
    @DeleteMapping("")
    public BaseRsp<Boolean> delete(@RequestBody BatchDeleteReq requestData) {
        return new BaseRsp<>(evalRecordService.delete(requestData.getIds()));
    }
}
