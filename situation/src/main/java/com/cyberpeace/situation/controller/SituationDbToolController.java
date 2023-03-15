package com.cyberpeace.situation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.dao.entity.SituationDbTool;
import com.cyberpeace.situation.dao.entity.SituationDbToolCategory;
import com.cyberpeace.situation.dao.mapper.SituationDbToolCategoryMapper;
import com.cyberpeace.situation.dao.mapper.SituationDbToolMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(tags = "数据转换工具管理")
@RestController
@Slf4j
@RequestMapping("/situation/db/tool")
public class SituationDbToolController {

	@Autowired
	private SituationDbToolMapper situationDbToolMapper;

	@Autowired
	private SituationDbToolCategoryMapper situationDbToolCategoryMapper;

	@ApiOperation("查询数据转换工具类型列表")
	@GetMapping("/category/list")
	public BaseRsp<List<SituationDbToolCategory>> getSituationDbToolCategory(PageReqBase pageReqBase) {
		LambdaQueryWrapper<SituationDbToolCategory> queryWrapper = new LambdaQueryWrapper<>();
		String name = pageReqBase.getPattern();
		if(StringUtils.isNotEmpty(name)){
			queryWrapper.like(SituationDbToolCategory::getName, name);
		}
		List<SituationDbToolCategory> res = situationDbToolCategoryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	@ApiOperation("查询数据转换工具类型详情")
	@GetMapping("/category/")
	public BaseRsp<SituationDbToolCategory> getSituationDbToolCategoryDetail(@RequestParam("resourceId") @NotBlank String resourceId) {
		SituationDbToolCategory situationDbToolCategory = situationDbToolCategoryMapper.selectById(resourceId);
		if(null == situationDbToolCategory){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(situationDbToolCategory);
	}

	@ApiOperation("新增数据转换工具类型列表")
	@PostMapping("/category/")
	public BaseRsp<SituationDbToolCategory> addSituationDbToolCategory(@RequestBody @Validated AddCategoryReq req) {
		SituationDbToolCategory situationDbToolCategory = new SituationDbToolCategory();
		situationDbToolCategory.setName(req.getName());
		situationDbToolCategoryMapper.insert(situationDbToolCategory);
		return new BaseRsp<>(situationDbToolCategory);
	}

	@ApiOperation("删除数据转换工具类型")
	@DeleteMapping("/category/")
	public BaseRsp deleteSituationDbToolCategory(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();
		List<SituationDbToolCategory> dbToolCategories = situationDbToolCategoryMapper.selectBatchIds(resourceIds);
		if (dbToolCategories.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		situationDbToolCategoryMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}


	@ApiOperation("修改数据转换工具类型")
	@PatchMapping("/category/")
	public BaseRsp updateSituationDbToolCategory(@RequestBody @Validated AddCategoryReq req) {
		String resourceId = req.getResourceId();
		SituationDbToolCategory situationDbToolCategory = situationDbToolCategoryMapper.selectById(resourceId);
		if(null == situationDbToolCategory){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		if(!Objects.equals(situationDbToolCategory.getName(), req.getName())){
			LambdaQueryWrapper<SituationDbToolCategory> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(SituationDbToolCategory::getName, req.getName());
			SituationDbToolCategory toolCategory = situationDbToolCategoryMapper.selectOne(queryWrapper);
			if(null != toolCategory){
				throw new BusinessException(ErrorCode.NAME_DUPLICATE);
			}
		}
		SituationDbToolCategory category = new SituationDbToolCategory();
		BeanUtils.copyProperties(req ,category);
		situationDbToolCategoryMapper.updateById(category);
		return new BaseRsp<>();
	}


	@ApiOperation("查询数据转换工具详情")
	@GetMapping("")
	public BaseRsp<SituationDbTool> getSituationDbToolDetail(@RequestParam("resourceId") @NotBlank String resourceId) {
		SituationDbTool situationDbTool = situationDbToolMapper.selectById(resourceId);
		if(null == situationDbTool){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(situationDbTool);
	}

	@ApiOperation("新增数据转换工具")
	@PostMapping("")
	public BaseRsp<SituationDbTool>
			setSituationDbTool(@RequestBody @Validated(Create.class) SituationDbToolAddReq dbToolAddReq) {

		SituationDbTool situationDbTool = BeanUtils.copy(dbToolAddReq,
				SituationDbTool.class);
		situationDbTool.setToolType(dbToolAddReq.getToolCategory());
		String type = situationDbTool.getToolType();
		SituationDbToolCategory situationDbToolCategory = situationDbToolCategoryMapper.selectById(type);
		if(null == situationDbToolCategory){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		String userId = UserInfoContext.getUserContext().getUserId();
		situationDbTool.setCreateUserId(userId);
		try{
			situationDbToolMapper.insert(situationDbTool);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(situationDbTool);
	}

	@ApiOperation("查询数据转换工具列表")
	@GetMapping("list")
	public BaseRsp<List<SituationDbTool>>
			getSituationDbTool(SituationDbToolFilterReq req) {
		LambdaQueryWrapper<SituationDbTool> queryWrapper = new LambdaQueryWrapper<>();
		if (null != req.getName()){
			queryWrapper.like(SituationDbTool::getName, req.getName());

		}
		if (null != req.getCategory() && !req.getCategory().equals("")){
			queryWrapper.eq(SituationDbTool::getToolType, req.getCategory());
		}
		queryWrapper.orderByDesc(SituationDbTool::getCreateTime);
		List<SituationDbTool> res = situationDbToolMapper.selectList(queryWrapper);

		List<SituationDbToolCategory> dbToolCategories = situationDbToolCategoryMapper.selectList(null);
		Map<String, String> categoryMap = StreamApiUtils.toMap(dbToolCategories,
				SituationDbToolCategory::getResourceId, toolCategory -> toolCategory.getName());
		res.forEach(dbTool -> {
			String toolCategory = categoryMap.get(dbTool.getToolType());
			toolCategory = StringUtils.isEmpty(toolCategory) ? "" : toolCategory;
			dbTool.setToolType(toolCategory);
		});

		return new BaseRsp<>(res);
	}

	@ApiOperation("删除数据转换工具")
	@DeleteMapping("")
	public BaseRsp deleteSituationDbTool(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();
		List<SituationDbTool> twoDimensionalComponents = situationDbToolMapper.selectBatchIds(resourceIds);
		if (twoDimensionalComponents.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		situationDbToolMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	@ApiOperation("修改数据转换工具")
	@PatchMapping("")
	public BaseRsp patchSituationDbTool(@RequestBody @Validated(Update.class) SituationDbToolUpdateReq twoDimensionalComponentsUpdateReq) {
		SituationDbTool patchObj = BeanUtils.copy(twoDimensionalComponentsUpdateReq, SituationDbTool.class);
		SituationDbTool twoDimensionalComponents = situationDbToolMapper.selectById(twoDimensionalComponentsUpdateReq.getResourceId());
		if(null == twoDimensionalComponents){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		LambdaQueryWrapper<SituationDbTool> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SituationDbTool::getName, twoDimensionalComponentsUpdateReq.getName());
		SituationDbTool one = situationDbToolMapper.selectOne(queryWrapper);
		if (null != one && !Objects.equals(one.getResourceId(), twoDimensionalComponentsUpdateReq.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		situationDbToolMapper.updateById(patchObj);
		return new BaseRsp<>();
	}
}