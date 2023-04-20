package com.cyberpeace.situation.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.DataSourceRecordDelReq;
import com.cyberpeace.situation.controller.req.DataSourceRecordPageReq;
import com.cyberpeace.situation.controller.req.DataSourceRecordReq;
import com.cyberpeace.situation.controller.req.DataSourceReq;
import com.cyberpeace.situation.controller.res.DataResourceRecordInfoRes;
import com.cyberpeace.situation.controller.res.DataSourceRecordRes;
import com.cyberpeace.situation.service.DataSourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author xuxz
 * @Date 28/10/2022 上午9:22
 */
@Api(tags = "数据源管理-数据库")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/datasource/")
public class DataSourceDBController {

	@Autowired
	private DataSourceService dataSourceService;

	@ApiOperation("连接测试-详情页")
	@GetMapping("connection/test")
	public BaseRsp<String> testDBConnection(DataSourceRecordReq dataSourceRecordReq) {
		return new BaseRsp<>(dataSourceService.testDBConnection(dataSourceRecordReq));
	}

	@ApiOperation("连接测试-列表页")
	@GetMapping("connection/resource/test")
	public BaseRsp<String> testDBConnection(@RequestParam("resource_id") @ApiParam(required = true,
			name = "resource_id") String resourceId) {
		return new BaseRsp<>(dataSourceService.testDBConnection(resourceId));
	}

	@ApiOperation("获取数据库类型选项")
	@GetMapping("type")
	public BaseRsp<List<String>> getDataSourceType() {
		return new BaseRsp<>(dataSourceService.getDataSourceType());
	}

	@ApiOperation("数据库-列表查询")
	@GetMapping("page")
	public BaseRsp<ListRsp<DataSourceRecordRes>>
			getApiConnectPage(DataSourceRecordPageReq dataSourceRecordPageReq) {
		return new BaseRsp<>(dataSourceService.getApiConnectPage(dataSourceRecordPageReq));
	}

	@ApiOperation("数据库-单个查询")
	@GetMapping("")
	public BaseRsp<DataResourceRecordInfoRes>
			getDataSourceRecordInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id") String resourceId) {
		return new BaseRsp<>(dataSourceService.getDataSourceRecordInfo(resourceId));
	}

	@ApiOperation("数据库-删除")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteDataSourceRecord(
			@RequestBody @Validated DataSourceRecordDelReq dataSourceRecordDelReq) {
		return new BaseRsp<>(dataSourceService.deleteDataSourceRecord(dataSourceRecordDelReq));
	}

	@ApiOperation("数据库-新增")
	@PostMapping("")
	public BaseRsp<String> createDataSourceRecord(
			@RequestBody @Validated(Create.class) DataSourceRecordReq dataSourceRecordReq) {
		return new BaseRsp<>(dataSourceService.createDataSourceRecord(dataSourceRecordReq));
	}

	@ApiOperation("数据库-修改")
	@PatchMapping("")
	public BaseRsp<String> updateDataSourceRecord(
			@RequestBody @Validated(Update.class) DataSourceRecordReq dataSourceRecordReq) {
		return new BaseRsp<>(dataSourceService.updateDataSourceRecord(dataSourceRecordReq));
	}

	@ApiOperation("获取数据库表列表")
	@GetMapping("tables")
	public BaseRsp<List<String>> getDBTables(
			@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id") String resourceId,
			@RequestParam("name") @ApiParam(required = true, name = "name") String name) {
		return new BaseRsp<>(dataSourceService.getDBTables(resourceId, name));
	}

	@ApiOperation("获取数据库库列表")
	@GetMapping("database")
	public BaseRsp<List<String>> getDBName(@RequestParam("resource_id") @ApiParam(required = true,
			name = "resource_id") String resourceId) {
		return new BaseRsp<>(dataSourceService.getDBName(resourceId));
	}

	@ApiOperation("获取数据库SQL执行结果")
	@GetMapping("get/data")
	public BaseRsp<List<JSONObject>> getDataSourceData(
			@RequestParam(value = "sql", required = false) @ApiParam(name = "sql") String sql,
			@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id") String resourceId,
			@RequestParam(value = "db_name",
					required = false) @ApiParam(name = "db_name") String dbName,
			@RequestParam(value = "table_name",
					required = false) @ApiParam(name = "table_name") String tableName,
			@RequestParam(value = "custom_params",
					required = false) @ApiParam(name = "custom_params") String customParams) {

		return new BaseRsp<>(dataSourceService.getDataSourceData(
				new DataSourceReq(sql, resourceId, dbName, tableName, customParams)));
	}
}
