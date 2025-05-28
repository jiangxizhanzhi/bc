/**
 * 这段代码是一个Spring Boot控制器类 `ExternalTheoryController`，用于处理与外部理论相关的API请求。以下是该代码的简要分析： 
 * ### 包含的依赖和注解 
 * - **依赖**： 
 * - `org.springframework.web.bind.annotation.*`：用于定义RESTful API。 
 * - `com.baomidou.mybatisplus.*`：MyBatis Plus的元数据和分页插件。 
 * - `io.swagger.annotations.*`：用于生成API文档。 
 * - `lombok.extern.slf4j.Slf4j`：日志记录。 
 * - **注解**： 
 * - `@RestController`：表明这是一个REST控制器。 
 * - `@RequestMapping("/gateway/theory")`：定义了该控制器的基本路径。 
 * - `@Slf4j`：Lombok提供的日志记录功能。 
 * ### 主要方法 
 * 1. **获取实践设置列表 (`getPracticeSetList`)** 
 * - **描述**：获取用户有权访问的理论习题集列表。 
 * - **实现**：通过用户信息和权限上下文来过滤习题集，并将结果转换为 `ExternalPracticeSetBo` 对象列表。 
 * 2. **获取类别列表 (`getCategoryList`)** 
 * - **描述**：获取理论分类列表。 
 * - **实现**：根据传入的实践设置ID过滤分类列表。 
 * 3. **获取理论列表 (`getTheoryList`)** 
 * - **描述**：分页查询理论习题。 
 * - **实现**：通过分页参数、查询条件和用户权限来获取理论习题列表。 
 * 4. **批量获取 (`batchGet`)** 
 * - **描述**：根据资源ID批量获取理论习题。 
 * - **实现**：直接从数据库中批量查询理论习题。 
 * 5. **获取理论随机 (`getTheoryRandom`)** 
 * - **描述**：随机获取一定数量的理论习题。 
 * - **实现**：根据用户权限和查询参数随机选择理论习题。 
 * 6. **搜索计数 (`searchCount`)** 
 * - **描述**：查询各类别理论习题的数量。 
 * - **实现**：根据查询参数和用户权限统计各类别理论习题的数量。 
 * ### 主要特点 
 * - **权限控制**：使用了自定义注解 `@AuthKeyUser` 和 `@AuthOjKey` 来控制不同方法的访问权限。 
 * - **数据校验**：使用了Spring的验证注解 `@Validated` 和自定义请求体类来确保输入数据的有效性。 
 * - **分页和排序**：通过 `Page` 对象实现分页和排序功能。 
 * - **对象映射**：使用 `BeanUtils` 进行对象之间的映射。 
 * ### 总结 
 * 该控制器类主要用于处理与理论习题相关的API请求，包括获取习题集、分类、分页查询、随机获取和统计等操作。通过合理的权限控制和数据校验，确保了系统的安全性和可靠性。 
**/
package com.cyberpeace.gateway.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.constant.WebConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
import com.cyberpeace.gateway.service.bo.ExternalPracticeCategoryBo;
import com.cyberpeace.gateway.service.bo.ExternalPracticeSetBo;
import com.cyberpeace.oj.practice.controller.req.external.PracticeRandomChoiceReq;
import com.cyberpeace.oj.practice.controller.req.external.PracticeTheoryListReq;
import com.cyberpeace.oj.practice.dao.context.TheoryListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.TheoryListQueryBo;
import com.cyberpeace.oj.practice.service.externalbo.ExternalTheoryCountBo;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 外部理论Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "外部接口-理论习题相关api")
@Slf4j
@AuthKeyUser
@RestController
@RequestMapping("/gateway/theory")
public class ExternalTheoryController {

	/**
	 * 实践理论Mapper
	 */
	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	/**
	 * 实践设置Mapper
	 */
	@Autowired
	private PracticeSetMapper practiceSetMapper;

	/**
	 * 实践类别Mapper
	 */
	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	/**
	 * 实践设置Service
	 */
	@Autowired
	private PracticeSetService practiceSetService;

	/**
	 * 实践理论Service
	 */
	@Autowired
	private PracticeTheoryService practiceTheoryService;

	/**
	 * 获取实践设置列表
	 *
	 * @return {@code BaseRsp<List<ExternalPracticeSetBo>> }
	 */
	@ApiOperation("理论基础习题集")
	@GetMapping("/practice-set/list")
	public BaseRsp<List<ExternalPracticeSetBo>> getPracticeSetList() {

		// 组织校验
		UserInfo userInfo = UserInfoContext.getUserContext();
		
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		List<String> setIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(setIds)) {
			return new BaseRsp<>(Lists.newArrayList());
		}

		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.THEORY.getCode(), false);
		List<ExternalPracticeSetBo> setBoList = practiceSets.stream()
				.filter(practiceSet -> setIds.contains(practiceSet.getResourceId()))
				.map(practiceSet -> BeanUtils.copy(practiceSet, ExternalPracticeSetBo.class))
				.collect(Collectors.toList());

		return new BaseRsp<>(setBoList);
	}

	/**
	 * 获取类别列表
	 *
	 * @param practiceSetId 实践设置id
	 * @return {@code BaseRsp<List<ExternalPracticeCategoryBo>> }
	 */
	@AuthOjKey
	@ApiOperation("理论基础分类-全量获取")
	@GetMapping("/category/list")
	public BaseRsp<List<ExternalPracticeCategoryBo>> getCategoryList(
			@RequestParam(name = "practice_set_id", required = false) String practiceSetId) {

		List<PracticeCategory> categoryList = practiceCategoryMapper
				.listByTypeAndName(PracticeCategory.PracticeType.THEORY.getCode(), null);

		List<ExternalPracticeCategoryBo> categoryBoList = BeanUtils.deepCopyList(categoryList,
				ExternalPracticeCategoryBo.class);

		if(!StringUtils.hasText(practiceSetId)) {
			return new BaseRsp<>(categoryBoList);
		}

		// 将理论题的方向和习题集全部查出来
		List<PracticeTheory> allCategoryIds = practiceTheoryService.getCategoryIdAndSetIds();
		// 筛选出对应习题集的方向id
		if(CollectionUtils.isEmpty(allCategoryIds)) {
			return new BaseRsp<>();
		}
		List<String> categoryIds = allCategoryIds.stream().filter(item -> item.getPracticeSetId().equals(practiceSetId))
				.map(PracticeTheory::getCategoryId).collect(Collectors.toList());
		// 将对应的方向数据筛选出来
		categoryBoList = categoryBoList.stream().filter(item -> categoryIds.contains(item.getResourceId())).collect(Collectors.toList());

		return new BaseRsp<>(categoryBoList);
	}

	/**
	 * 获取理论列表
	 *
	 * @param queryParams 查询参数
	 * @return {@code BaseRsp<ListRsp<PracticeTheory>> }
	 */
	@ApiOperation("理论基础题库-分页查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<PracticeTheory>> getTheoryList(PracticeTheoryListReq queryParams) {
		
		// 数据校验
		UserInfo userInfo = UserInfoContext.getUserContext();

		// 习题集权限查询
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		List<String> setIds = practiceSetService.getSetIds(authContext,
				queryParams.getPracticeSet());
		if (CollectionUtils.isEmpty(setIds)) {
			return new BaseRsp<>(PageUtil.getNullResult());
		}

		Page page = queryParams.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		IPage<PracticeTheory> result = practiceTheoryMapper.getList(page, queryParams.getPattern(),
				setIds, queryParams.getCategory(), queryParams.getQuestionType(),
				queryParams.getDifficulty());
		return new BaseRsp<>(PageUtil.getListResult(result));
	}

	/**
	 * 批量获取
	 *
	 * @param resourceIds 资源id列表
	 * @return {@code BaseRsp<List<PracticeTheory>> }
	 */
	@ApiOperation("理论基础题库-批量查询")
	@GetMapping("/batch-get")
	@AuthOjKey
	public BaseRsp<List<PracticeTheory>> batchGet(
			@ApiParam("资源编号数组") @RequestParam("resource_ids") @NotBlank List<String> resourceIds) {
		
		if (resourceIds == null) {
			return new BaseRsp<>(Lists.newArrayList());
		}
		return new BaseRsp<>(practiceTheoryMapper.batchGet(resourceIds));
	}

	/**
	 * 获取理论随机
	 *
	 * @param queryParams 查询参数
	 * @return {@code BaseRsp<List<PracticeTheory>> }
	 */
	@ApiOperation("理论基础题库-随机获取数条")
	@GetMapping("/random-choice")
	public BaseRsp<List<PracticeTheory>> getTheoryRandom(PracticeRandomChoiceReq queryParams) {
		
		UserInfo userInfo = UserInfoContext.getUserContext();
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		List<String> setIds = practiceSetService.getSetIds(authContext,
				queryParams.getPracticeSet());
		if (CollectionUtils.isEmpty(setIds)) {
			return new BaseRsp<>(Lists.newArrayList());
		}

		TheoryListQuery listQuery = BeanUtils.copy(queryParams, TheoryListQuery.class);
		if (StringUtils.hasText(queryParams.getCategory())) {
			listQuery.setCategoryIds(Lists.newArrayList(queryParams.getCategory()));
		}
		listQuery.setPracticeSetIds(setIds);
		List<String> practiceIds = practiceTheoryMapper.selectBySetIds(setIds, true);
		listQuery.setResourceIds(practiceIds);

		List<PracticeTheory> result = practiceTheoryMapper.randomChoice(listQuery,
				queryParams.getCount());
		for (PracticeTheory item : result) {
			item.setScore(queryParams.getScore());
		}
		return new BaseRsp<>(result);
	}

	/**
	 * 搜索计数
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<ExternalTheoryCountBo> }
	 */
	@ApiOperation("查询各类型理论习题数量")
	@GetMapping("/type/count")
	public BaseRsp<ExternalTheoryCountBo> searchCount(@Validated PracticeTheoryListReq listReq) {

		UserInfo userInfo = UserInfoContext.getUserContext();

		// 设置查询条件
		TheoryListQueryBo queryBo = BeanUtils.copy(listReq, TheoryListQueryBo.class);
		if (StringUtils.hasText(listReq.getCategory())) {
			queryBo.setCategoryIds(Lists.newArrayList(listReq.getCategory()));
		}
		queryBo.setPracticeSetId(listReq.getPracticeSet());

		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		ExternalTheoryCountBo countBo = practiceTheoryService.typeCount(queryBo, authContext);
		return new BaseRsp<>(countBo);
	}

}
