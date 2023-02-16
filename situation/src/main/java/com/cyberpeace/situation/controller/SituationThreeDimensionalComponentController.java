package com.cyberpeace.situation.controller;

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
import com.cyberpeace.situation.dao.entity.ThreeDimensionalComponents;
import com.cyberpeace.situation.dao.entity.ThreeDimensionalComponentsCategory;
import com.cyberpeace.situation.dao.entity.TwoDimensionalComponents;
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

@Api(tags = "组件库-三维组件")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/three_dimensional_component")
public class SituationThreeDimensionalComponentController {

	@Autowired
	private ThreeDimensionalComponentsCategoryMapper threeDimensionalComponentsCategoryMapper;

	@Autowired
	private ThreeDimensionalComponentsMapper threeDimensionalComponentsMapper;

	@Autowired
	private ThreeDimensionalComponentsService threeDimensionalComponentsService;

	@Autowired
	private ThreeDimensionalComponentsCategoryService threeDimensionalComponentsCategoryService;

	@ApiOperation("新增三维组件类型")
	@PostMapping("/category")
	public BaseRsp<ThreeDimensionalComponentsCategory>
	setThreeDimensionalComponentsCategory(@RequestBody @Validated(Create.class) AddCategoryReq addCategoryReq) {
		String name = addCategoryReq.getName();
		ThreeDimensionalComponentsCategory threeDimensionalComponentsCategory = new ThreeDimensionalComponentsCategory();
		threeDimensionalComponentsCategory.setName(name);
		threeDimensionalComponentsCategory.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		try{
			threeDimensionalComponentsCategoryMapper.insert(threeDimensionalComponentsCategory);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(threeDimensionalComponentsCategory);
	}

	@ApiOperation("查询三维组件类型")
	@GetMapping("/category/list")
	public BaseRsp<List<ThreeDimensionalComponentsCategory>>
	getThreeDimensionalComponentsCategory(String name) {

		LambdaQueryWrapper<ThreeDimensionalComponentsCategory> queryWrapper = new LambdaQueryWrapper<>();
		if (null != name){
			queryWrapper.like(ThreeDimensionalComponentsCategory::getName, name);

		}
		queryWrapper.orderByDesc(ThreeDimensionalComponentsCategory::getCreateTime);
		List<ThreeDimensionalComponentsCategory> res = threeDimensionalComponentsCategoryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	@ApiOperation("查询三维组件类型详情")
	@GetMapping("/category")
	public BaseRsp<ThreeDimensionalComponentsCategory>
	getThreeDimensionalComponentsCategoryDetail(@RequestParam("resourceId") @NotBlank String resourceId) {

		ThreeDimensionalComponentsCategory threeDimensionalComponentsCategory = threeDimensionalComponentsCategoryMapper.selectById(resourceId);
		if(null == threeDimensionalComponentsCategory){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(threeDimensionalComponentsCategory);
	}

	@ApiOperation("删除三维组件类型")
	@DeleteMapping("/category")
	public BaseRsp deleteThreeDimensionalComponentsCategory(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();
		List<ThreeDimensionalComponentsCategory> threeDimensionalComponentsCategories = threeDimensionalComponentsCategoryMapper.selectBatchIds(resourceIds);
		if (threeDimensionalComponentsCategories.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		LambdaQueryWrapper<ThreeDimensionalComponents> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(ThreeDimensionalComponents::getThreeDimensionalComponentsCategory, resourceIds);
		List<ThreeDimensionalComponents> threeDimensionalComponents = threeDimensionalComponentsMapper.selectList(queryWrapper);
		if(!threeDimensionalComponents.isEmpty()){
			throw new BusinessException(SituationErrorCode.TYPE_WITH_COMPONENTS_ERROR);
		}

		threeDimensionalComponentsCategoryMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	@ApiOperation("修改三维组件类型")
	@PatchMapping("/category")
	public BaseRsp patchThreeDimensionalComponentsCategory(@RequestBody @Validated(Update.class)AddCategoryReq addCategoryReq) {
		String name = addCategoryReq.getName();
		String resourceId = addCategoryReq.getResourceId();

		ThreeDimensionalComponentsCategory patchObj = new ThreeDimensionalComponentsCategory();
		patchObj.setName(name);
		patchObj.setResourceId(resourceId);

		ThreeDimensionalComponentsCategory twoDimensionalComponents = threeDimensionalComponentsCategoryMapper.selectById(resourceId);
		if(null == twoDimensionalComponents){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		LambdaQueryWrapper<ThreeDimensionalComponentsCategory> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ThreeDimensionalComponentsCategory::getName, name);
		ThreeDimensionalComponentsCategory one = threeDimensionalComponentsCategoryService.getOne(queryWrapper);
		if (null != one && !Objects.equals(one.getResourceId(), resourceId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		threeDimensionalComponentsCategoryMapper.updateById(patchObj);
		return new BaseRsp<>();
	}

	@ApiOperation("新增三维组件")
	@PostMapping("")
	public BaseRsp<ThreeDimensionalComponents>
	setThreeDimensionalComponents(@RequestBody @Validated(Create.class) ThreeDimensionalComponentsAddReq threeDimensionalComponentsAddReq) {

		ThreeDimensionalComponents threeDimensionalComponents = BeanUtils.copy(threeDimensionalComponentsAddReq,
				ThreeDimensionalComponents.class);
		String userId = UserInfoContext.getUserContext().getUserId();
		threeDimensionalComponents.setCreateUserId(userId);

		ThreeDimensionalComponentsCategory category = threeDimensionalComponentsCategoryMapper.selectById(threeDimensionalComponentsAddReq.getThreeDimensionalComponentsCategory());
		if(null == category){
			throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
		}

		try{
			threeDimensionalComponentsMapper.insert(threeDimensionalComponents);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(threeDimensionalComponents);
	}

	@ApiOperation("查询三维组件")
	@GetMapping("list")
	public BaseRsp<List<ThreeDimensionalComponents>>
	getThreeDimensionalComponents(ThreeDimensionalComponentsFilterReq threeDimensionalComponentsFilterReq) {
		LambdaQueryWrapper<ThreeDimensionalComponents> queryWrapper = new LambdaQueryWrapper<>();
		if (null != threeDimensionalComponentsFilterReq.getName()){
			queryWrapper.like(ThreeDimensionalComponents::getName, threeDimensionalComponentsFilterReq.getName());

		}
		String type = threeDimensionalComponentsFilterReq.getThreeDimensionalComponentsCategory();
		if (null != type) {
			ThreeDimensionalComponentsCategory category = threeDimensionalComponentsCategoryMapper.selectById(type);
			if (null == category) {
				throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
			}
		}

		if (null != type){
			queryWrapper.eq(ThreeDimensionalComponents::getThreeDimensionalComponentsCategory, type);
		}
		queryWrapper.orderByDesc(ThreeDimensionalComponents::getCreateTime);
		List<ThreeDimensionalComponents> res = threeDimensionalComponentsMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	@ApiOperation("查询三维组件详情")
	@GetMapping("")
	public BaseRsp<ThreeDimensionalComponents>
	getThreeDimensionalComponentsDetail(@RequestParam @NotBlank String resourceId) {

		ThreeDimensionalComponents threeDimensionalComponents = threeDimensionalComponentsMapper.selectById(resourceId);
		if(null == threeDimensionalComponents){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(threeDimensionalComponents);
	}


	@ApiOperation("删除三维组件")
	@DeleteMapping("")
	public BaseRsp deleteThreeDimensionalComponents(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();
		List<ThreeDimensionalComponents> threeDimensionalComponents = threeDimensionalComponentsMapper.selectBatchIds(resourceIds);
		if (threeDimensionalComponents.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		threeDimensionalComponentsMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	@ApiOperation("修改三维组件")
	@PatchMapping("")
	public BaseRsp patchThreeDimensionalComponents(@Validated(Update.class) ThreeDimensionalComponentsUpdateReq threeDimensionalComponentsUpdateReq) {
		ThreeDimensionalComponents patchObj = BeanUtils.copy(threeDimensionalComponentsUpdateReq, ThreeDimensionalComponents.class);
		ThreeDimensionalComponents threeDimensionalComponents = threeDimensionalComponentsMapper.selectById(threeDimensionalComponentsUpdateReq.getResourceId());
		if(null == threeDimensionalComponents){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		ThreeDimensionalComponentsCategory category = threeDimensionalComponentsCategoryMapper.selectById(threeDimensionalComponentsUpdateReq.getThreeDimensionalComponentsCategory());
		if(null == category){
			throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
		}

		LambdaQueryWrapper<ThreeDimensionalComponents> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ThreeDimensionalComponents::getName, threeDimensionalComponentsUpdateReq.getName());
		ThreeDimensionalComponents one = threeDimensionalComponentsService.getOne(queryWrapper);
		if (null != one && !Objects.equals(one.getResourceId(), threeDimensionalComponents.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		threeDimensionalComponentsMapper.updateById(patchObj);
		return new BaseRsp<>();
	}
}