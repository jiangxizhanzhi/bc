package com.cyberpeace.oj.practice.controller.admin;

import java.util.List;

import com.cyberpeace.common.util.*;
import com.cyberpeace.oj.practice.controller.res.PracticeSetRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.practice.controller.req.LibPracticeListReq;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "oj-后台课后练习和试卷相关api")
@RestController
@RequestMapping("/oj/admin/paper")
@RequiresAuthentication
public class AdminPaperController {

	@Autowired
	private PaperService paperService;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@ApiOperation("获取习题库习题列表")
	@GetMapping("/practice/list")
	public BaseRsp<PracticeLibInfoBo> selectPracticeInfo(LibPracticeListReq practiceListReq) {

		// 查询数据组装
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		Page page = practiceListReq.toPageParam();
		LibPracticeQueryBo queryBo = BeanUtils.copy(practiceListReq, LibPracticeQueryBo.class);

		// 分类数据组装
		if (StringUtils.isNotBlank(practiceListReq.getCategoryId())) {
			List<String> categoryIds = practiceCategoryService
					.selectChildrenIds(practiceListReq.getCategoryId(), true);
			queryBo.setCategoryIds(categoryIds);
		}

		// 习题集数据查询
		List<PracticeSetRes> practiceSetRes = selectPracticeSet(authContext);
		PracticeLibInfoBo libInfoBo = new PracticeLibInfoBo();
		libInfoBo.setPracticeSet(practiceSetRes);

		if (StringUtils.isNotBlank(practiceListReq.getPracticeSetId())) {
			// 联动分类数据查询
			PracticeSet practiceSet = practiceSetMapper
					.selectById(practiceListReq.getPracticeSetId());
			List<CategoryListBo> categoryListBos = selectPracticeCategory(
					practiceSet.getPracticeType(), practiceListReq.getCategoryChildren());
			libInfoBo.setCategory(categoryListBos);

			// 习题类型数据查询
			List<Integer> questionType = selectQuestionType(practiceSet.getPracticeType());
			libInfoBo.setQuestionType(questionType);
		}

		// 习题数据查询
		IPage<LibPracticeListBo> iPage = paperService.selectLibPractice(page, queryBo, authContext);
		libInfoBo.setPracticeList(PageUtil.getListResult(iPage));
		return new BaseRsp(libInfoBo);
	}

	/**
	 * 获取习题类型
	 * @param practiceType
	 * @return
	 */
	private List<Integer> selectQuestionType(int practiceType) {

		if (practiceType == PracticeCategory.PracticeType.OPERATION.getCode().intValue()) {
			List<Integer> listCode = EnumUtils.getListCode(PracticeTheory.TheoryType.class);
			listCode.remove(PracticeTheory.TheoryType.OPERATION.getCode());
			return listCode;
		} else {
			return Lists.newArrayList(PracticeTheory.TheoryType.OPERATION.getCode());
		}
	}

	/**
	 * 习题集分类查询
	 * @param practiceType
	 * @return
	 */
	private List<CategoryListBo> selectPracticeCategory(int practiceType, boolean children) {
		CategoryListQuery listQuery = CategoryListQuery.builder().practiceType(practiceType)
				.build();

		return practiceCategoryService.listByTypeAndName(listQuery, children);
	}

	/**
	 * 获取习题集
	 * @param authContext
	 * @return
	 */
	private List<PracticeSetRes> selectPracticeSet(AuthContext authContext) {

		List<String> practiceSetIds = practiceSetMapper.selectByAuthContext(authContext);
		List<PracticeSet> practiceSets = practiceSetMapper.selectBatchIds(practiceSetIds);
		return StreamApiUtils.simpleMap(practiceSets,
				practiceSet -> BeanUtils.copy(practiceSet, PracticeSetRes.class));
	}

}
