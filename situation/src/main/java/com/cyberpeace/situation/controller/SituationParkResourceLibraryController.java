package com.cyberpeace.situation.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.dao.entity.ParkResourceLibrary;
import com.cyberpeace.situation.dao.entity.ParkResourceLibraryCategory;
import com.cyberpeace.situation.dao.mapper.ParkResourceLibraryCategoryMapper;
import com.cyberpeace.situation.dao.mapper.ParkResourceLibraryMapper;
import com.cyberpeace.situation.service.ParkRecourseLibraryCategoryService;
import com.cyberpeace.situation.service.ParkRecourseLibraryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "【废弃】组件库-园区资源库")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/park_resource_library")
public class SituationParkResourceLibraryController {

	@Autowired
	private ParkResourceLibraryMapper parkResourceLibraryMapper;

	@Autowired
	private ParkResourceLibraryCategoryMapper parkResourceLibraryCategoryMapper;

	@Autowired
	private ParkRecourseLibraryService parkRecourseLibraryService;

	@Autowired
	private ParkRecourseLibraryCategoryService parkRecourseLibraryCategoryService;

	@ApiOperation("新增园区资源库")
	@PostMapping("")
	public BaseRsp<ParkResourceLibrary> setParkResourceLibrary(
			@RequestBody @Validated(Create.class) ParkResourceLibraryAddReq parkResourceLibraryAddReq) {

		ParkResourceLibrary parkResourceLibrary = BeanUtils.copy(parkResourceLibraryAddReq,
				ParkResourceLibrary.class);
		String userId = UserInfoContext.getUserContext().getUserId();
		parkResourceLibrary.setCreateUserId(userId);

		ParkResourceLibraryCategory category = parkResourceLibraryCategoryMapper
				.selectById(parkResourceLibraryAddReq.getParkResourceLibraryCategory());
		if (null == category) {
			throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
		}

		try {
			parkResourceLibraryMapper.insert(parkResourceLibrary);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(parkResourceLibrary);
	}

	@ApiOperation("查询园区资源库")
	@GetMapping("list")
	public BaseRsp<List<ParkResourceLibrary>>
			getParkResourceLibrary(ParkResourceLibraryFilterReq parkResourceLibraryFilterReq) {
		LambdaQueryWrapper<ParkResourceLibrary> queryWrapper = new LambdaQueryWrapper<>();
		String name = parkResourceLibraryFilterReq.getName();
		if (null != name) {
			queryWrapper.like(ParkResourceLibrary::getName, name);

		}
		String type = parkResourceLibraryFilterReq.getParkResourceLibraryCategory();
		if (null != type) {
			ParkResourceLibraryCategory category = parkResourceLibraryCategoryMapper
					.selectById(type);
			if (null == category) {
				throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
			}
		}

		if (null != type) {
			queryWrapper.eq(ParkResourceLibrary::getParkResourceLibraryCategory, type);
		}
		queryWrapper.orderByDesc(ParkResourceLibrary::getCreateTime);
		List<ParkResourceLibrary> res = parkResourceLibraryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	@ApiOperation("删除园区资源库")
	@DeleteMapping("")
	public BaseRsp
			deleteParkResourceLibrary(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();
		List<ParkResourceLibrary> parkResourceLibraries = parkResourceLibraryMapper
				.selectBatchIds(resourceIds);
		if (parkResourceLibraries.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		parkResourceLibraryMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	@ApiOperation("修改园区资源库")
	@PatchMapping("")
	public BaseRsp patchParkResourceLibrary(
			@Validated(Update.class) ParkResourceLibraryUpdateReq parkResourceLibraryUpdateReq) {
		ParkResourceLibrary patchObj = BeanUtils.copy(parkResourceLibraryUpdateReq,
				ParkResourceLibrary.class);
		ParkResourceLibrary parkResourceLibrary = parkResourceLibraryMapper
				.selectById(parkResourceLibraryUpdateReq.getResourceId());
		if (null == parkResourceLibrary) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		ParkResourceLibraryCategory category = parkResourceLibraryCategoryMapper
				.selectById(parkResourceLibraryUpdateReq.getParkResourceLibraryCategory());
		if (null == category) {
			throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
		}

		LambdaQueryWrapper<ParkResourceLibrary> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ParkResourceLibrary::getName, parkResourceLibraryUpdateReq.getName());
		ParkResourceLibrary one = parkRecourseLibraryService.getOne(queryWrapper);
		if (null != one && !Objects.equals(one.getResourceId(),
				parkResourceLibraryUpdateReq.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		parkResourceLibraryMapper.updateById(patchObj);
		return new BaseRsp<>();
	}

	@ApiOperation("查询园区资源详情")
	@GetMapping("/")
	public BaseRsp<ParkResourceLibrary>
			getParkResourceLibraryDetail(@RequestParam("resourceId") @NotBlank String resourceId) {

		ParkResourceLibrary threeDimensionalComponents = parkResourceLibraryMapper
				.selectById(resourceId);
		if (null == threeDimensionalComponents) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(threeDimensionalComponents);
	}

	@ApiOperation("新增园区资源库类型")
	@PostMapping("/category")
	public BaseRsp<ParkResourceLibraryCategory> setParkResourceLibraryCategory(
			@RequestBody @Validated(Create.class) AddCategoryReq addCategoryReq) {

		String name = addCategoryReq.getName();
		ParkResourceLibraryCategory parkResourceLibraryCategory = new ParkResourceLibraryCategory();
		parkResourceLibraryCategory.setName(name);
		parkResourceLibraryCategory.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		try {
			parkResourceLibraryCategoryMapper.insert(parkResourceLibraryCategory);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(parkResourceLibraryCategory);
	}

	@ApiOperation("查询园区资源库类型")
	@GetMapping("/category/list")
	public BaseRsp<List<ParkResourceLibraryCategory>> getParkResourceLibraryCategory(String name) {

		LambdaQueryWrapper<ParkResourceLibraryCategory> queryWrapper = new LambdaQueryWrapper<>();
		if (null != name) {
			queryWrapper.like(ParkResourceLibraryCategory::getName, name);

		}
		queryWrapper.orderByDesc(ParkResourceLibraryCategory::getCreateTime);
		List<ParkResourceLibraryCategory> res = parkResourceLibraryCategoryMapper
				.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	@ApiOperation("删除园区资源库类型")
	@DeleteMapping("/category")
	public BaseRsp deleteParkResourceLibraryCategory(
			@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();

		LambdaQueryWrapper<ParkResourceLibrary> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper.in(ParkResourceLibrary::getParkResourceLibraryCategory, resourceIds);
		List<ParkResourceLibraryCategory> parkResourceLibraryCategories = parkResourceLibraryCategoryMapper
				.selectBatchIds(resourceIds);
		if (parkResourceLibraryCategories.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		List<ParkResourceLibrary> parkResourceLibraries = parkResourceLibraryMapper
				.selectList(queryWrapper);
		if (!parkResourceLibraries.isEmpty()) {
			throw new BusinessException(SituationErrorCode.TYPE_WITH_COMPONENTS_ERROR);
		}

		parkResourceLibraryCategoryMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	@ApiOperation("查询园区资源库详情")
	@GetMapping("/category")
	public BaseRsp<ParkResourceLibraryCategory>
			getParkResourceLibraryCategoryDetail(@RequestParam @NotBlank String resourceId) {

		ParkResourceLibraryCategory parkResourceLibraryCategory = parkResourceLibraryCategoryMapper
				.selectById(resourceId);
		if (null == parkResourceLibraryCategory) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(parkResourceLibraryCategory);
	}

	@ApiOperation("修改园区资源库类型")
	@PatchMapping("/category")
	public BaseRsp patchParkResourceLibraryCategory(
			@RequestBody @Validated(Update.class) AddCategoryReq addCategoryReq) {

		String name = addCategoryReq.getName();
		String resourceId = addCategoryReq.getResourceId();

		ParkResourceLibraryCategory patchObj = new ParkResourceLibraryCategory();
		patchObj.setName(name);
		patchObj.setResourceId(resourceId);

		ParkResourceLibraryCategory parkResourceLibraryCategory = parkResourceLibraryCategoryMapper
				.selectById(resourceId);
		if (null == parkResourceLibraryCategory) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		LambdaQueryWrapper<ParkResourceLibraryCategory> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ParkResourceLibraryCategory::getName, name);
		ParkResourceLibraryCategory one = parkRecourseLibraryCategoryService.getOne(queryWrapper);
		if (null != one && !Objects.equals(one.getResourceId(), resourceId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		parkResourceLibraryCategoryMapper.updateById(patchObj);
		return new BaseRsp<>();
	}

}