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
import com.cyberpeace.situation.constant.auth.DataType;
import com.cyberpeace.situation.constant.auth.SubSystem;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationDeleteReq;
import com.cyberpeace.situation.controller.req.SituationDbAccessAuthorityAddReq;
import com.cyberpeace.situation.controller.res.SituationEnumRes;
import com.cyberpeace.situation.dao.entity.SituationDbAccessAuthority;
import com.cyberpeace.situation.dao.mapper.SituationDbAccessAuthorityMapper;
import com.cyberpeace.situation.service.SituationGisService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 数据库访问权限控制器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Api(tags = "数据库管理-访问权限管理")
@RestController
@RequiresAuthentication
@RequestMapping("/situation/db/access/authority")
public class DbAccessAuthorityController {

	@Autowired
	private SituationDbAccessAuthorityMapper situationDbAccessAuthorityMapper;

	@Autowired
	private SituationGisService situationGisService;

	/**
	 * 外部系统名称-列表查询
	 *
	 * @param pageReqBase 分页查询请求
	 * @return {@code BaseRsp<ListRsp<SituationEnumRes>>}
	 */
	@ApiOperation("外部系统名称-列表查询")
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

	/**
	 * 访问数据类型-列表查询
	 *
	 * @param pageReqBase 分页查询请求
	 * @return {@code BaseRsp<ListRsp<SituationEnumRes>>}
	 */
	@ApiOperation("访问数据类型-列表查询")
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

	/**
	 * 新增数据库访问权限
	 *
	 * @param req 访问权限新增请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("访问权限-新增")
	@PostMapping()
	public BaseRsp<String> addDbAccessAuthority(
			@RequestBody @Validated(Create.class) SituationDbAccessAuthorityAddReq req) {
		SituationDbAccessAuthority dbAccessAuthority = new SituationDbAccessAuthority();
		BeanUtils.copyProperties(req, dbAccessAuthority);
		int insert = situationDbAccessAuthorityMapper.insert(dbAccessAuthority);
		situationGisService.refreshAuthorizationIp();
		return new BaseRsp(dbAccessAuthority);
	}

	/**
	 * 删除数据库访问权限
	 *
	 * @param deleteReq 访问权限删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@ApiOperation("访问权限--删除")
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

	/**
	 * 数据库访问权限-列表查询
	 *
	 * @param pageReqBase 分页查询请求
	 * @return {@code BaseRsp<ListRsp<SituationDbAccessAuthority>>}
	 */
	@ApiOperation("访问权限-列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<SituationDbAccessAuthority>>
			getDbAccessAuthorities(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<SituationDbAccessAuthority> situationDbAccessAuthorityIPage = situationDbAccessAuthorityMapper
				.selectPage(page);
		return new BaseRsp<>(PageUtil.getListResult(situationDbAccessAuthorityIPage));
	}
}
