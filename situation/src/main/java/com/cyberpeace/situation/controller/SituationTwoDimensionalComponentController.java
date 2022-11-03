package com.cyberpeace.situation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.dao.entity.*;
import com.cyberpeace.situation.dao.mapper.*;
import com.cyberpeace.situation.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Api(tags = "组件库")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/two_dimensional_component")
public class SituationTwoDimensionalComponentController {

	@Autowired
	private TwoDimensionalComponentsMapper twoDimensionalComponentsMapper;

	@Autowired
	private TwoDimensionalComponentsService twoDimensionalComponentsService;

	@ApiOperation("新增二维组件")
	@PostMapping("")
	public BaseRsp<TwoDimensionalComponents>
			setTwoDimensionalComponents(@RequestBody @Validated(Create.class) TwoDimensionalComponentsAddReq twoDimensionalComponentsAddReq) {

		TwoDimensionalComponents twoDimensionalComponents = BeanUtils.copy(twoDimensionalComponentsAddReq,
				TwoDimensionalComponents.class);
		String userId = UserInfoContext.getUserContext().getUserId();
		twoDimensionalComponents.setCreateUserId(userId);
		try{
			twoDimensionalComponentsMapper.insert(twoDimensionalComponents);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(twoDimensionalComponents);
	}

	@ApiOperation("查询二维组件")
	@GetMapping("list")
	public BaseRsp<List<TwoDimensionalComponents>>
			getTwoDimensionalComponents(TwoDimensionalComponentsFilterReq twoDimensionalComponentsFilterReq) {
		LambdaQueryWrapper<TwoDimensionalComponents> queryWrapper = new LambdaQueryWrapper<>();
		if (null != twoDimensionalComponentsFilterReq.getName()){
			queryWrapper.like(TwoDimensionalComponents::getName, twoDimensionalComponentsFilterReq.getName());

		}
		if (null != twoDimensionalComponentsFilterReq.getType()){
			queryWrapper.eq(TwoDimensionalComponents::getType, twoDimensionalComponentsFilterReq.getType());
		}
		List<TwoDimensionalComponents> res = twoDimensionalComponentsMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	@ApiOperation("查询二维组件类型详情")
	@GetMapping("/category")
	public BaseRsp<TwoDimensionalComponents>
	getThreeDimensionalComponentsCategoryDetail(@RequestParam("resourceId") @NotBlank String resourceId) {

		TwoDimensionalComponents twoDimensionalComponents = twoDimensionalComponentsMapper.selectById(resourceId);
		if(null == twoDimensionalComponents){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(twoDimensionalComponents);
	}

	@ApiOperation("删除二维组件")
	@DeleteMapping("")
	public BaseRsp deleteTwoDimensionalComponents(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();
		List<TwoDimensionalComponents> twoDimensionalComponents = twoDimensionalComponentsMapper.selectBatchIds(resourceIds);
		if (twoDimensionalComponents.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		twoDimensionalComponentsMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	@ApiOperation("修改二维组件")
	@PatchMapping("")
	public BaseRsp patchTwoDimensionalComponents(@Validated(Update.class) TwoDimensionalComponentsAddReq twoDimensionalComponentsAddReq) {
		TwoDimensionalComponents patchObj = BeanUtils.copy(twoDimensionalComponentsAddReq, TwoDimensionalComponents.class);
		TwoDimensionalComponents twoDimensionalComponents = twoDimensionalComponentsMapper.selectById(twoDimensionalComponentsAddReq.getResourceId());
		if(null == twoDimensionalComponents){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}


		LambdaQueryWrapper<TwoDimensionalComponents> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(TwoDimensionalComponents::getName, twoDimensionalComponentsAddReq.getName());
		TwoDimensionalComponents one = twoDimensionalComponentsService.getOne(queryWrapper);
		if (!Objects.equals(one.getResourceId(), twoDimensionalComponentsAddReq.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		twoDimensionalComponentsMapper.updateById(patchObj);
		return new BaseRsp<>();
	}

}