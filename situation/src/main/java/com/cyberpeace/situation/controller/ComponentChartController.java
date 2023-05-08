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

/**
 * 组件管理-图表组件
 *
 * @author chenhy
 * @date 2023/05/08
 */
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

	/**
	 * 图表组件类型-列表查询
	 *
	 * @return {@code BaseRsp<List<TwoDimensionalComponentsCategory>>}
	 */
	@ApiOperation("图表组件类型-列表查询")
	@GetMapping("/category/list")
	public BaseRsp<List<TwoDimensionalComponentsCategory>> getTwoDimensionalComponents() {

		// 从数据库中查询图表组件类型
		LambdaQueryWrapper<TwoDimensionalComponentsCategory> queryWrapper = new LambdaQueryWrapper<>();
		List<TwoDimensionalComponentsCategory> res = twoDimensionalComponentsCategoryMapper
				.selectList(queryWrapper);

		// 插入数据库查询日志
		insertIntoDb();
		return new BaseRsp<>(res);
	}

	/**
	 * 图表组件类型-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<TwoDimensionalComponents>}
	 */
	@ApiOperation("图表组件类型-单个查询")
	@GetMapping("")
	public BaseRsp<TwoDimensionalComponents> getThreeDimensionalComponentsCategoryDetail(
			@RequestParam("resourceId") @NotBlank String resourceId) {

		// 从数据库中查询图表组件类型
		TwoDimensionalComponents twoDimensionalComponents = twoDimensionalComponentsMapper
				.selectById(resourceId);

		// 判断是否查询到数据
		if (null == twoDimensionalComponents) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 写入数据库操作日志
		insertIntoDb();
		return new BaseRsp<>(twoDimensionalComponents);
	}

	/**
	 * 图表组件-新增
	 *
	 * @param twoDimensionalComponentsAddReq 图表组件新增请求
	 * @return {@code BaseRsp<TwoDimensionalComponents>}
	 */
	@ApiOperation("图表组件-新增")
	@PostMapping("")
	public BaseRsp<TwoDimensionalComponents> setTwoDimensionalComponents(
			@RequestBody @Validated(Create.class) TwoDimensionalComponentsAddReq twoDimensionalComponentsAddReq) {

		// 判断图表组件类型是否存在
		TwoDimensionalComponents twoDimensionalComponents = BeanUtils
				.copy(twoDimensionalComponentsAddReq, TwoDimensionalComponents.class);
		String type = twoDimensionalComponents.getType();
		TwoDimensionalComponentsCategory twoDimensionalComponentsCategory = twoDimensionalComponentsCategoryMapper
				.selectById(type);
		if (null == twoDimensionalComponentsCategory) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 写入创建人
		String userId = UserInfoContext.getUserContext().getUserId();
		twoDimensionalComponents.setCreateUserId(userId);

		// 插入并判断图表组件名称是否重复，重复则抛出异常
		try {
			twoDimensionalComponentsMapper.insert(twoDimensionalComponents);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 写入数据库操作日志
		insertIntoDb();
		return new BaseRsp<>(twoDimensionalComponents);
	}

	/**
	 * 图表组件-列表查询
	 *
	 * @param twoDimensionalComponentsFilterReq 图表组件查询请求
	 * @return {@code BaseRsp<List<SituationComponentsChartDetailsRes>>}
	 */
	@ApiOperation("图表组件-列表查询")
	@GetMapping("list")
	public BaseRsp<List<SituationComponentsChartDetailsRes>> getTwoDimensionalComponents(
			TwoDimensionalComponentsFilterReq twoDimensionalComponentsFilterReq) {

		// 如果图表组件名称不为空，则使用模糊查询
		LambdaQueryWrapper<TwoDimensionalComponents> queryWrapper = new LambdaQueryWrapper<>();
		if (null != twoDimensionalComponentsFilterReq.getName()) {
			queryWrapper.like(TwoDimensionalComponents::getName,
					twoDimensionalComponentsFilterReq.getName());
		}

		// 如果图表组件类型不为空，则使用类型匹配查询
		if (null != twoDimensionalComponentsFilterReq.getType()
				&& !twoDimensionalComponentsFilterReq.getType().equals("")) {
			queryWrapper.eq(TwoDimensionalComponents::getType,
					twoDimensionalComponentsFilterReq.getType());
		}

		// 设置排序规则
		queryWrapper.orderByDesc(TwoDimensionalComponents::getCreateTime);

		// 从数据库中查询图表组件
		List<TwoDimensionalComponents> res = twoDimensionalComponentsMapper
				.selectList(queryWrapper);

		// 将查询结果转换为返回结果
		List<SituationComponentsChartDetailsRes> result = new ArrayList<>();

		// 从数据库中查询图表组件类型
		List<TwoDimensionalComponentsCategory> twoDimensionalComponentsCategories = twoDimensionalComponentsCategoryMapper
				.selectList(null);
		Map<String, String> categoryMap = StreamApiUtils.toMap(twoDimensionalComponentsCategories,
				TwoDimensionalComponentsCategory::getResourceId,
				twoDimensionalComponentsCategory -> twoDimensionalComponentsCategory.getName());

		// 将查询结果转换为返回结果
		res.forEach(twoDimensionalComponent -> {
			SituationComponentsChartDetailsRes situationComponentsChartDetailsRes = new SituationComponentsChartDetailsRes();
			BeanUtils.copyProperties(twoDimensionalComponent, situationComponentsChartDetailsRes);
			String chartCategory = categoryMap.get(twoDimensionalComponent.getType());
			chartCategory = StringUtils.isEmpty(chartCategory) ? "" : chartCategory;
			situationComponentsChartDetailsRes.setChartCategoryName(chartCategory);
			result.add(situationComponentsChartDetailsRes);
		});

		// 写入远程审计日志
		remoteManageService.writeLog(ServiceLogContext.SubModule.TJZJ, ServiceLogContext.Type.CX);

		// 写入数据库操作日志
		insertIntoDb();

		return new BaseRsp<>(result);
	}

	/**
	 * 图表组件-删除
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("图表组件-删除")
	@DeleteMapping("")
	public BaseRsp
			deleteTwoDimensionalComponents(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		// 判断需要删除的图表组件是否存在
		List<String> resourceIds = batchDeleteReq.getIds();
		List<TwoDimensionalComponents> twoDimensionalComponents = twoDimensionalComponentsMapper
				.selectBatchIds(resourceIds);
		if (twoDimensionalComponents.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 删除图表组件
		twoDimensionalComponentsMapper.deleteBatchIds(resourceIds);

		// 写入数据库操作日志
		insertIntoDb();
		return new BaseRsp<>();
	}

	/**
	 * 图表组件-修改
	 *
	 * @param twoDimensionalComponentsUpdateReq 图表组件修改请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("图表组件-修改")
	@PatchMapping("")
	public BaseRsp patchTwoDimensionalComponents(
			@RequestBody @Validated(Update.class) TwoDimensionalComponentsUpdateReq twoDimensionalComponentsUpdateReq) {

		// 判断需要修改的图表组件是否存在
		TwoDimensionalComponents patchObj = BeanUtils.copy(twoDimensionalComponentsUpdateReq,
				TwoDimensionalComponents.class);
		TwoDimensionalComponents twoDimensionalComponents = twoDimensionalComponentsMapper
				.selectById(twoDimensionalComponentsUpdateReq.getResourceId());
		if (null == twoDimensionalComponents) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 判断图表组件名称是否重复
		LambdaQueryWrapper<TwoDimensionalComponents> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(TwoDimensionalComponents::getName,
				twoDimensionalComponentsUpdateReq.getName());
		TwoDimensionalComponents one = twoDimensionalComponentsService.getOne(queryWrapper);
		if (null != one && !Objects.equals(one.getResourceId(),
				twoDimensionalComponentsUpdateReq.getResourceId())) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 修改图表组件
		twoDimensionalComponentsMapper.updateById(patchObj);

		// 写入数据库操作日志
		insertIntoDb();
		return new BaseRsp<>();
	}

	/**
	 * 图表组件-导出
	 *
	 * @param resourceId 资源id
	 * @param response   响应
	 */
	@SneakyThrows
	@ApiOperation("图表组件-导出")
	@GetMapping("export")
	public void exportTwoDimensionalComponents(
			@RequestParam("resourceId") @NotBlank String resourceId, HttpServletResponse response) {

		// 判断需要导出的图表组件是否存在
		TwoDimensionalComponents twoDimensionalComponents = twoDimensionalComponentsMapper
				.selectById(resourceId);
		if (null == twoDimensionalComponents) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 在cpms目录下生成图表组件文件并导出
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

	/**
	 * 图表组件-导入
	 * todo: 确认是否使用
	 *
	 * @param file 文件
	 * @return {@code BaseRsp<TwoDimensionalComponents>}
	 */
	@ApiOperation("图表组件-导入")
	@PutMapping("import")
	public BaseRsp<TwoDimensionalComponents>
			importTwoDimensionalComponents(@RequestParam("file") MultipartFile file) {

		TwoDimensionalComponents twoDimensionalComponents = null;
		InputStream inputStream = null;
		try {

			// 判断文件后缀是否为json
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

	/**
	 * 写入数据库操作日志
	 */
	private void insertIntoDb() {

		// 获取请求信息
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

		// 构造数据库操作日志对象
		SituationDbAccess situationDbAccess = new SituationDbAccess();
		// 填充基本信息
		situationDbAccess.setDbName("bc");
		situationDbAccess.setTableName("situation_components_chart");
		String requestURI = request.getRequestURI();
		if (requestURI.contains("category")) {
			situationDbAccess.setTableName("situation_components_chart_category");
		}

		// 填充操作类型
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

		// 写入数据库操作日志
		situationDbAccessMapper.insert(situationDbAccess);
	}
}