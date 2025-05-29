package com.cyberpeace.gateway.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.bo.OperationListQueryBo;
import com.cyberpeace.oj.practice.service.externalbo.ExternalOperationCountBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.annoations.AuthOjKey;
import com.cyberpeace.common.annoations.AuthKeyUser;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.dao.mapper.AttachmentMapper;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.gateway.controller.req.ExternalBatchReq;
import com.cyberpeace.gateway.controller.req.OperationPageReq;
import com.cyberpeace.gateway.service.bo.ExternalOperationBo;
import com.cyberpeace.gateway.service.bo.ExternalPracticeCategoryBo;
import com.cyberpeace.gateway.service.bo.ExternalPracticeSetBo;
import com.cyberpeace.oj.practice.controller.req.external.PracticeRandomChoiceReq;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.bo.PracticeOperationFileBo;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 外部实操Controller
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ExternalOperationController` 的 Spring Boot 控制器，用于处理与实践操作相关的外部 API。以下是该控制器的主要功能和结构的简要分析： 
 * ### 主要功能 
 * 1. **习题集查询 (`/practice-set/list`)** 
 * - 获取用户有权限访问的习题集，并将其转换为 `ExternalPracticeSetBo` 对象返回。 
 * 2. **实操习题分类查询 (`/category/list`)** 
 * - 根据提供的实践设置 ID 查询相应的实操习题分类。 
 * - 如果没有提供实践设置 ID，则返回所有分类。 
 * 3. **实操习题批量查询 (`/batch-get`)** 
 * - 根据提供的习题 ID 列表查询多个实操习题，并将其转换为 `ExternalOperationBo` 对象返回。 
 * 4. **实操习题分页查询 (`/list`)** 
 * - 根据请求参数进行分页查询，并返回查询结果。 
 * 5. **实操习题随机获取 (`/random-choice`)** 
 * - 随机获取指定数量的实操习题，并返回结果。 
 * 6. **获取各类型习题数量 (`/type/count`)** 
 * - 获取不同类型的实操习题的数量。 
 * ### 技术要点 
 * - **依赖注入** 
 * - 使用 `@Autowired` 注入了多个 Mapper 和 Service 类，如 `PracticeOperationMapper`, `PracticeCategoryMapper`, `PracticeSetMapper`, `AttachmentMapper`, `UtilService`, `PracticeSetService`, `PracticeOperationService`。 
 * - **数据转换** 
 * - 使用 `BeanUtils` 工具类将实体对象转换为业务对象（例如 `PracticeOperation` 转换为 `ExternalOperationBo`）。 
 * - **权限控制** 
 * - 使用了自定义注解 `@AuthKeyUser` 和 `@AuthOjKey` 进行权限控制。 
 * - **分页和排序** 
 * - 使用 `Page` 和 `IPage` 对象进行分页查询，并通过 `PageUtil` 设置分页参数。 
 * - **日志记录** 
 * - 使用 `@Slf4j` 注解启用日志记录，方便调试和问题追踪。 
 * ### 总结 
 * `ExternalOperationController` 是一个处理实践操作相关外部 API 的控制器。它通过依赖注入获取必要的服务和映射器，并通过各种方法处理不同的查询请求，包括习题集查询、分类查询、批量查询、分页查询、随机查询以及统计查询。整体设计合理，功能清晰，使用了现代 Java 技术和框架特性。 
**/
@Api(tags = "外部接口-实操习题相关api")
@Slf4j
@AuthKeyUser
@RestController
@RequestMapping("/gateway/exercise")
public class ExternalOperationController {

	/**
	 * 实践实操Mapper
	 */
	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	/**
	 * 实践类别Mapper
	 */
	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	/**
	 * 实践设置Mapper
	 */
	@Autowired
	private PracticeSetMapper practiceSetMapper;

	/**
	 * 附件Mapper
	 */
	@Autowired
	private AttachmentMapper attachmentMapper;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 实践设置Service
	 */
	@Autowired
	private PracticeSetService practiceSetService;

	/**
	 * 实践实操Service
	 */
	@Autowired
	private PracticeOperationService practiceOperationService;

	/**
	 * 实践设置列表
	 *
	 * @return {@code BaseRsp<List<ExternalPracticeSetBo>> }
	 */
	@ApiOperation("习题集查询")
	@GetMapping("/practice-set/list")
	public BaseRsp<List<ExternalPracticeSetBo>> practiceSetList() {

		UserInfo userInfo = UserInfoContext.getUserContext();

		List<String> practiceSetIds = practiceSetMapper
				.selectByAuthContext(AuthContextUtils.constructAuth(userInfo, false));
		if (CollectionUtils.isEmpty(practiceSetIds)) {
			return new BaseRsp(Lists.newArrayList());
		}

		// 数据查询
		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.OPERATION.getCode(), false);
		List<ExternalPracticeSetBo> practiceSetBos = practiceSets.stream()
				.filter(practiceSet -> practiceSetIds.contains(practiceSet.getResourceId()))
				.map(practiceSet -> BeanUtils.copy(practiceSet, ExternalPracticeSetBo.class))
				.collect(Collectors.toList());

		return new BaseRsp(practiceSetBos);
	}

	/**
	 * 类别列表
	 *
	 * @param practiceSetId 实践设置id
	 * @return {@code BaseRsp<List<ExternalPracticeCategoryBo>> }
	 */
	@AuthOjKey
	@ApiOperation("实操习题分类查询")
	@GetMapping("/category/list")
	public BaseRsp<List<ExternalPracticeCategoryBo>> categoryList(@RequestParam(name = "practice_set_id", required = false) String practiceSetId) {

		List<PracticeCategory> categoryList = practiceCategoryMapper
				.listByTypeAndName(PracticeCategory.PracticeType.OPERATION.getCode(), null);
		List<ExternalPracticeCategoryBo> categoryBos = BeanUtils.deepCopyList(categoryList,
				ExternalPracticeCategoryBo.class);

		if(!StringUtils.hasText(practiceSetId)) {
			return new BaseRsp<>(categoryBos);
		}

		// 将实操题的方向和习题集全部查出来
		List<PracticeOperation> allCategoryIds = practiceOperationService.getCategoryIdAndSetIds();
// 筛选出对应习题集的方向id
		if(org.apache.commons.collections.CollectionUtils.isEmpty(allCategoryIds)) {
			return new BaseRsp<>();
		}
		List<String> categoryIds = allCategoryIds.stream().filter(item -> item.getPracticeSetId().equals(practiceSetId))
				.map(PracticeOperation::getCategoryId).collect(Collectors.toList());
		// 将对应的方向数据筛选出来
		categoryBos = categoryBos.stream().filter(item -> categoryIds.contains(item.getResourceId())).collect(Collectors.toList());

		return new BaseRsp<>(categoryBos);
	}

	/**
	 * 批量获取
	 *
	 * @param batchReq 批量请求体
	 * @return {@code BaseRsp<List<ExternalOperationBo>> }
	 */
	@AuthOjKey
	@ApiOperation("实操习题批量查询")
	@GetMapping("/batch-get")
	public BaseRsp<List<ExternalOperationBo>> batchGet(ExternalBatchReq batchReq) {

		List<PracticeOperation> operationList = practiceOperationMapper
				.selectBatchIds(batchReq.getResourceIds());
		List<ExternalOperationBo> operationBos = StreamApiUtils.map(operationList,
				practiceOperation -> entityToExternal(practiceOperation));

		return new BaseRsp(operationBos);
	}

	/**
	 * 实体类转换为外部字段
	 *
	 * @param operation
	 * @return
	 */
	private ExternalOperationBo entityToExternal(PracticeOperation operation) {
		ExternalOperationBo externalOperationBo = BeanUtils.copy(operation,
				ExternalOperationBo.class);
		externalOperationBo.setCreateUser(operation.getCreateUserId());

		// 分类
		PracticeCategory practiceCategory = practiceCategoryMapper
				.selectById(operation.getCategoryId());
		if (null != practiceCategory) {
			externalOperationBo.setCategoryInfo(practiceCategory.getName());
		}

		if (StringUtils.hasText(operation.getAttachmentId())) {
			if (null != operation.getDynamicFlag() && operation.getDynamicFlag()
					.intValue() == PracticeOperation.DynamicFlag.RANDOM.getCode().intValue()) {

				List<PracticeOperationFileBo> fileBos = practiceOperationService
						.getOperationFile(operation.getAttachmentId());
				externalOperationBo.setAttachment(fileBos);

			} else {
				Attachment attachment = attachmentMapper.selectById(operation.getAttachmentId());
				String fileUrl = utilService.handlerFileUrl(attachment.getFilePath());
				externalOperationBo.setFileUrl(fileUrl);
				externalOperationBo.setFileName(attachment.getFileName());
			}

		}

		externalOperationBo.setIsDynamicEnv(operation.getDynamicEnv());
		externalOperationBo.setIsDynamicFlag(operation.getDynamicFlag());
		externalOperationBo.setShareMode(operation.getSceneShareMode());

		return externalOperationBo;
	}

	/**
	 * 分页列表
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<ListRsp<ExternalOperationBo>> }
	 */
	@ApiOperation("实操习题分页查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<ExternalOperationBo>> pageList(OperationPageReq req) {

		UserInfo userInfo = UserInfoContext.getUserContext();
		Page page = req.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		// 条件组装
		OperationListQuery listQuery = BeanUtils.copy(req, OperationListQuery.class);
		List<String> setIds = practiceSetService
				.getSetIds(AuthContextUtils.constructAuth(userInfo, false), req.getPracticeSet());
		if (CollectionUtils.isEmpty(setIds)) {
			return new BaseRsp(PageUtil.getNullResult());
		}
		listQuery.setPracticeSetIds(setIds);
		List<String> practiceIds = practiceOperationMapper.selectBySetIds(setIds, true);
		listQuery.setResourceIds(practiceIds);

		if (StringUtils.hasText(req.getCategory())) {
			listQuery.setCategoryIds(Lists.newArrayList(req.getCategory()));
		}

		// 条件查询
		IPage<PracticeOperation> iPage = practiceOperationMapper.pageByQuery(page, listQuery, true);
		IPage<ExternalOperationBo> convert = iPage
				.convert(practiceOperation -> entityToExternal(practiceOperation));
		return new BaseRsp(PageUtil.getListResult(convert));
	}

	/**
	 * 获取实操随机
	 *
	 * @param choiceReq 选择请求体
	 * @return {@code BaseRsp<List<ExternalOperationBo>> }
	 */
	@ApiOperation("实操习题随机获取数条")
	@GetMapping("/random-choice")
	public BaseRsp<List<ExternalOperationBo>>
			getOperationRandom(PracticeRandomChoiceReq choiceReq) {
		UserInfo userInfo = UserInfoContext.getUserContext();

		OperationListQuery listQuery = BeanUtils.copy(choiceReq, OperationListQuery.class);
		if (StringUtils.hasText(choiceReq.getCategory())) {
			listQuery.setCategoryIds(Lists.newArrayList(choiceReq.getCategory()));
		}

		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		List<String> setIds = practiceSetService.getSetIds(authContext, choiceReq.getPracticeSet());
		if (CollectionUtils.isEmpty(setIds)) {
			return new BaseRsp<>(new ArrayList<>());
		}
		listQuery.setPracticeSetIds(setIds);

		List<String> practiceIds = practiceOperationMapper.selectBySetIds(setIds, true);
		if (CollectionUtils.isEmpty(practiceIds)) {
			return new BaseRsp<>(new ArrayList<>());
		}
		listQuery.setResourceIds(practiceIds);

		List<PracticeOperation> operationList = practiceOperationMapper.randomChoice(listQuery,
				choiceReq.getCount());
		List<ExternalOperationBo> boList = StreamApiUtils.map(operationList,
				operation -> entityToExternal(operation));
		for (ExternalOperationBo operationBo : boList) {
			operationBo.setScore(choiceReq.getScore());
		}

		return new BaseRsp<>(boList);
	}

	/**
	 * 搜索计数
	 *
	 * @param pageReq 分页请求体
	 * @return {@code BaseRsp<ExternalOperationCountBo> }
	 */
	@ApiOperation("获取各类型习题数量")
	@GetMapping("/type/count")
	public BaseRsp<ExternalOperationCountBo> searchCount(OperationPageReq pageReq) {

		UserInfo userInfo = UserInfoContext.getUserContext();

		OperationListQueryBo queryBo = BeanUtils.copy(pageReq, OperationListQueryBo.class);
		queryBo.setPracticeSetId(pageReq.getPracticeSet());
		if (StringUtils.hasText(pageReq.getCategory())) {
			queryBo.setCategoryIds(Lists.newArrayList(pageReq.getCategory()));
		}

		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		ExternalOperationCountBo countBo = practiceOperationService.typeCount(authContext, queryBo);
		return new BaseRsp<>(countBo);
	}

}

