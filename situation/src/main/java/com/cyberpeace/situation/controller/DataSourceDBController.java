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
 * 数据源管理-数据库
 *
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

	/**
	 * 测试数据库连接（详情页）
	 *
	 * @param dataSourceRecordReq 数据库记录请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("连接测试-详情页")
	@GetMapping("connection/test")
	public BaseRsp<String> testDBConnection(DataSourceRecordReq dataSourceRecordReq) {
		return new BaseRsp<>(dataSourceService.testDBConnection(dataSourceRecordReq));
	}

	/**
	 * 测试数据库连接（列表页）
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("连接测试-列表页")
	@GetMapping("connection/resource/test")
	public BaseRsp<String> testDBConnection(@RequestParam("resource_id") @ApiParam(required = true,
			name = "resource_id") String resourceId) {
		return new BaseRsp<>(dataSourceService.testDBConnection(resourceId));
	}

	/**
	 * 获取数据库类型选项
	 *
	 * @return {@code BaseRsp<List<String>>}
	 */
	@ApiOperation("获取数据库类型选项")
	@GetMapping("type")
	public BaseRsp<List<String>> getDataSourceType() {
		return new BaseRsp<>(dataSourceService.getDataSourceType());
	}

	/**
	 * 获取数据库记录列表
	 *
	 * @param dataSourceRecordPageReq 数据库记录分页请求
	 * @return {@code BaseRsp<ListRsp<DataSourceRecordRes>>}
	 */
	@ApiOperation("数据库-列表查询")
	@GetMapping("page")
	public BaseRsp<ListRsp<DataSourceRecordRes>>
			getApiConnectPage(DataSourceRecordPageReq dataSourceRecordPageReq) {
		return new BaseRsp<>(dataSourceService.getApiConnectPage(dataSourceRecordPageReq));
	}

	/**
	 * 获取数据库记录详情
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<DataResourceRecordInfoRes>}
	 */
	@ApiOperation("数据库-单个查询")
	@GetMapping("")
	public BaseRsp<DataResourceRecordInfoRes>
			getDataSourceRecordInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id") String resourceId) {
		return new BaseRsp<>(dataSourceService.getDataSourceRecordInfo(resourceId));
	}

	/**
	 * 删除数据库记录
	 *
	 * @param dataSourceRecordDelReq 数据库记录删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@ApiOperation("数据库-删除")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteDataSourceRecord(
			@RequestBody @Validated DataSourceRecordDelReq dataSourceRecordDelReq) {
		return new BaseRsp<>(dataSourceService.deleteDataSourceRecord(dataSourceRecordDelReq));
	}

	/**
	 * 新增数据库记录
	 *
	 * @param dataSourceRecordReq 数据库记录请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("数据库-新增")
	@PostMapping("")
	public BaseRsp<String> createDataSourceRecord(
			@RequestBody @Validated(Create.class) DataSourceRecordReq dataSourceRecordReq) {
		return new BaseRsp<>(dataSourceService.createDataSourceRecord(dataSourceRecordReq));
	}

	/**
	 * 修改数据库记录
	 *
	 * @param dataSourceRecordReq 数据库记录请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("数据库-修改")
	@PatchMapping("")
	public BaseRsp<String> updateDataSourceRecord(
			@RequestBody @Validated(Update.class) DataSourceRecordReq dataSourceRecordReq) {
		return new BaseRsp<>(dataSourceService.updateDataSourceRecord(dataSourceRecordReq));
	}

	/**
	 * 获取数据库表列表
	 *
	 * @param resourceId 资源id
	 * @param name       数据库名称
	 * @return {@code BaseRsp<List<String>>}
	 */
	@ApiOperation("获取数据库表列表")
	@GetMapping("tables")
	public BaseRsp<List<String>> getDBTables(
			@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id") String resourceId,
			@RequestParam("name") @ApiParam(required = true, name = "name") String name) {
		return new BaseRsp<>(dataSourceService.getDBTables(resourceId, name));
	}

	/**
	 * 获取数据库库列表
	 *
	 * @param resourceId 数据库连接ID
	 * @return {@code BaseRsp<List<String>>}
	 */
	@ApiOperation("获取数据库库列表")
	@GetMapping("database")
	public BaseRsp<List<String>> getDBName(@RequestParam("resource_id") @ApiParam(required = true,
			name = "resource_id") String resourceId) {
		return new BaseRsp<>(dataSourceService.getDBName(resourceId));
	}

	/**
	 * 获取数据库SQL执行结果
	 *
	 * @param sql          sql
	 * @param resourceId   资源id
	 * @param dbName       数据库名称
	 * @param tableName    数据表名称
	 * @param customParams 自定义参数
	 * @return {@code BaseRsp<List<JSONObject>>}
	 */
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
