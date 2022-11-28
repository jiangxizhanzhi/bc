package com.cyberpeace.situation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.constant.Color;
import com.cyberpeace.situation.constant.LineType;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.dao.entity.DynamicEffectsLibrary;
import com.cyberpeace.situation.dao.entity.DynamicEffectsLibraryCategory;
import com.cyberpeace.situation.dao.mapper.*;
import com.cyberpeace.situation.model.LocalType;
import com.cyberpeace.situation.service.*;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Api(tags = "组件库-动效库")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/dynamic_effect_library")
public class SituationDynamicEffectLibraryController {

	@Autowired
	private DynamicEffectsLibraryMapper dynamicEffectsLibraryMapper;

	@Autowired
	private DynamicEffectsLibraryCategoryMapper dynamicEffectsLibraryCategoryMapper;

	@Autowired
	private DynamicEffectsLibraryService dynamicEffectsLibraryService;

	@Autowired
	private DynamicEffectsLibraryCategoryService dynamicEffectsLibraryCategoryService;


	@ApiOperation("新增动效库")
	@PostMapping("")
	public BaseRsp<DynamicEffectsLibrary>
	setDynamicEffectsLibrary(@RequestBody @Validated(Create.class) DynamicEffectLibraryAddReq dynamicEffectLibraryAddReq) {

		DynamicEffectsLibrary dynamicEffectsLibrary = BeanUtils.copy(dynamicEffectLibraryAddReq,
				DynamicEffectsLibrary.class);
		String userId = UserInfoContext.getUserContext().getUserId();
		dynamicEffectsLibrary.setCreateUserId(userId);

		DynamicEffectsLibraryCategory category = dynamicEffectsLibraryCategoryMapper.selectById(dynamicEffectLibraryAddReq.getCategory());
		if(null == category){
			throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
		}

		try{
			dynamicEffectsLibraryMapper.insert(dynamicEffectsLibrary);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(dynamicEffectsLibrary);
	}

	@ApiOperation("查询动效库")
	@GetMapping("/list")
	public BaseRsp<List<DynamicEffectsLibrary>>
	getDynamicEffectsLibrary(DynamicEffectLibraryFilterReq dynamicEffectLibraryFilterReq) {
		LambdaQueryWrapper<DynamicEffectsLibrary> queryWrapper = new LambdaQueryWrapper<>();
		String name = dynamicEffectLibraryFilterReq.getName();
		if (null != name){
			queryWrapper.like(DynamicEffectsLibrary::getName, name);

		}
		String type = dynamicEffectLibraryFilterReq.getCategory();
		if (null != type) {
			DynamicEffectsLibraryCategory category = dynamicEffectsLibraryCategoryMapper.selectById(type);
			if (null == category) {
				throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
			}
		}

		if (null != type){
			queryWrapper.eq(DynamicEffectsLibrary::getCategory, type);
		}
		queryWrapper.orderByDesc(DynamicEffectsLibrary::getCreateTime);
		List<DynamicEffectsLibrary> res = dynamicEffectsLibraryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	@ApiOperation("查询动效库详情")
	@GetMapping("/")
	public BaseRsp<DynamicEffectsLibrary>
	getDynamicEffectsLibraryDetail(@RequestParam("resourceId") @NotBlank String resourceId) {

		DynamicEffectsLibrary dynamicEffectsLibrary= dynamicEffectsLibraryMapper.selectById(resourceId);
		if(null == dynamicEffectsLibrary){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(dynamicEffectsLibrary);
	}

	@ApiOperation("删除动效库")
	@DeleteMapping("")
	public BaseRsp deleteDynamicEffectsLibrary(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		List<String> resourceIds = batchDeleteReq.getIds();

		List<DynamicEffectsLibrary> dynamicEffectsLibraries = dynamicEffectsLibraryMapper.selectBatchIds(resourceIds);
		if (dynamicEffectsLibraries.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		dynamicEffectsLibraryMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	@ApiOperation("修改动效库")
	@PatchMapping("")
	public BaseRsp patchDynamicEffectsLibrary(@Validated(Update.class) DynamicEffectLibraryUpdateReq dynamicEffectLibraryUpdateReq) {
		DynamicEffectsLibrary patchObj = BeanUtils.copy(dynamicEffectLibraryUpdateReq, DynamicEffectsLibrary.class);
		DynamicEffectsLibrary dynamicEffectsLibrary = dynamicEffectsLibraryMapper.selectById(dynamicEffectLibraryUpdateReq.getResourceId());
		if(null == dynamicEffectsLibrary){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		DynamicEffectsLibraryCategory category = dynamicEffectsLibraryCategoryMapper.selectById(dynamicEffectLibraryUpdateReq.getCategory());
		if(null == category){
			throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
		}

		LambdaQueryWrapper<DynamicEffectsLibrary> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DynamicEffectsLibrary::getName, dynamicEffectLibraryUpdateReq.getName());
		DynamicEffectsLibrary one = dynamicEffectsLibraryService.getOne(queryWrapper);
		if (null != one && !Objects.equals(one.getResourceId(), dynamicEffectLibraryUpdateReq.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		dynamicEffectsLibraryMapper.updateById(patchObj);
		return new BaseRsp<>();
	}


	@ApiOperation("新增动效库类型")
	@PostMapping("/category")
	public BaseRsp<DynamicEffectsLibraryCategory>
	setDynamicEffectsLibraryCategory(@RequestBody @Validated(Create.class) AddCategoryReq addCategoryReq) {
		String name = addCategoryReq.getName();
		DynamicEffectsLibraryCategory dynamicEffectsLibraryCategory = new DynamicEffectsLibraryCategory();
		dynamicEffectsLibraryCategory.setName(name);
		dynamicEffectsLibraryCategory.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		try{
			dynamicEffectsLibraryCategoryMapper.insert(dynamicEffectsLibraryCategory);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(dynamicEffectsLibraryCategory);
	}

	@ApiOperation("查询动效库类型列表")
	@GetMapping("/category/list")
	public BaseRsp<List<DynamicEffectsLibraryCategory>>
	getDynamicEffectsLibraryCategory(String name) {

		LambdaQueryWrapper<DynamicEffectsLibraryCategory> queryWrapper = new LambdaQueryWrapper<>();
		if (null != name){
			queryWrapper.like(DynamicEffectsLibraryCategory::getName, name);

		}
		queryWrapper.orderByDesc(DynamicEffectsLibraryCategory::getCreateTime);
		List<DynamicEffectsLibraryCategory> res = dynamicEffectsLibraryCategoryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}


	@ApiOperation("查询动效库类型详情")
	@GetMapping("/category")
	public BaseRsp<DynamicEffectsLibraryCategory>
	getDynamicEffectsLibraryCategoryDetail(@RequestParam("resourceId") @NotBlank String resourceId) {

		DynamicEffectsLibraryCategory dynamicEffectsLibraryCategory = dynamicEffectsLibraryCategoryMapper.selectById(resourceId);
		if(null == dynamicEffectsLibraryCategory){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(dynamicEffectsLibraryCategory);
	}


	@ApiOperation("删除动效库类型")
	@DeleteMapping("/category")
	public BaseRsp deleteDynamicEffectsLibraryCategory(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();

		List<DynamicEffectsLibraryCategory> dynamicEffectsLibraryCategory = dynamicEffectsLibraryCategoryMapper.selectBatchIds(resourceIds);
		if (dynamicEffectsLibraryCategory.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		dynamicEffectsLibraryCategoryMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	@ApiOperation("修改动效库类型")
	@PatchMapping("/category")
	public BaseRsp patchDynamicEffectsLibraryCategory(@RequestBody @Validated(Update.class) AddCategoryReq addCategoryReq) {

		String name = addCategoryReq.getName();

		String resourceId = addCategoryReq.getResourceId();

		DynamicEffectsLibraryCategory patchObj = new DynamicEffectsLibraryCategory();
		patchObj.setName(name);
		patchObj.setResourceId(resourceId);

		DynamicEffectsLibraryCategory dynamicEffectsLibraryCategory = dynamicEffectsLibraryCategoryMapper.selectById(resourceId);
		if(null == dynamicEffectsLibraryCategory){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}


		LambdaQueryWrapper<DynamicEffectsLibraryCategory> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DynamicEffectsLibraryCategory::getName, name);
		DynamicEffectsLibraryCategory one = dynamicEffectsLibraryCategoryService.getOne(queryWrapper);
		if (null != one && !Objects.equals(one.getResourceId(), resourceId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		dynamicEffectsLibraryCategoryMapper.updateById(patchObj);
		return new BaseRsp<>();
	}

	@ApiOperation("返回所有选择项")
	@GetMapping("/option")
	public BaseRsp<OptionsRes> getAllOption() {
		OptionsRes optionsRes = new OptionsRes();
		optionsRes.setLineTypeList(LineType.getMsgList());
		optionsRes.setColorList(Color.getMsgList());
		return new BaseRsp<>(optionsRes);
	}


}