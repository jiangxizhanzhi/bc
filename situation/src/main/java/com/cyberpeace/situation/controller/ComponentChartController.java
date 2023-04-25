package com.cyberpeace.situation.controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.model.manage.ServiceLogContext;
import com.cyberpeace.integratation.service.RemoteManageService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.BatchDeleteReq;
import com.cyberpeace.situation.controller.req.TwoDimensionalComponentsAddReq;
import com.cyberpeace.situation.controller.req.TwoDimensionalComponentsFilterReq;
import com.cyberpeace.situation.controller.req.TwoDimensionalComponentsUpdateReq;
import com.cyberpeace.situation.controller.res.SituationComponentsChartDetailsRes;
import com.cyberpeace.situation.dao.entity.SituationDbAccess;
import com.cyberpeace.situation.dao.entity.TwoDimensionalComponents;
import com.cyberpeace.situation.dao.entity.TwoDimensionalComponentsCategory;
import com.cyberpeace.situation.dao.mapper.SituationDbAccessMapper;
import com.cyberpeace.situation.dao.mapper.TwoDimensionalComponentsCategoryMapper;
import com.cyberpeace.situation.dao.mapper.TwoDimensionalComponentsMapper;
import com.cyberpeace.situation.service.TwoDimensionalComponentsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "组件管理-图表组件")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/two_dimensional_component")
public class ComponentChartController {

	@Autowired
	private TwoDimensionalComponentsMapper twoDimensionalComponentsMapper;

	@Autowired
	private TwoDimensionalComponentsService twoDimensionalComponentsService;

	@Autowired
	private TwoDimensionalComponentsCategoryMapper twoDimensionalComponentsCategoryMapper;

	@Autowired
	private RemoteManageService remoteManageService;

	@Autowired
	private SituationDbAccessMapper situationDbAccessMapper;

	@ApiOperation("图表组件类型-列表查询")
	@GetMapping("/category/list")
	public BaseRsp<List<TwoDimensionalComponentsCategory>> getTwoDimensionalComponents() {
		LambdaQueryWrapper<TwoDimensionalComponentsCategory> queryWrapper = new LambdaQueryWrapper<>();
		List<TwoDimensionalComponentsCategory> res = twoDimensionalComponentsCategoryMapper
				.selectList(queryWrapper);
		insertIntoDb();
		return new BaseRsp<>(res);
	}

	@ApiOperation("图表组件类型-单个查询")
	@GetMapping("")
	public BaseRsp<TwoDimensionalComponents> getThreeDimensionalComponentsCategoryDetail(
			@RequestParam("resourceId") @NotBlank String resourceId) {

		TwoDimensionalComponents twoDimensionalComponents = twoDimensionalComponentsMapper
				.selectById(resourceId);
		if (null == twoDimensionalComponents) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		insertIntoDb();
		return new BaseRsp<>(twoDimensionalComponents);
	}

	@ApiOperation("图表组件-新增")
	@PostMapping("")
	public BaseRsp<TwoDimensionalComponents> setTwoDimensionalComponents(
			@RequestBody @Validated(Create.class) TwoDimensionalComponentsAddReq twoDimensionalComponentsAddReq) {

		TwoDimensionalComponents twoDimensionalComponents = BeanUtils
				.copy(twoDimensionalComponentsAddReq, TwoDimensionalComponents.class);
		String type = twoDimensionalComponents.getType();
		TwoDimensionalComponentsCategory twoDimensionalComponentsCategory = twoDimensionalComponentsCategoryMapper
				.selectById(type);
		if (null == twoDimensionalComponentsCategory) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		String userId = UserInfoContext.getUserContext().getUserId();
		twoDimensionalComponents.setCreateUserId(userId);
		try {
			twoDimensionalComponentsMapper.insert(twoDimensionalComponents);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		insertIntoDb();
		return new BaseRsp<>(twoDimensionalComponents);
	}

	@ApiOperation("图表组件-单个查询")
	@GetMapping("list")
	public BaseRsp<List<SituationComponentsChartDetailsRes>> getTwoDimensionalComponents(
			TwoDimensionalComponentsFilterReq twoDimensionalComponentsFilterReq) {
		LambdaQueryWrapper<TwoDimensionalComponents> queryWrapper = new LambdaQueryWrapper<>();
		if (null != twoDimensionalComponentsFilterReq.getName()) {
			queryWrapper.like(TwoDimensionalComponents::getName,
					twoDimensionalComponentsFilterReq.getName());

		}
		if (null != twoDimensionalComponentsFilterReq.getType()
				&& !twoDimensionalComponentsFilterReq.getType().equals("")) {
			queryWrapper.eq(TwoDimensionalComponents::getType,
					twoDimensionalComponentsFilterReq.getType());
		}
		queryWrapper.orderByDesc(TwoDimensionalComponents::getCreateTime);
		List<TwoDimensionalComponents> res = twoDimensionalComponentsMapper
				.selectList(queryWrapper);
		List<SituationComponentsChartDetailsRes> result = new ArrayList<>();
		List<TwoDimensionalComponentsCategory> twoDimensionalComponentsCategories = twoDimensionalComponentsCategoryMapper
				.selectList(null);
		Map<String, String> categoryMap = StreamApiUtils.toMap(twoDimensionalComponentsCategories,
				TwoDimensionalComponentsCategory::getResourceId,
				twoDimensionalComponentsCategory -> twoDimensionalComponentsCategory.getName());
		res.forEach(twoDimensionalComponent -> {
			SituationComponentsChartDetailsRes situationComponentsChartDetailsRes = new SituationComponentsChartDetailsRes();
			BeanUtils.copyProperties(twoDimensionalComponent, situationComponentsChartDetailsRes);
			String chartCategory = categoryMap.get(twoDimensionalComponent.getType());
			chartCategory = StringUtils.isEmpty(chartCategory) ? "" : chartCategory;
			situationComponentsChartDetailsRes.setChartCategoryName(chartCategory);
			result.add(situationComponentsChartDetailsRes);
		});
		remoteManageService.writeLog(ServiceLogContext.SubModule.TJZJ, ServiceLogContext.Type.CX);
		insertIntoDb();
		return new BaseRsp<>(result);
	}

	@ApiOperation("图表组件-删除")
	@DeleteMapping("")
	public BaseRsp
			deleteTwoDimensionalComponents(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();
		List<TwoDimensionalComponents> twoDimensionalComponents = twoDimensionalComponentsMapper
				.selectBatchIds(resourceIds);
		if (twoDimensionalComponents.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		twoDimensionalComponentsMapper.deleteBatchIds(resourceIds);
		insertIntoDb();
		return new BaseRsp<>();
	}

	@ApiOperation("图表组件-修改")
	@PatchMapping("")
	public BaseRsp patchTwoDimensionalComponents(
			@RequestBody @Validated(Update.class) TwoDimensionalComponentsUpdateReq twoDimensionalComponentsUpdateReq) {
		TwoDimensionalComponents patchObj = BeanUtils.copy(twoDimensionalComponentsUpdateReq,
				TwoDimensionalComponents.class);
		TwoDimensionalComponents twoDimensionalComponents = twoDimensionalComponentsMapper
				.selectById(twoDimensionalComponentsUpdateReq.getResourceId());
		if (null == twoDimensionalComponents) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		LambdaQueryWrapper<TwoDimensionalComponents> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(TwoDimensionalComponents::getName,
				twoDimensionalComponentsUpdateReq.getName());
		TwoDimensionalComponents one = twoDimensionalComponentsService.getOne(queryWrapper);
		if (null != one && !Objects.equals(one.getResourceId(),
				twoDimensionalComponentsUpdateReq.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		twoDimensionalComponentsMapper.updateById(patchObj);
		insertIntoDb();
		return new BaseRsp<>();
	}

	@SneakyThrows
	@ApiOperation("图表组件-导出")
	@GetMapping("export")
	public void exportTwoDimensionalComponents(
			@RequestParam("resourceId") @NotBlank String resourceId, HttpServletResponse response) {
		TwoDimensionalComponents twoDimensionalComponents = twoDimensionalComponentsMapper
				.selectById(resourceId);
		if (null == twoDimensionalComponents) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		String path = "/cpms/" + CommonConstant.TWO_DIMENSIONAL_COMPONENTS + resourceId + ".json";
		try {
			String jsonString = JsonUtil.toJsonString(twoDimensionalComponents);
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path),
					StandardCharsets.UTF_8);
			FileUtil.writeString(jsonString, osw);
			File file = new File(path);
			FileUtil.setResponse(file, true, response);
		} catch (Exception e) {
			log.error("failed to export pdf: {}", path);
			throw e;
		} finally {
			if (!FileUtil.deleteDir(new File(path))) {
				log.error("failed to delete dir: {}", path);
			}
		}
		insertIntoDb();
	}

	@ApiOperation("图表组件-导入")
	@PutMapping("import")
	public BaseRsp<TwoDimensionalComponents>
			importTwoDimensionalComponents(@RequestParam("file") MultipartFile file) {
		TwoDimensionalComponents twoDimensionalComponents = null;
		InputStream inputStream = null;
		try {
			FileUtil.validateSuffix(file, FileSunffixConstant.JSON.getDesc());
			inputStream = file.getInputStream();
			twoDimensionalComponents = JsonUtil.toJsonObject(inputStream,
					TwoDimensionalComponents.class);
		} catch (IOException e) {
			throw new BusinessException(SituationErrorCode.COMPONENTS_IMPORT_FAILED);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		insertIntoDb();
		return new BaseRsp<>(twoDimensionalComponents);
	}

	private void insertIntoDb() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

		SituationDbAccess situationDbAccess = new SituationDbAccess();
		situationDbAccess.setDbName("bc");
		situationDbAccess.setTableName("situation_components_chart");
		String requestURI = request.getRequestURI();
		if (requestURI.contains("category")) {
			situationDbAccess.setTableName("situation_components_chart_category");
		}
		String method = request.getMethod();
		if (method.equals("GET")) {
			situationDbAccess.setOperationType("查询");
			if (requestURI.contains("export")) {
				situationDbAccess.setOperationType("导出");
			}
		} else if (method.equals("PATCH") || method.equals("POST")) {
			situationDbAccess.setOperationType("写入");
		} else if (method.equals("PUT")) {
			situationDbAccess.setOperationType("导入");
		}
		String now = DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		situationDbAccess.setOperationTime(now);
		situationDbAccess.setOperationDetail(
				String.format("select * from %s", situationDbAccess.getTableName()));
		// situationDbAccess.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		situationDbAccessMapper.insert(situationDbAccess);
	}
}