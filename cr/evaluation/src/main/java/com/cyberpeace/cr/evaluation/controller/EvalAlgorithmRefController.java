package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlgorithmRefReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalAlgorithmEditRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalAlgorithmRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmRefMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexRefMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmRefService;
import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import com.cyberpeace.cr.evaluation.utils.AHPComputeWeight;
import com.cyberpeace.cr.evaluation.utils.MatrixData;
import com.cyberpeace.integratation.service.RemotePythonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * EVAL 算法 REF 控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Slf4j
@Api(tags = "前台-测试靶场-算法管理")
@RestController
@RequestMapping("/cr/eval/algorithm/ref")
@RequiresAuthentication
public class EvalAlgorithmRefController {

	/**
	 * eval 算法 ref mapper
	 */
	@Autowired
	private EvalAlgorithmRefMapper evalAlgorithmRefMapper;

	/**
	 * eval 索引 ref 映射器
	 */
	@Autowired
	private EvalIndexRefMapper evalIndexRefMapper;

	/**
	 * eval 变量 ref 映射器
	 */
	@Autowired
	private EvalVariableRefMapper evalVariableRefMapper;

	/**
	 * 远程蟒Service
	 */
	@Autowired
	private RemotePythonService remotePythonService;

	/**
	 * 评估算法裁判Service
	 */
	@Autowired
	private EvalAlgorithmRefService evalAlgorithmRefService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 执行者
	 */
	@Qualifier("taskExecutor")
	@Autowired
	private Executor executor;

	/**
	 * 编辑算法
	 *
	 * @param req
	 * @return
	 */
	@PutMapping("")
	@ApiOperation("编辑算法")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	public BaseRsp<EvalAlgorithmEditRsp> editAlgorithm(@RequestBody EvalAlgorithmRefReq req) {

		EvalAlgorithmEditRsp res = new EvalAlgorithmEditRsp();
		String evalAlgorithmId = req.getResourceId();
		EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMapper.selectById(evalAlgorithmId);
		BeanUtils.copyProperties(req, evalAlgorithmRef);
		if (null == evalAlgorithmRef) {
			log.error("Resource not found! evalAlgorithmId = [{}]", evalAlgorithmId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		if (req.getMode().equals(EvalAlgorithmRef.Mode.FORMAT_ALGORITHM.getCode())) {
			String matrixData = req.getMatrixData();
			LambdaQueryWrapper<EvalAlgorithmRef> queryFilter = new LambdaQueryWrapper<>();
			queryFilter.eq(EvalAlgorithmRef::getId, 1);
			EvalAlgorithmRef checkAhpMatrixDataRef = evalAlgorithmRefMapper.selectOne(queryFilter);
			checkMatrixData(req, res, checkAhpMatrixDataRef, matrixData);
			LambdaQueryWrapper<EvalIndexRef> query = new LambdaQueryWrapper<>();
			query.eq(EvalIndexRef::getAlgorithmId, evalAlgorithmId);
			EvalIndexRef evalIndexRef = evalIndexRefMapper.selectOne(query);
			evalIndexRef.setMatrixData(matrixData);
			evalAlgorithmRef.setMatrixData(matrixData);
			if (StringUtils.isNotBlank(req.getCode())) {
				evalAlgorithmRef.setCode(req.getCode());
			}
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					evalAlgorithmRefMapper.updateById(evalAlgorithmRef);
					evalIndexRefMapper.updateById(evalIndexRef);
				}
			});
		} else {
			evalAlgorithmRefMapper.updateById(evalAlgorithmRef);
		}
		BeanUtils.copyProperties(evalAlgorithmRef, res);
		return new BaseRsp(res);
	}

	/**
	 * 检查矩阵数据
	 *
	 * @param req              请求体
	 * @param res              结果
	 * @param evalAlgorithmRef eval 算法 ref
	 * @param matrixData       矩阵数据
	 */
	void checkMatrixData(EvalAlgorithmRefReq req, EvalAlgorithmEditRsp res,
			EvalAlgorithmRef evalAlgorithmRef, String matrixData) {
		LambdaQueryWrapper<EvalIndexRef> query = new LambdaQueryWrapper<>();
		query.eq(EvalIndexRef::getAlgorithmId, req.getResourceId());
		EvalIndexRef evalIndexRef = evalIndexRefMapper.selectOne(query);
		if (StringUtils.isBlank(matrixData)) {
			throw new BusinessException(CrBusinessErrorCode.MATRIX_DATA_EMPTY_ERROR);
		}
		try {
			double[][] formatMatrixData = MatrixData.getFormatMatrixData(matrixData);
			int N = formatMatrixData[0].length;
			double[] weight = new double[N];
			AHPComputeWeight instance = AHPComputeWeight.getInstance();
			instance.weight(formatMatrixData, weight, N);
			double cr = instance.getCR();
			res.setCr(Boolean.parseBoolean(String.valueOf(cr < 0.1)));
		} catch (RemoteServiceException e) {
			String msg;
			if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
				msg = String.format(
						BusinessException.getMessageByLanguage(
								CrBusinessErrorCode.AGG_ALGORITHM_RESULT_IS_INVALID),
						EvalIndexRef.Level.VARIABLE.getCode(), evalAlgorithmRef.getName());
			} else {
				msg = String.format(
						BusinessException
								.getMessageByLanguage(CrBusinessErrorCode.INDEX_EXECUTE_FAILED),
						EvalIndexRef.Level.VARIABLE.getCode(), evalAlgorithmRef.getName());
			}
			res.setMsg(msg);
			log.error(evalIndexRef.getName(), "[{}]consistency check failure.");
		}
	}

	/**
	 * 获取评估算法裁判
	 *
	 * @param resourceId 资源id
	 * @param type       类型
	 * @return {@code BaseRsp<EvalAlgorithmRsp> }
	 */
	@ApiOperation("查询算法")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_ALGORITHM)
	public BaseRsp<EvalAlgorithmRsp> getEvalAlgorithmRef(
			@ApiParam(name = "resourceId", value = "id",
					required = true) @RequestParam(name = "resourceId") String resourceId,
			@ApiParam(name = "type", value = "算法类型",
					required = true) @RequestParam(name = "type") Integer type) {

		EvalAlgorithmRsp evalAlgorithmRsp;
		if (EvalAlgorithm.Type.valid(type)) {
			if (type.equals(EvalAlgorithm.Type.AGGREGATING_ALGORITHM.getCode())) {
				LambdaQueryWrapper<EvalIndexRef> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(EvalIndexRef::getAlgorithmId, resourceId);
				List<EvalIndexRef> evalIndexRefs = evalIndexRefMapper.selectList(queryWrapper);
				EvalIndexRef evalIndexRef = evalIndexRefs.get(0);
				EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMapper.selectById(resourceId);
				if (null == evalAlgorithmRef) {
					log.error("Resource not found! resourceId = [{}]", resourceId);
					throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
				}
				evalAlgorithmRsp = BeanUtils.copy(evalAlgorithmRef, EvalAlgorithmRsp.class);

				String nextIds = evalIndexRef.getNextIds();
				List<String> next = JsonUtil.toJsonObjectArray(nextIds, String.class);
				List<Object> choices = JsonUtil.toJsonObjectArray(evalIndexRef.getMatrixData(),
						Object.class);
				List<Object> choicesNew = new ArrayList<>();
				for (String s : next) {
					for (Object obj : choices) {
						Map<String, Object> map = (Map<String, Object>) obj;
						String key = (String) map.get("key");
						if (s.equals(key)) {
							choicesNew.add(obj);
						}
					}
				}
				evalAlgorithmRsp.setMatrixData(JsonUtil.toJsonString(choicesNew));

				// evalAlgorithmRsp.setMatrixData(evalIndexRef.getMatrixData());
				evalAlgorithmRsp.setEvalAlgorithmId(evalAlgorithmRef.getResourceId());
				evalAlgorithmRsp.setParams(JsonUtil.toJsonObjectArray(
						evalIndexRef.getAlgorithmParams(), AlgorithmParamContext.class));
				evalAlgorithmRsp.setParamsNum(evalAlgorithmRsp.getParams().size());

				return new BaseRsp(evalAlgorithmRsp);
			}

		}
		EvalVariableRef evalVariableRef;
		LambdaQueryWrapper<EvalVariableRef> query = new LambdaQueryWrapper<>();
		String params = "";
		String algorithmId = "";
		if (type.equals(EvalAlgorithm.Type.SCORING_ALGORITHM.getCode())) {
			query.eq(EvalVariableRef::getMarkAlgorithmId, resourceId);
			evalVariableRef = evalVariableRefMapper.selectOne(query);
			params = evalVariableRef.getMarkParams();
			algorithmId = evalVariableRef.getMarkAlgorithmId();
		} else if (type.equals(EvalAlgorithm.Type.FORMAT_ALGORITHM.getCode())) {
			query.eq(EvalVariableRef::getFormatAlgorithmId, resourceId);
			evalVariableRef = evalVariableRefMapper.selectOne(query);
			params = evalVariableRef.getFormatParams();
			algorithmId = evalVariableRef.getFormatAlgorithmId();
		} else if (type.equals(EvalAlgorithm.Type.MATCHING_ALGORITHM.getCode())) {
			query.eq(EvalVariableRef::getMatchAlgorithmId, resourceId);
			evalVariableRef = evalVariableRefMapper.selectOne(query);
			params = evalVariableRef.getMatchParams();
			algorithmId = evalVariableRef.getMatchAlgorithmId();
		}
		EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMapper.selectById(algorithmId);
		evalAlgorithmRsp = BeanUtils.copy(evalAlgorithmRef, EvalAlgorithmRsp.class);
		evalAlgorithmRsp.setEvalAlgorithmId(algorithmId);
		evalAlgorithmRsp.setParams(JsonUtil.toJsonObjectArray(params, AlgorithmParamContext.class));
		evalAlgorithmRsp.setParamsNum(evalAlgorithmRsp.getParams().size());

		return new BaseRsp<>(evalAlgorithmRsp);
	}

}
