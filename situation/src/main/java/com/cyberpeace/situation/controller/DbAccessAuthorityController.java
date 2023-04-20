package com.cyberpeace.situation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.constant.DataType;
import com.cyberpeace.situation.constant.SubSystem;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationDeleteReq;
import com.cyberpeace.situation.controller.req.SituationDbAccessAuthorityAddReq;
import com.cyberpeace.situation.controller.res.SituationEnumRes;
import com.cyberpeace.situation.dao.entity.SituationDbAccessAuthority;
import com.cyberpeace.situation.dao.mapper.SituationDbAccessAuthorityMapper;
import com.cyberpeace.situation.service.SituationGisService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "数据库管理-访问权限管理")
@RestController
@RequiresAuthentication
@RequestMapping("/situation/db/access/authority")
public class DbAccessAuthorityController {

	@Autowired
	private SituationDbAccessAuthorityMapper situationDbAccessAuthorityMapper;

	@Autowired
	private SituationGisService situationGisService;

	@ApiOperation("获取系统名称列表")
	@GetMapping("/sys/list")
	public BaseRsp<ListRsp<SituationEnumRes>> getSubSystems(PageReqBase pageReqBase) {
		ListRsp<SituationEnumRes> res = new ListRsp<>();
		List<SituationEnumRes> data = new ArrayList<>();
		Arrays.asList(SubSystem.values())
				.forEach(item -> data.add(new SituationEnumRes(item.getCode(), item.getName())));
		res.setList(data);
		res.setTotal(data.size());
		res.setCurrentPage(pageReqBase.getPage());
		res.setLastPage(res.getTotal() / pageReqBase.getPerPage() + 1);
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取数据类型列表")
	@GetMapping("/data_type/list")
	public BaseRsp<ListRsp<SituationEnumRes>> getDataTypes(PageReqBase pageReqBase) {
		ListRsp<SituationEnumRes> res = new ListRsp<>();
		List<SituationEnumRes> data = new ArrayList<>();
		Arrays.asList(DataType.values())
				.forEach(item -> data.add(new SituationEnumRes(item.getCode(), item.getName())));
		res.setList(data);
		res.setTotal(data.size());
		res.setCurrentPage(pageReqBase.getPage());
		res.setLastPage(res.getTotal() / pageReqBase.getPerPage() + 1);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增数据库访问权限")
	@PostMapping()
	public BaseRsp<String> addDbAccessAuthority(
			@RequestBody @Validated(Create.class) SituationDbAccessAuthorityAddReq req) {
		SituationDbAccessAuthority dbAccessAuthority = new SituationDbAccessAuthority();
		BeanUtils.copyProperties(req, dbAccessAuthority);
		int insert = situationDbAccessAuthorityMapper.insert(dbAccessAuthority);
		situationGisService.refreshAuthorizationIp();
		return new BaseRsp(dbAccessAuthority);
	}

	@ApiOperation("删除数据库访问权限")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteDbAccessAuthority(
			@RequestBody @Validated SituationComponentsAnimationDeleteReq deleteReq) {
		Integer res = null;
		if (!deleteReq.getResourceIds().isEmpty()) {
			List<SituationDbAccessAuthority> situationDbAccessAuthorities = situationDbAccessAuthorityMapper
					.selectBatchIds(deleteReq.getResourceIds());
			if (situationDbAccessAuthorities.isEmpty()) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
			}
			res = situationDbAccessAuthorityMapper.deleteBatchIds(deleteReq.getResourceIds());
		}
		situationGisService.refreshAuthorizationIp();
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取数据库访问权限列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<SituationDbAccessAuthority>>
			getDbAccessAuthorities(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<SituationDbAccessAuthority> situationDbAccessAuthorityIPage = situationDbAccessAuthorityMapper
				.selectPage(page);
		return new BaseRsp<>(PageUtil.getListResult(situationDbAccessAuthorityIPage));
	}
}
