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
import com.cyberpeace.integratation.service.RemotePythonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


@Slf4j
@Api(tags = "前台-测试靶场-算法管理")
@RestController
@RequestMapping("/cr/eval/algorithm/ref")
@RequiresAuthentication
public class EvalAlgorithmRefController {

	@Autowired
	private EvalAlgorithmRefMapper evalAlgorithmRefMapper;

	@Autowired
	private EvalIndexRefMapper evalIndexRefMapper;

	@Autowired
	private EvalVariableRefMapper evalVariableRefMapper;

	@Autowired
	private RemotePythonService remotePythonService;

	@Autowired
	private EvalAlgorithmRefService evalAlgorithmRefService;


	/**
	 * 编辑算法
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
		if(null == evalAlgorithmRef){
			log.error("Resource not found! evalAlgorithmId = [{}]", evalAlgorithmId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		if(req.getMode().equals(EvalAlgorithmRef.Mode.FORMAT_ALGORITHM.getCode())){
			LambdaQueryWrapper<EvalIndexRef> query = new LambdaQueryWrapper<>();
			query.eq(EvalIndexRef::getAlgorithmId, req.getResourceId());
			EvalIndexRef evalIndexRef = evalIndexRefMapper.selectOne(query);
			try {
				List<Serializable> otherArguments = evalAlgorithmRefService.buildOtherArguments(evalIndexRef.getAlgorithmParams());
				Integer cr = remotePythonService.indexAhpPerform(req.getResourceId(), evalAlgorithmRef.getMatrixData(),
						evalAlgorithmRef.getCode(), null, otherArguments);
				if (null != cr) {
					res.setCr(Boolean.parseBoolean(String.valueOf(cr < 0.1)));
				}
			} catch (RemoteServiceException e) {
				String msg = "";
				if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
					msg = String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.AGG_ALGORITHM_RESULT_IS_INVALID), evalIndexRef.getLevel(), evalIndexRef.getName());
				} else {
					msg = String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.INDEX_EXECUTE_FAILED), evalIndexRef.getLevel(), evalIndexRef.getName());
				}
				res.setMsg(msg);
				log.error(evalIndexRef.getName(), "[{}]consistency check failure.");
			}
			evalAlgorithmRef.setMatrixData(req.getMatrixData());
		}
		evalAlgorithmRef.setCode(req.getCode());
		evalAlgorithmRefMapper.updateById(evalAlgorithmRef);
		BeanUtils.copyProperties(evalAlgorithmRef, res);
		return new BaseRsp(res);
	}


	@ApiOperation("查询算法")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_ALGORITHM)
	public BaseRsp<EvalAlgorithmRsp> getEvalAlgorithmRef(
			@ApiParam(name = "resourceId", value = "id", required = true) @RequestParam(name = "resourceId") String resourceId,
			@ApiParam(name = "type", value = "算法类型", required = true) @RequestParam(name = "type") Integer type) {

		EvalAlgorithmRsp evalAlgorithmRsp;
		if(EvalAlgorithm.Type.valid(type)){
			if(type.equals(EvalAlgorithm.Type.AGGREGATING_ALGORITHM.getCode())){
				LambdaQueryWrapper<EvalIndexRef> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(EvalIndexRef::getAlgorithmId, resourceId);
				List<EvalIndexRef> evalIndexRefs = evalIndexRefMapper.selectList(queryWrapper);
				EvalIndexRef evalIndexRef = evalIndexRefs.get(0);
				EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMapper.selectById(resourceId);
				if(null == evalAlgorithmRef){
					log.error("Resource not found! resourceId = [{}]", resourceId);
					throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
				}
				evalAlgorithmRsp = BeanUtils.copy(evalAlgorithmRef, EvalAlgorithmRsp.class);
				evalAlgorithmRsp.setEvalAlgorithmId(evalAlgorithmRef.getResourceId());
				evalAlgorithmRsp.setParams(
						JsonUtil.toJsonObjectArray(evalIndexRef.getAlgorithmParams(), AlgorithmParamContext.class));
				evalAlgorithmRsp.setParamsNum(evalAlgorithmRsp.getParams().size());

				return new BaseRsp(evalAlgorithmRsp);
			}

		}
		EvalVariableRef evalVariableRef;
		LambdaQueryWrapper<EvalVariableRef> query =  new LambdaQueryWrapper<>();
		String params = "";
		String algorithmId = "";
		if(type.equals(EvalAlgorithm.Type.SCORING_ALGORITHM.getCode())){
			query.eq(EvalVariableRef::getMarkAlgorithmId, resourceId);
			evalVariableRef = evalVariableRefMapper.selectOne(query);
			params = evalVariableRef.getMarkParams();
			algorithmId = evalVariableRef.getMarkAlgorithmId();
		} else if(type.equals(EvalAlgorithm.Type.FORMAT_ALGORITHM.getCode())){
			query.eq(EvalVariableRef::getFormatAlgorithmId, resourceId);
			evalVariableRef = evalVariableRefMapper.selectOne(query);
			params = evalVariableRef.getFormatParams();
			algorithmId = evalVariableRef.getFormatAlgorithmId();
		} else if(type.equals(EvalAlgorithm.Type.MATCHING_ALGORITHM.getCode())){
			query.eq(EvalVariableRef::getMatchAlgorithmId, resourceId);
			evalVariableRef = evalVariableRefMapper.selectOne(query);
			params = evalVariableRef.getMatchParams();
			algorithmId = evalVariableRef.getMatchAlgorithmId();
		}
		EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMapper.selectById(algorithmId);
		evalAlgorithmRsp = BeanUtils.copy(evalAlgorithmRef, EvalAlgorithmRsp.class);
		evalAlgorithmRsp.setEvalAlgorithmId(algorithmId);
		evalAlgorithmRsp.setParams(
				JsonUtil.toJsonObjectArray(params, AlgorithmParamContext.class));
		evalAlgorithmRsp.setParamsNum(evalAlgorithmRsp.getParams().size());

		return new BaseRsp<>(evalAlgorithmRsp);
	}

}
