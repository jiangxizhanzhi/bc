package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlgorithmRefReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalAlgorithmRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmRefMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexRefMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefMapper;
import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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


	/**
	 * 编辑算法
	 * @param req
	 * @return
	 */
	@PutMapping("")
	@ApiOperation("编辑算法")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	public BaseRsp<EvalAlgorithmRef> editAlgorithm(@RequestBody EvalAlgorithmRefReq req) {

		String evalAlgorithmId = req.getResourceId();
		EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMapper.selectById(evalAlgorithmId);
		if(null == evalAlgorithmRef){
			log.error("Resource not found! evalAlgorithmId = [{}]", evalAlgorithmId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		if(req.getMode().equals(EvalAlgorithmRef.Mode.FORMAT_ALGORITHM.getCode())){
			evalAlgorithmRef.setMatrixData(req.getMatrixData());
		}
		evalAlgorithmRef.setCode(req.getCode());
		evalAlgorithmRefMapper.updateById(evalAlgorithmRef);


		return new BaseRsp(evalAlgorithmRef);
	}


	@ApiOperation("查询算法")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_ALGORITHM)
	public BaseRsp<EvalAlgorithmRsp> getEvalAlgorithmRef(
			@ApiParam(name = "resourceId", value = "id", required = true) String resourceId,
			@ApiParam(name = "type", value = "算法类型", required = true) @RequestParam(name = "type") Integer type) {

		EvalAlgorithmRsp evalAlgorithmRsp;
		EvalIndexRef evalIndexRef = evalIndexRefMapper.selectById(resourceId);
		if(EnumUtils.isValidEnum(EvalAlgorithm.Type.class, type.toString())){
			if(type.equals(EvalAlgorithm.Type.AGGREGATING_ALGORITHM.getCode())){
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
		LambdaQueryWrapper<EvalVariableRef> query =  new LambdaQueryWrapper<>();
		evalAlgorithmRsp = BeanUtils.copy(evalIndexRef, EvalAlgorithmRsp.class);
		evalAlgorithmRsp.setEvalAlgorithmId(evalIndexRef.getResourceId());
		String params = "";
		if(type.equals(EvalAlgorithm.Type.SCORING_ALGORITHM.getCode())){
			query.eq(EvalVariableRef::getMarkAlgorithmId, resourceId);
			EvalVariableRef evalVariableRef = evalVariableRefMapper.selectOne(query);
			params = evalVariableRef.getMarkParams();
		} else if(type.equals(EvalAlgorithm.Type.FORMAT_ALGORITHM.getCode())){
			query.eq(EvalVariableRef::getFormatAlgorithmId, resourceId);
			EvalVariableRef evalVariableRef = evalVariableRefMapper.selectOne(query);
			params = evalVariableRef.getFormatParams();
		} else if(type.equals(EvalAlgorithm.Type.MATCHING_ALGORITHM.getCode())){
			query.eq(EvalVariableRef::getMatchAlgorithmId, resourceId);
			EvalVariableRef evalVariableRef = evalVariableRefMapper.selectOne(query);
			params = evalVariableRef.getMatchParams();
		}
		evalAlgorithmRsp.setParams(
				JsonUtil.toJsonObjectArray(params, AlgorithmParamContext.class));
		evalAlgorithmRsp.setParamsNum(evalAlgorithmRsp.getParams().size());

		return new BaseRsp<>(evalAlgorithmRsp);
	}

}
