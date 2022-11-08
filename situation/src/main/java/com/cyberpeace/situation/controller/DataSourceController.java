package com.cyberpeace.situation.controller;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
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
@Api(tags = "数据库管理")
@RestController
@Slf4j
@RequestMapping("/datasource/")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    @ApiOperation("详情页数据库连接测试")
    @GetMapping("connection/test")
    public BaseRsp<String> testDBConnection(DataSourceRecordReq dataSourceRecordReq) {
        return new BaseRsp<>(dataSourceService.testDBConnection(dataSourceRecordReq));
    }

    @ApiOperation("数据库连接测试")
    @GetMapping("connection/resource/test")
    public BaseRsp<String> testDBConnection(@RequestParam("resource_id")
                                            @ApiParam(required = true, name = "resource_id") String resourceId) {
        return new BaseRsp<>(dataSourceService.testDBConnection(resourceId));
    }

    @ApiOperation("获取数据库类型列表")
    @GetMapping("type")
    public BaseRsp<List<String>> getDataSourceType() {
        return new BaseRsp<>(dataSourceService.getDataSourceType());
    }

    @ApiOperation("数据库列表")
    @GetMapping("page")
    public BaseRsp<ListRsp<DataSourceRecordRes>> getApiConnectPage(DataSourceRecordPageReq dataSourceRecordPageReq){
        return new BaseRsp<>(dataSourceService.getApiConnectPage(dataSourceRecordPageReq));
    }

    @ApiOperation("数据库详情")
    @GetMapping("")
    public BaseRsp<DataResourceRecordInfoRes> getDataSourceRecordInfo(@RequestParam("resource_id")
                                                                      @ApiParam(required = true, name = "resource_id") String resourceId){
        return new BaseRsp<>(dataSourceService.getDataSourceRecordInfo(resourceId));
    }

    @ApiOperation("删除数据库")
    @DeleteMapping("")
    public BaseRsp<Integer> deleteDataSourceRecord(@RequestBody @Validated DataSourceRecordDelReq dataSourceRecordDelReq){
        return new BaseRsp<>(dataSourceService.deleteDataSourceRecord(dataSourceRecordDelReq));
    }

    @ApiOperation("新增数据库")
    @PostMapping("")
    public BaseRsp<String> createDataSourceRecord(@RequestBody @Validated(Create.class) DataSourceRecordReq dataSourceRecordReq){
        return new BaseRsp<>(dataSourceService.createDataSourceRecord(dataSourceRecordReq));
    }

    @ApiOperation("更新数据库")
    @PatchMapping("")
    public BaseRsp<String> updateDataSourceRecord(@RequestBody @Validated(Update.class) DataSourceRecordReq dataSourceRecordReq){
        return new BaseRsp<>(dataSourceService.updateDataSourceRecord(dataSourceRecordReq));
    }

    @ApiOperation("获取表信息")
    @GetMapping("tables")
    public BaseRsp<List<String>> getDBTables(@RequestParam("resource_id")
                                                      @ApiParam(required = true, name = "resource_id") String resourceId,
                                                      @RequestParam("name")
                                                      @ApiParam(required = true, name = "name") String name){
        return new BaseRsp<>(dataSourceService.getDBTables(resourceId, name));
    }

    @ApiOperation("获取库信息")
    @GetMapping("database")
    public BaseRsp<List<String>> getDBName(@RequestParam("resource_id")
                                                      @ApiParam(required = true, name = "resource_id") String resourceId){
        return new BaseRsp<>(dataSourceService.getDBName(resourceId));
    }
}
