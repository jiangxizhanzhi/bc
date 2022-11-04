package com.cyberpeace.situation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.DataSourceRecordDelReq;
import com.cyberpeace.situation.controller.req.DataSourceRecordPageReq;
import com.cyberpeace.situation.controller.req.DataSourceRecordReq;
import com.cyberpeace.situation.controller.res.DataResourceRecordInfoRes;
import com.cyberpeace.situation.controller.res.DataSourceRecordRes;
import com.cyberpeace.situation.service.DataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author xuxz
 * @Date 28/10/2022 上午9:22
 */
@Api(tags = "数据源管理")
@RestController
@Slf4j
@RequestMapping("/datasource/")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    @ApiOperation("详情页数据源连接测试")
    @GetMapping("connection/test")
    public BaseRsp<String> testDBConnection(DataSourceRecordReq dataSourceRecordReq) {
        return new BaseRsp<>(dataSourceService.testDBConnection(dataSourceRecordReq));
    }

    @ApiOperation("数据源连接测试")
    @GetMapping("connection/resource/test")
    public BaseRsp<String> testDBConnection(@RequestParam("resource_id")
                                            @ApiParam(required = true, name = "resource_id") String resourceId) {
        return new BaseRsp<>(dataSourceService.testDBConnection(resourceId));
    }

    @ApiOperation("获取数据源类型列表")
    @GetMapping("type")
    public BaseRsp<List<String>> getDataSourceType() {
        return new BaseRsp<>(dataSourceService.getDataSourceType());
    }

    @ApiOperation("数据源列表")
    @GetMapping("page")
    public BaseRsp<ListRsp<DataSourceRecordRes>> getApiConnectPage(DataSourceRecordPageReq dataSourceRecordPageReq){
        return new BaseRsp<>(dataSourceService.getApiConnectPage(dataSourceRecordPageReq));
    }

    @ApiOperation("数据源详情")
    @GetMapping("")
    public BaseRsp<DataResourceRecordInfoRes> getDataSourceRecordInfo(@RequestParam("resource_id")
                                                                      @ApiParam(required = true, name = "resource_id") String resourceId){
        return new BaseRsp<>(dataSourceService.getDataSourceRecordInfo(resourceId));
    }

    @ApiOperation("删除数据源")
    @DeleteMapping("")
    public BaseRsp<Integer> deleteDataSourceRecord(@RequestBody @Validated DataSourceRecordDelReq dataSourceRecordDelReq){
        return new BaseRsp<>(dataSourceService.deleteDataSourceRecord(dataSourceRecordDelReq));
    }

    @ApiOperation("新增数据源")
    @PostMapping("")
    public BaseRsp<String> createDataSourceRecord(DataSourceRecordReq dataSourceRecordReq){
        return new BaseRsp<>(dataSourceService.createDataSourceRecord(dataSourceRecordReq));
    }

    @ApiOperation("更新数据源")
    @PatchMapping("")
    public BaseRsp<String> updateDataSourceRecord(DataSourceRecordReq dataSourceRecordReq){
        return new BaseRsp<>(dataSourceService.updateDataSourceRecord(dataSourceRecordReq));
    }
}
