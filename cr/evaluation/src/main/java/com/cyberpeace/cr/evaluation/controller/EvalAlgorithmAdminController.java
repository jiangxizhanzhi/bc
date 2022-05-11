package com.cyberpeace.cr.evaluation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlgorithmListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlgorithmReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalAlgorithmRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "后台-测试靶场-算法管理")
@RestController
@RequestMapping("/cr/eval/algorithm")
@RequiresAuthentication
public class EvalAlgorithmAdminController {

	@Autowired
	private EvalAlgorithmService evalAlgorithmService;

	@Autowired
	private EvalAlgorithmMapper evalAlgorithmMapper;
	/**
	 * 新增算法
	 * @param req
	 * @return
	 */
	@PostMapping("")
	@ApiOperation("新增算法")
	public BaseRsp<EvalAlgorithm> addAlgorithm(@RequestBody EvalAlgorithmReq req) {

		EvalAlgorithm evalAlgorithm = convertEvalAlgorithmReqToEvalAlgorithm(req);

		evalAlgorithmService.save(evalAlgorithm);

		return new BaseRsp(evalAlgorithm);
	}

	private EvalAlgorithm convertEvalAlgorithmReqToEvalAlgorithm(EvalAlgorithmReq req) {

		validateReq(req);

		EvalAlgorithm evalAlgorithm = BeanUtils.copy(req, EvalAlgorithm.class);

		evalAlgorithm.setResourceId(req.getEvalAlgorithmId());
		evalAlgorithm.setParamsNum(req.getParams().size());
		evalAlgorithm.setParams(JsonUtil.arrayToString(req.getParams()));
		evalAlgorithm.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		return evalAlgorithm;
	}

	private void validateReq(EvalAlgorithmReq req) {
		String resourceId = req.getEvalAlgorithmId();

		// 校验名称长度
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		// 校验名称重复
		if (evalAlgorithmMapper.existByName(req.getName())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 校验描述长度
		CrCommonValidator.validateLength(req.getDescription(), CrCommonConstants.ZERO_STR_LEN,
				CrCommonConstants.MAX_DESC_LEN, CrBusinessErrorCode.ILLEGAL_DESC_LEN);

	}

	/**
	 * 编辑算法
	 * @param req
	 * @return
	 */
	@PutMapping("")
	@ApiOperation("编辑算法")
	public BaseRsp<String> editAlgorithm(@RequestBody EvalAlgorithmReq req) {

		EvalAlgorithm originEvalAlgorithm = evalAlgorithmService.getById(req.getEvalAlgorithmId());

		if (null == originEvalAlgorithm || originEvalAlgorithm.getState() == 1) {
			log.info("EvalAlgorithm not found! evalAlgorithmId=[{}]", req.getEvalAlgorithmId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		EvalAlgorithm evalAlgorithm = convertEvalAlgorithmReqToEvalAlgorithm(req);

		Integer useNum = evalAlgorithmService
				.selectUsedNum(Lists.newArrayList(req.getEvalAlgorithmId()));
		// 如果已经使用过，需要备份原先的算法，新增一个新算法。
		if (useNum > 0) {

			evalAlgorithmService.updateStateById(evalAlgorithm.getResourceId());

			evalAlgorithm.setResourceId(null);
			evalAlgorithmService.save(evalAlgorithm);
		}
		// 如果没有使用过，更新算法。
		else {
			evalAlgorithmService.updateById(evalAlgorithm);

		}

		return new BaseRsp(evalAlgorithm);
	}

	/**
	 * 删除算法
	 * @param deleteReq
	 * @return
	 */

	@DeleteMapping("")
	@ApiOperation("批量删除")
	public BaseRsp batchDelete(@RequestBody @Validated BatchDeleteReq deleteReq) {

		Integer useNum = evalAlgorithmService.selectUsedNum(deleteReq.getIds());

		if (useNum > 0) {
			log.info("Algorithm Already Used! algorithmIds=[{}]",
					JsonUtil.arrayToString(deleteReq.getIds()));
			throw new BusinessException(CrBusinessErrorCode.ALGORITHM_ALREADY_USED);
		}

		return new BaseRsp(evalAlgorithmService.delete(deleteReq.getIds()));
	}

	/**
	 * 查询算法列表
	 * @return
	 */
	@GetMapping("/type")
	@ApiOperation("根据类型查询算法列表")
	public BaseRsp<List<EvalAlgorithmRsp>>
			algorithmList(@ApiParam(name = "type", value = "算法类型", required = true) String type) {

		Preconditions.checkArgument(StringUtils.isNotBlank(type), "type null");
		List<EvalAlgorithm> algorithms = evalAlgorithmService.selectByType(type);
		List<EvalAlgorithmRsp> rsp = algorithms.stream().map(evalAlgorithm -> {
			EvalAlgorithmRsp evalAlgorithmRsp = BeanUtils.copy(evalAlgorithm,
					EvalAlgorithmRsp.class);
			evalAlgorithmRsp.setEvalAlgorithmId(evalAlgorithm.getResourceId());
			evalAlgorithmRsp.setParams(JsonUtil.toJsonObjectArray(evalAlgorithm.getParams(),
					AlgorithmParamContext.class));

			return evalAlgorithmRsp;
		}).collect(Collectors.toList());

		return new BaseRsp(rsp);
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<EvalAlgorithmRsp>> list(EvalAlgorithmListReq listReq) {
		EvalListQuery listQuery = BeanUtils.copy(listReq, EvalListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		// 默认时间排序
		listQuery.setSort("-create_time");

		IPage<EvalAlgorithm> data = evalAlgorithmService.page(listQuery);

		data.convert(evalAlgorithm -> {
			EvalAlgorithmRsp evalAlgorithmRsp = BeanUtils.copy(evalAlgorithm,
					EvalAlgorithmRsp.class);
			evalAlgorithmRsp.setEvalAlgorithmId(evalAlgorithm.getResourceId());
			evalAlgorithmRsp.setParams(JsonUtil.toJsonObjectArray(evalAlgorithm.getParams(),
					AlgorithmParamContext.class));
			evalAlgorithmRsp.setParamsNum(evalAlgorithmRsp.getParams().size());

			return evalAlgorithmRsp;
		});
		return new BaseRsp(PageUtil.getListResult(data));
	}

	@ApiOperation("查询算法")
	@GetMapping("")
	public BaseRsp<EvalAlgorithmRsp> getEvalAlgorithmRsp(
			@ApiParam(name = "resource_id", value = "id", required = true) String resource_id) {

		EvalAlgorithm evalAlgorithm = evalAlgorithmService.getById(resource_id);
		if (null == evalAlgorithm) {
			log.info("EvalAlgorithm not found! evalAlgorithmId=[{}]", resource_id);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		EvalAlgorithmRsp evalAlgorithmRsp = BeanUtils.copy(evalAlgorithm, EvalAlgorithmRsp.class);
		evalAlgorithmRsp.setEvalAlgorithmId(evalAlgorithm.getResourceId());
		evalAlgorithmRsp.setParams(
				JsonUtil.toJsonObjectArray(evalAlgorithm.getParams(), AlgorithmParamContext.class));
		evalAlgorithmRsp.setParamsNum(evalAlgorithmRsp.getParams().size());

		return new BaseRsp(evalAlgorithmRsp);
	}
	/*
	
	@GetMapping("/list")
	@ApiOperation("分页查询算法列表")
	public ListRsp<EstimateAlgorithm> list(AlgorithmPageReq req) {
	req.validate();
	Page page = req.toPageParam();
	ListRsp<EstimateAlgorithm> algorithms = evalAlgorithmService
			.selectPageByType(req.getType(), page);
	return algorithms;
	}
	
	 
	
	*//**
		* 根据编号查询算法信息
		* @param algorithmId
		* @return
		*//*
			@GetMapping("/create/{algorithm_id}")
			@ApiOperation("根据编号查询算法信息")
			public BaseRsp<EstimateAlgorithmRsp> getAlgorithm(@ApiParam(name = "algorithm_id",
				value = "算法编号") @PathVariable("algorithm_id") String algorithmId) {
			
			Preconditions.checkArgument(StringUtils.isNotBlank(algorithmId), "algorithm_id null");
			EstimateAlgorithm estimateAlgorithm = estimateAlgorithmDao.selectById(algorithmId);
			if (null == estimateAlgorithm) {
				return new BaseRsp<>();
			}
			EstimateAlgorithmRsp algorithmRsp = new EstimateAlgorithmRsp();
			BeanUtils.copyProperties(estimateAlgorithm, algorithmRsp);
			List<AlgorithmParam> paramList = JSONArray.parseArray(estimateAlgorithm.getParams(),
					AlgorithmParam.class);
			algorithmRsp.setParams(paramList);
			BaseRsp<EstimateAlgorithmRsp> rsp = new BaseRsp<>();
			rsp.setData(algorithmRsp);
			return rsp;
			}
			*/
}
