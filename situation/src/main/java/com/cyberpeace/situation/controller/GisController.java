package com.cyberpeace.situation.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.dao.entity.GisMark;
import com.cyberpeace.situation.dao.mapper.GisMarkMapper;
import com.cyberpeace.situation.service.context.GisMarkContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "GIS接口")
@RestController
@RequestMapping("/situation/gis")
public class GisController {

	@Autowired
	private GisMarkMapper gisMarkMapper;

	@ApiOperation("新增标绘配置")
	@PostMapping("/mark/")
	public BaseRsp<GisMarkContext> setConfig(@RequestBody GisMarkContext gisMarkContext) {
		return new BaseRsp<>(addGisMark(gisMarkContext));
	}

	@ApiOperation("查询标绘配置")
	@GetMapping("/mark/")
	public BaseRsp<GisMarkContext> getConfig(@RequestParam("mark_id") @ApiParam(required = true,
			name = "mark_id", value = "标绘ID") String markId) {
		GisMark gisMark = gisMarkMapper.selectById(markId);
		return new BaseRsp<>(BeanUtils.copy(gisMark, GisMarkContext.class));
	}

	@ApiOperation("更新标绘配置")
	@PatchMapping("/mark/")
	public BaseRsp<GisMarkContext> updateConfig(@RequestBody GisMarkContext gisMarkContext) {

		// 软删除原标绘数据
		deleteMark(gisMarkContext.getMarkId());
		return new BaseRsp<>(addGisMark(gisMarkContext));
	}

	@ApiOperation("删除标绘配置")
	@DeleteMapping("/mark/")
	public BaseRsp<Boolean> deleteConfig(@RequestParam("mark_id") @ApiParam(required = true,
			name = "mark_id", value = "标绘ID") String markId) {
		deleteMark(markId);
		return new BaseRsp<>(true);
	}

	@ApiOperation("查询大屏标绘配置")
	@GetMapping("/mark/list/")
	public BaseRsp<List<GisMarkContext>> getConfigList(
			@RequestParam("dashboard_id") @ApiParam(required = true, name = "dashboard_id",
					value = "大屏ID") String dashboardId,
			@RequestParam(value = "end_time", required = false) @ApiParam(required = false,
					name = "end_time",
					value = "结束时间") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
		List<GisMarkContext> gisMarkList = gisMarkMapper.getGisMarkList(dashboardId, endTime);
		return new BaseRsp<>(gisMarkList);
	}

	public GisMarkContext addGisMark(GisMarkContext gisMarkContext) {
		GisMark gisMark = new GisMark();
		gisMark.setDashboardId(gisMarkContext.getDashboardId());
		gisMark.setConfig(JsonUtil.toJsonString(gisMarkContext.getMarkConfig()));
		gisMarkMapper.insert(gisMark);
		return BeanUtils.copy(gisMark, GisMarkContext.class);
	}

	public void deleteMark(String markId) {
		GisMark gisMark = gisMarkMapper.selectById(markId);
		gisMark.setDeleteTime(new Date());
		gisMarkMapper.updateById(gisMark);
	}
}