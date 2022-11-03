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
import com.cyberpeace.situation.controller.req.ParkResourceLibraryAddReq;
import com.cyberpeace.situation.controller.req.ParkResourceLibraryFilterReq;
import com.cyberpeace.situation.dao.entity.ParkResourceLibrary;
import com.cyberpeace.situation.dao.entity.ParkResourceLibraryCategory;
import com.cyberpeace.situation.dao.mapper.ParkResourceLibraryCategoryMapper;
import com.cyberpeace.situation.dao.mapper.ParkResourceLibraryMapper;
import com.cyberpeace.situation.service.ParkRecourseLibraryService;
import com.cyberpeace.situation.service.ParkRecourseLibraryCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Api(tags = "组件库-园区资源库")
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
	public BaseRsp<ParkResourceLibrary>
	setParkResourceLibrary(@RequestBody @Validated(Create.class) ParkResourceLibraryAddReq parkResourceLibraryAddReq) {

		ParkResourceLibrary parkResourceLibrary = BeanUtils.copy(parkResourceLibraryAddReq,
				ParkResourceLibrary.class);
		String userId = UserInfoContext.getUserContext().getUserId();
		parkResourceLibrary.setCreateUserId(userId);

		ParkResourceLibraryCategory category = parkResourceLibraryCategoryMapper.selectById(parkResourceLibraryAddReq.getParkResourceLibraryCategory());
		if(null == category){
			throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
		}

		try{
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
		if (null != name){
			queryWrapper.like(ParkResourceLibrary::getName, name);

		}
		String type = parkResourceLibraryFilterReq.getParkResourceLibraryCategory();
		if (null != type) {
			ParkResourceLibraryCategory category = parkResourceLibraryCategoryMapper.selectById(type);
			if (null == category) {
				throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
			}
		}

		if (null != type){
			queryWrapper.eq(ParkResourceLibrary::getParkResourceLibraryCategory, type);
		}
		List<ParkResourceLibrary> res = parkResourceLibraryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	@ApiOperation("删除园区资源库")
	@DeleteMapping("")
	public BaseRsp deleteParkResourceLibrary(String resourceId) {
		ParkResourceLibrary parkResourceLibrary = parkResourceLibraryMapper.selectById(resourceId);
		if (null == parkResourceLibrary) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		parkResourceLibraryMapper.deleteById(resourceId);
		return new BaseRsp<>();
	}

	@ApiOperation("修改园区资源库")
	@PatchMapping("")
	public BaseRsp patchParkResourceLibrary(@Validated(Update.class) ParkResourceLibraryAddReq parkResourceLibraryAddReq) {
		ParkResourceLibrary patchObj = BeanUtils.copy(parkResourceLibraryAddReq, ParkResourceLibrary.class);
		ParkResourceLibrary parkResourceLibrary = parkResourceLibraryMapper.selectById(parkResourceLibraryAddReq.getResourceId());
		if(null == parkResourceLibrary){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		ParkResourceLibraryCategory category = parkResourceLibraryCategoryMapper.selectById(parkResourceLibraryAddReq.getParkResourceLibraryCategory());
		if(null == category){
			throw new BusinessException(SituationErrorCode.TYPE_INVALID_ERROR);
		}

		LambdaQueryWrapper<ParkResourceLibrary> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ParkResourceLibrary::getName, parkResourceLibraryAddReq.getName());
		ParkResourceLibrary one = parkRecourseLibraryService.getOne(queryWrapper);
		if (!Objects.equals(one.getResourceId(), parkResourceLibraryAddReq.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		parkResourceLibraryMapper.updateById(patchObj);
		return new BaseRsp<>();
	}

	@ApiOperation("查询园区资源详情")
	@GetMapping("/")
	public BaseRsp<ParkResourceLibrary>
	getParkResourceLibraryDetail(@RequestParam("resourceId") @NotBlank String resourceId) {

		ParkResourceLibrary threeDimensionalComponents = parkResourceLibraryMapper.selectById(resourceId);
		if(null == threeDimensionalComponents){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(threeDimensionalComponents);
	}


	@ApiOperation("新增园区资源库类型")
	@PostMapping("/category")
	public BaseRsp<ParkResourceLibraryCategory>
	setParkResourceLibraryCategory(String name) {

		if(StringUtils.isBlank(name)){
			throw new BusinessException(SituationErrorCode.NAME_EMPTY_ERROR);
		}
		ParkResourceLibraryCategory parkResourceLibraryCategory = new ParkResourceLibraryCategory();
		parkResourceLibraryCategory.setName(name);
		parkResourceLibraryCategory.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		try{
			parkResourceLibraryCategoryMapper.insert(parkResourceLibraryCategory);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return new BaseRsp<>(parkResourceLibraryCategory);
	}

	@ApiOperation("查询园区资源库类型")
	@GetMapping("/category/list")
	public BaseRsp<List<ParkResourceLibraryCategory>>
	getParkResourceLibraryCategory(String name) {

		LambdaQueryWrapper<ParkResourceLibraryCategory> queryWrapper = new LambdaQueryWrapper<>();
		if (null != name){
			queryWrapper.like(ParkResourceLibraryCategory::getName, name);

		}
		List<ParkResourceLibraryCategory> res = parkResourceLibraryCategoryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	@ApiOperation("删除园区资源库类型")
	@DeleteMapping("/category")
	public BaseRsp deleteParkResourceLibraryCategory(String resourceId) {
		ParkResourceLibraryCategory parkResourceLibraryCategory = parkResourceLibraryCategoryMapper.selectById(resourceId);
		if (null == parkResourceLibraryCategory) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		parkResourceLibraryCategoryMapper.deleteById(resourceId);
		return new BaseRsp<>();
	}

	@ApiOperation("查询三维组件类型详情")
	@GetMapping("/category")
	public BaseRsp<ParkResourceLibraryCategory>
	getParkResourceLibraryCategoryDetail(String resourceId) {

		ParkResourceLibraryCategory parkResourceLibraryCategory = parkResourceLibraryCategoryMapper.selectById(resourceId);
		if(null == parkResourceLibraryCategory){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(parkResourceLibraryCategory);
	}

	@ApiOperation("修改园区资源库类型")
	@PatchMapping("/category")
	public BaseRsp patchParkResourceLibraryCategory(String resourceId, String name) {

		if(StringUtils.isBlank(name)){
			throw new BusinessException(SituationErrorCode.NAME_EMPTY_ERROR);
		}
		if(StringUtils.isBlank(resourceId)){
			throw new BusinessException(SituationErrorCode.RESOURCE_ID_EMPTY_ERROR);
		}

		ParkResourceLibraryCategory patchObj = new ParkResourceLibraryCategory();
		patchObj.setName(name);
		patchObj.setResourceId(resourceId);

		ParkResourceLibraryCategory parkResourceLibraryCategory = parkResourceLibraryCategoryMapper.selectById(resourceId);
		if(null == parkResourceLibraryCategory){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}


		LambdaQueryWrapper<ParkResourceLibraryCategory> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ParkResourceLibraryCategory::getName, name);
		ParkResourceLibraryCategory one = parkRecourseLibraryCategoryService.getOne(queryWrapper);
		if (!Objects.equals(one.getResourceId(), resourceId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		parkResourceLibraryCategoryMapper.updateById(patchObj);
		return new BaseRsp<>();
	}

}