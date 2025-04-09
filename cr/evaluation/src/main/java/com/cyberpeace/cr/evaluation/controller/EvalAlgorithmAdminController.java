package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评估算法管理Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Api(tags = "后台-测试靶场-算法管理")
@RestController
@RequestMapping("/cr/eval/algorithm")
@RequiresAuthentication
public class EvalAlgorithmAdminController {

	/**
	 * 评估算法Service
	 */
	@Autowired
	private EvalAlgorithmService evalAlgorithmService;

	/**
	 * 评估算法Mapper
	 */
	@Autowired
	private EvalAlgorithmMapper evalAlgorithmMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 新增算法
	 *
	 * @param req
	 * @return
	 */
	@PostMapping("")
	@ApiOperation("新增算法")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_ALGORITHM)
	public BaseRsp<EvalAlgorithm> addAlgorithm(@RequestBody EvalAlgorithmReq req) {

		EvalAlgorithm evalAlgorithm = convertEvalAlgorithmReqToEvalAlgorithm(req);
		evalAlgorithm.setBuiltin(EvalAlgorithm.Builtin.FORMAT_ALGORITHM.getCode());
		evalAlgorithmService.save(evalAlgorithm);

		return new BaseRsp(evalAlgorithm);
	}

	/**
	 * 转换评估算法请求体自评估算法
	 *
	 * @param req 请求体
	 * @return {@code EvalAlgorithm }
	 */
	protected EvalAlgorithm convertEvalAlgorithmReqToEvalAlgorithm(EvalAlgorithmReq req) {

		validateReq(req);

		EvalAlgorithm evalAlgorithm = BeanUtils.copy(req, EvalAlgorithm.class);

		evalAlgorithm.setResourceId(req.getEvalAlgorithmId());
		evalAlgorithm.setParamsNum(req.getParams().size());
		evalAlgorithm.setParams(JsonUtil.arrayToString(req.getParams()));
		evalAlgorithm.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalAlgorithm.setApplyType(JsonUtil.arrayToString(req.getApplyType()));

		return evalAlgorithm;
	}

	/**
	 * 验证请求体
	 *
	 * @param req 请求体
	 */
	protected void validateReq(EvalAlgorithmReq req) {
		String resourceId = req.getEvalAlgorithmId();

		// 校验名称长度
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		// 校验名称重复
		if (evalAlgorithmMapper.existByName(req.getName(), resourceId)) {
			log.error("Name duplicate! name = [{}]", req.getName());
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 校验描述长度
		CrCommonValidator.validateLength(req.getDescription(), CrCommonConstants.ZERO_STR_LEN,
				CrCommonConstants.MAX_DESC_LEN, CrBusinessErrorCode.ILLEGAL_DESC_LEN);

		List<AlgorithmParamContext> params = req.getParams();

		// 校验算法参数重名
		CrCommonValidator.checkStringListDuplicate(params.stream()
				.map(AlgorithmParamContext::getParamName).collect(Collectors.toList()),
				CrBusinessErrorCode.PARAM_NAME_DUPLICATE);

	}

	/**
	 * 编辑算法
	 *
	 * @param req
	 * @return
	 */
	@PutMapping("")
	@ApiOperation("编辑算法")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_ALGORITHM)
	public BaseRsp<String> editAlgorithm(@RequestBody EvalAlgorithmReq req) {

		String evalAlgorithmId = req.getEvalAlgorithmId();
		checkAlgorithmMode(evalAlgorithmId);
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
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					evalAlgorithmService.updateStateById(evalAlgorithm.getResourceId());

					evalAlgorithm.setResourceId(null);
					evalAlgorithmService.save(evalAlgorithm);
				}
			});

		}
		// 如果没有使用过，更新算法。
		else {
			evalAlgorithmService.updateById(evalAlgorithm);

		}

		return new BaseRsp(evalAlgorithm);
	}

	/**
	 * 检查算法模式
	 *
	 * @param evalAlgorithmId 评估算法id
	 */
	protected void checkAlgorithmMode(String evalAlgorithmId) {
		LambdaQueryWrapper<EvalAlgorithm> queryWrapper =  new LambdaQueryWrapper<>();
		queryWrapper.eq(EvalAlgorithm::getBuiltin, EvalAlgorithm.Builtin.SCORING_ALGORITHM.getCode());
		List<EvalAlgorithm> evalAlgorithms = evalAlgorithmMapper.selectList(queryWrapper);
		List<String> ids = StreamApiUtils.getColumn(evalAlgorithms, EvalAlgorithm::getResourceId);
		if(ids.contains(evalAlgorithmId)){
			log.error("The built-in algorithm forbids deletion! evalAlgorithmId = [{}]", evalAlgorithmId);
			throw new BusinessException(CrBusinessErrorCode.BUILTIN_ALGORITHM_FORBID_DELETE_ERROR);
		}
	}

	/**
	 * 删除算法
	 *
	 * @param deleteReq
	 * @return
	 */

	@DeleteMapping("")
	@ApiOperation("批量删除")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_ALGORITHM)
	public BaseRsp batchDelete(@RequestBody @Validated BatchDeleteReq deleteReq) {

		List<String> selectUsedAlgorithmIds = evalAlgorithmService
				.selectUsedAlgorithmIds(deleteReq.getIds());
		LambdaQueryWrapper<EvalAlgorithm> queryWrapper =  new LambdaQueryWrapper<>();
		queryWrapper.eq(EvalAlgorithm::getBuiltin, EvalAlgorithm.Builtin.SCORING_ALGORITHM.getCode());
		List<EvalAlgorithm> evalAlgorithms = evalAlgorithmMapper.selectList(queryWrapper);
		List<String> ids = StreamApiUtils.getColumn(evalAlgorithms, EvalAlgorithm::getResourceId);
		List<String> deleteReqIds = deleteReq.getIds();
		long count = deleteReqIds.stream().filter(ids::contains).count();
		if(count > 0){
			log.error("The built-in algorithm forbids deletion! deleteReqIds = [{}]", JsonUtil.toJsonString(deleteReqIds));
			throw new BusinessException(CrBusinessErrorCode.BUILTIN_ALGORITHM_FORBID_DELETE_ERROR);
		}
		// 如果都是未使用，直接删除，存在已使用，则先修改已使用算法的状态，再删除其他所有算法
		if (selectUsedAlgorithmIds.size() > 0) {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					evalAlgorithmService.updateStateByIds(selectUsedAlgorithmIds);
					evalAlgorithmMapper.deleteAll(deleteReq.getIds());

				}
			});
		} else {
			evalAlgorithmService.delete(deleteReq.getIds());
		}

		return new BaseRsp();
	}

	/**
	 * 获取评估算法响应
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<EvalAlgorithmRsp> }
	 */
	@ApiOperation("查询算法")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_ALGORITHM)
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
		evalAlgorithmRsp.setApplyType(JsonUtil.toJsonObjectArray(evalAlgorithm.getApplyType(), String.class));

		return new BaseRsp(evalAlgorithmRsp);
	}

	/**
	 * 根据类型查询算法列表
	 *
	 * @param type 类型
	 * @return
	 */
	@GetMapping("/type")
	@ApiOperation("根据类型查询算法列表")
	@RequiresPermissions(value = { MenuConstant.CMS_CR_EVALUATION_ALGORITHM,
			MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM,
			MenuConstant.CMS_CR_EVALUATION_INDICATOR }, logical = Logical.OR)
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

	/**
	 * 获取都
	 *
	 * @return {@code BaseRsp<List<EvalAlgorithmRsp>> }
	 */
	@ApiOperation("获取全部算法")
	@GetMapping("/getAll")
	@RequiresPermissions(value = { MenuConstant.CMS_CR_EVALUATION_ALGORITHM,
			MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM,
			MenuConstant.CMS_CR_EVALUATION_INDICATOR }, logical = Logical.OR)
	public BaseRsp<List<EvalAlgorithmRsp>> getAll() {

		List<EvalAlgorithm> data = evalAlgorithmMapper.getAll();

		List<EvalAlgorithmRsp> evalAlgorithmRsps = data.stream().map(evalAlgorithm -> {
			EvalAlgorithmRsp evalAlgorithmRsp = BeanUtils.copy(evalAlgorithm,
					EvalAlgorithmRsp.class);
			evalAlgorithmRsp.setEvalAlgorithmId(evalAlgorithm.getResourceId());
			evalAlgorithmRsp.setParams(JsonUtil.toJsonObjectArray(evalAlgorithm.getParams(),
					AlgorithmParamContext.class));
			evalAlgorithmRsp.setParamsNum(evalAlgorithmRsp.getParams().size());

			return evalAlgorithmRsp;
		}).collect(Collectors.toList());

		return new BaseRsp(evalAlgorithmRsps);
	}

	/**
	 * 列表
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<ListRsp<EvalAlgorithmRsp>> }
	 */
	@ApiOperation("列表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_ALGORITHM)
	public BaseRsp<ListRsp<EvalAlgorithmRsp>> list(EvalAlgorithmListReq listReq) {
		EvalListQuery listQuery = BeanUtils.copy(listReq, EvalListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		listQuery.setName(listReq.getPattern());

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
			evalAlgorithmRsp.setApplyType(JsonUtil.toJsonObjectArray(evalAlgorithm.getApplyType(),
					String.class));

			return evalAlgorithmRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));
	}

}
