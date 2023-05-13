package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlgorithmRefReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmRefMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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


	/**
	 * 编辑算法
	 * @param req
	 * @return
	 */
	@PutMapping("")
	@ApiOperation("编辑算法")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_ALGORITHM)
	public BaseRsp<EvalAlgorithmRef> editAlgorithm(@RequestBody EvalAlgorithmRefReq req) {

		String evalAlgorithmId = req.getResourceId();
		EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMapper.selectById(evalAlgorithmId);
		if(null == evalAlgorithmRef){
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
	public BaseRsp<EvalAlgorithmRef> getEvalAlgorithmRef(
			@ApiParam(name = "resourceId", value = "id", required = true) String resourceId) {

		EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMapper.selectById(resourceId);
		if(null == evalAlgorithmRef){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp(evalAlgorithmRef);
	}
}
