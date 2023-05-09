package com.cyberpeace.situation.controller;

import java.io.File;
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
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.ZipUtil;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.topo.Color;
import com.cyberpeace.situation.constant.topo.LineType;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.dao.entity.DynamicEffectsLibrary;
import com.cyberpeace.situation.dao.entity.DynamicEffectsLibraryCategory;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;
import com.cyberpeace.situation.dao.mapper.DynamicEffectsLibraryCategoryMapper;
import com.cyberpeace.situation.dao.mapper.DynamicEffectsLibraryMapper;
import com.cyberpeace.situation.service.DynamicEffectsLibraryCategoryService;
import com.cyberpeace.situation.service.DynamicEffectsLibraryService;
import com.cyberpeace.situation.service.SituationDynamicEffectMappingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 动效管理
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Api(tags = "动效管理")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation")
public class DynamicEffectController {

	@Autowired
	private DynamicEffectsLibraryMapper dynamicEffectsLibraryMapper;

	@Autowired
	private DynamicEffectsLibraryCategoryMapper dynamicEffectsLibraryCategoryMapper;

	@Autowired
	private DynamicEffectsLibraryService dynamicEffectsLibraryService;

	@Autowired
	private DynamicEffectsLibraryCategoryService dynamicEffectsLibraryCategoryService;

	@Autowired
	private SituationDynamicEffectMappingService situationDynamicEffectMappingService;

	/**
	 * 新增动效
	 *
	 * @param dynamicEffectLibraryAddReq 动效新增请求
	 * @return {@code BaseRsp<DynamicEffectsLibrary>}
	 */
	@ApiOperation("动效-新增")
	@PostMapping("/dynamic_effect_library")
	public BaseRsp<DynamicEffectsLibrary> setDynamicEffectsLibrary(
			@RequestBody @Validated(Create.class) DynamicEffectLibraryAddReq dynamicEffectLibraryAddReq) {

		// 提取动效信息
		DynamicEffectsLibrary dynamicEffectsLibrary = BeanUtils.copy(dynamicEffectLibraryAddReq,
				DynamicEffectsLibrary.class);
		String userId = UserInfoContext.getUserContext().getUserId();
		dynamicEffectsLibrary.setCreateUserId(userId);

		// 判断动效类型是否存在
		DynamicEffectsLibraryCategory category = dynamicEffectsLibraryCategoryMapper
				.selectById(dynamicEffectLibraryAddReq.getCategory());
		if (null == category) {
			throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
		}

		try {
			// 设置动效zip解压文件路径
			String sourceNodeDynamicFile = dynamicEffectLibraryAddReq.getSourceNodeDynamicFile();
			String sourceNodeDynamicDir = unZipFile(sourceNodeDynamicFile);
			dynamicEffectsLibrary.setSourceNodeDynamicDir(sourceNodeDynamicDir);
			dynamicEffectsLibraryMapper.insert(dynamicEffectsLibrary);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(dynamicEffectsLibrary);
	}

	/**
	 * 动效-列表查询
	 *
	 * @param dynamicEffectLibraryFilterReq 动效列表查询请求
	 * @return {@code BaseRsp<List<DynamicEffectsLibrary>>}
	 */
	@ApiOperation("动效-列表查询")
	@GetMapping("/dynamic_effect_library/list")
	public BaseRsp<List<DynamicEffectsLibrary>>
			getDynamicEffectsLibrary(DynamicEffectLibraryFilterReq dynamicEffectLibraryFilterReq) {

		// 设置查询条件
		LambdaQueryWrapper<DynamicEffectsLibrary> queryWrapper = new LambdaQueryWrapper<>();
		String name = dynamicEffectLibraryFilterReq.getName();
		if (null != name) {
			queryWrapper.like(DynamicEffectsLibrary::getName, name);

		}

		// 判断动效类型是否存在
		String type = dynamicEffectLibraryFilterReq.getCategory();
		if (null != type && !type.equals("")) {
			DynamicEffectsLibraryCategory category = dynamicEffectsLibraryCategoryMapper
					.selectById(type);
			if (null == category) {
				throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
			}

			queryWrapper.eq(DynamicEffectsLibrary::getCategory, type);
		}

		queryWrapper.orderByDesc(DynamicEffectsLibrary::getCreateTime);
		List<DynamicEffectsLibrary> res = dynamicEffectsLibraryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	/**
	 * 动效-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<DynamicEffectsLibrary>}
	 */
	@ApiOperation("动效-单个查询")
	@GetMapping("/dynamic_effect_library")
	public BaseRsp<DynamicEffectsLibrary> getDynamicEffectsLibraryDetail(
			@RequestParam("resourceId") @NotBlank String resourceId) {

		DynamicEffectsLibrary dynamicEffectsLibrary = dynamicEffectsLibraryMapper
				.selectById(resourceId);
		if (null == dynamicEffectsLibrary) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(dynamicEffectsLibrary);
	}

	/**
	 * 删除动效
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("动效-删除")
	@DeleteMapping("/dynamic_effect_library")
	public BaseRsp
			deleteDynamicEffectsLibrary(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		List<String> resourceIds = batchDeleteReq.getIds();

		List<DynamicEffectsLibrary> dynamicEffectsLibraries = dynamicEffectsLibraryMapper
				.selectBatchIds(resourceIds);
		if (dynamicEffectsLibraries.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		dynamicEffectsLibraryMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	/**
	 * 动效-修改
	 *
	 * @param dynamicEffectLibraryUpdateReq 动效修改请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("动效-修改")
	@PatchMapping("/dynamic_effect_library")
	public BaseRsp patchDynamicEffectsLibrary(
			@Validated(Update.class) DynamicEffectLibraryUpdateReq dynamicEffectLibraryUpdateReq) {

		DynamicEffectsLibrary patchObj = BeanUtils.copy(dynamicEffectLibraryUpdateReq,
				DynamicEffectsLibrary.class);
		DynamicEffectsLibrary dynamicEffectsLibrary = dynamicEffectsLibraryMapper
				.selectById(dynamicEffectLibraryUpdateReq.getResourceId());
		if (null == dynamicEffectsLibrary) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// 设置态势zip解压文件目录
		patchObj.setSourceNodeDynamicDir(
				unZipFile(dynamicEffectLibraryUpdateReq.getSourceNodeDynamicFile()));
		DynamicEffectsLibraryCategory category = dynamicEffectsLibraryCategoryMapper
				.selectById(dynamicEffectLibraryUpdateReq.getCategory());
		if (null == category) {
			throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
		}

		LambdaQueryWrapper<DynamicEffectsLibrary> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DynamicEffectsLibrary::getName, dynamicEffectLibraryUpdateReq.getName());
		DynamicEffectsLibrary one = dynamicEffectsLibraryService.getOne(queryWrapper);
		if (null != one && !Objects.equals(one.getResourceId(),
				dynamicEffectLibraryUpdateReq.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		dynamicEffectsLibraryMapper.updateById(patchObj);
		return new BaseRsp<>();
	}

	/**
	 * 动效类型-新增
	 *
	 * @param addCategoryReq 动效类型新增请求
	 * @return {@code BaseRsp<DynamicEffectsLibraryCategory>}
	 */
	@ApiOperation("动效类型-新增")
	@PostMapping("/dynamic_effect_library/category")
	public BaseRsp<DynamicEffectsLibraryCategory> setDynamicEffectsLibraryCategory(
			@RequestBody @Validated(Create.class) AddCategoryReq addCategoryReq) {
		String name = addCategoryReq.getName();
		DynamicEffectsLibraryCategory dynamicEffectsLibraryCategory = new DynamicEffectsLibraryCategory();
		dynamicEffectsLibraryCategory.setName(name);
		dynamicEffectsLibraryCategory.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		try {
			dynamicEffectsLibraryCategoryMapper.insert(dynamicEffectsLibraryCategory);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(dynamicEffectsLibraryCategory);
	}

	/**
	 * 动效类型-列表查询
	 *
	 * @param name 名称
	 * @return {@code BaseRsp<List<DynamicEffectsLibraryCategory>>}
	 */
	@ApiOperation("动效类型-列表查询")
	@GetMapping("/dynamic_effect_library/category/list")
	public BaseRsp<List<DynamicEffectsLibraryCategory>>
			getDynamicEffectsLibraryCategory(String name) {

		LambdaQueryWrapper<DynamicEffectsLibraryCategory> queryWrapper = new LambdaQueryWrapper<>();
		if (null != name) {
			queryWrapper.like(DynamicEffectsLibraryCategory::getName, name);

		}
		queryWrapper.orderByDesc(DynamicEffectsLibraryCategory::getCreateTime);
		List<DynamicEffectsLibraryCategory> res = dynamicEffectsLibraryCategoryMapper
				.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	/**
	 * 动效类型-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<DynamicEffectsLibraryCategory>}
	 */
	@ApiOperation("动效类型-单个查询")
	@GetMapping("/dynamic_effect_library/category")
	public BaseRsp<DynamicEffectsLibraryCategory> getDynamicEffectsLibraryCategoryDetail(
			@RequestParam("resourceId") @NotBlank String resourceId) {

		DynamicEffectsLibraryCategory dynamicEffectsLibraryCategory = dynamicEffectsLibraryCategoryMapper
				.selectById(resourceId);
		if (null == dynamicEffectsLibraryCategory) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(dynamicEffectsLibraryCategory);
	}

	/**
	 * 动效类型-删除
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("动效类型-删除")
	@DeleteMapping("/dynamic_effect_library/category")
	public BaseRsp deleteDynamicEffectsLibraryCategory(
			@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();

		List<DynamicEffectsLibraryCategory> dynamicEffectsLibraryCategory = dynamicEffectsLibraryCategoryMapper
				.selectBatchIds(resourceIds);
		if (dynamicEffectsLibraryCategory.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		dynamicEffectsLibraryCategoryMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	/**
	 * 动效类型-修改
	 *
	 * @param addCategoryReq 动效类型修改请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("动效类型-修改")
	@PatchMapping("/dynamic_effect_library/category")
	public BaseRsp patchDynamicEffectsLibraryCategory(
			@RequestBody @Validated(Update.class) AddCategoryReq addCategoryReq) {

		String name = addCategoryReq.getName();

		String resourceId = addCategoryReq.getResourceId();

		DynamicEffectsLibraryCategory patchObj = new DynamicEffectsLibraryCategory();
		patchObj.setName(name);
		patchObj.setResourceId(resourceId);

		DynamicEffectsLibraryCategory dynamicEffectsLibraryCategory = dynamicEffectsLibraryCategoryMapper
				.selectById(resourceId);
		if (null == dynamicEffectsLibraryCategory) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		LambdaQueryWrapper<DynamicEffectsLibraryCategory> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DynamicEffectsLibraryCategory::getName, name);
		DynamicEffectsLibraryCategory one = dynamicEffectsLibraryCategoryService
				.getOne(queryWrapper);
		if (null != one && !Objects.equals(one.getResourceId(), resourceId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		dynamicEffectsLibraryCategoryMapper.updateById(patchObj);
		return new BaseRsp<>();
	}

	/**
	 * 获取动效属性选项
	 *
	 * @return {@code BaseRsp<OptionsRes>}
	 */
	@ApiOperation("动效-获取动效属性选项")
	@GetMapping("/dynamic_effect_library/option")
	public BaseRsp<OptionsRes> getAllOption() {
		OptionsRes optionsRes = new OptionsRes();
		optionsRes.setLineTypeList(LineType.getMsgList());
		optionsRes.setColorList(Color.getMsgList());
		return new BaseRsp<>(optionsRes);
	}

	/**
	 * 解压文件
	 *
	 * @param zipPath zip路径
	 * @return {@code String}
	 */
	private String unZipFile(String zipPath) {
		String destPath = "/cpms/dynamic_effects/";
		File zipFile = new File(zipPath);
		log.info(zipFile.getName());
		destPath = destPath + zipFile.getName().substring(0, zipFile.getName().indexOf("."));
		log.info(destPath);
		ZipUtil.unzipToDest(zipPath, destPath);
		return destPath;
	}

	/**
	 * 动效映射-新增
	 *
	 * @param dynamicEffectMappingReq 动态映射新增请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("动效映射-新增")
	@PostMapping("/dynamic/effect/mapping")
	public BaseRsp<String> setDynamicEffectsMapping(
			@RequestBody @Validated(Create.class) DynamicEffectMappingReq dynamicEffectMappingReq) {

		return new BaseRsp<>(situationDynamicEffectMappingService
				.setDynamicEffectsMapping(dynamicEffectMappingReq));
	}

	/**
	 * 动效映射-列表查询
	 *
	 * @param dynamicEffectMappingFilterReq 动态映射列表查询请求
	 * @return {@code BaseRsp<ListRsp<DynamicEffectsMapping>>}
	 */
	@ApiOperation("动效映射-列表查询")
	@GetMapping("/dynamic/effect/mapping/list")
	public BaseRsp<ListRsp<DynamicEffectsMapping>>
			getDynamicEffectsMapping(DynamicEffectMappingPageReq dynamicEffectMappingFilterReq) {

		return new BaseRsp<>(situationDynamicEffectMappingService
				.getDynamicEffectsMapping(dynamicEffectMappingFilterReq));
	}

	/**
	 * 动效映射-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<DynamicEffectsMapping>}
	 */
	@ApiOperation("动效映射-单个查询")
	@GetMapping("/dynamic/effect/mapping")
	public BaseRsp<DynamicEffectsMapping> getDynamicEffectsMappingDetail(
			@RequestParam("resourceId") @NotBlank String resourceId) {

		return new BaseRsp<>(
				situationDynamicEffectMappingService.getDynamicEffectsMappingDetail(resourceId));
	}

	/**
	 * 动效映射-删除
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@ApiOperation("动效映射-删除")
	@DeleteMapping("/dynamic/effect/mapping")
	public BaseRsp<Integer>
			deleteDynamicEffectsMapping(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		return new BaseRsp<>(
				situationDynamicEffectMappingService.deleteDynamicEffectsMapping(batchDeleteReq));
	}

	/**
	 * 动效映射-修改
	 *
	 * @param dynamicEffectMappingUpdateReq 动态映射修改请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("动效映射-修改")
	@PatchMapping("/dynamic/effect/mapping")
	public BaseRsp<String> patchDynamicEffectsMapping(
			@RequestBody @Validated(Update.class) DynamicEffectMappingUpdateReq dynamicEffectMappingUpdateReq) {

		return new BaseRsp<>(situationDynamicEffectMappingService
				.patchDynamicEffectsMapping(dynamicEffectMappingUpdateReq));
	}
}