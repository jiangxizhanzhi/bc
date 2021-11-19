package com.cyberpeace.oj.practice.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.LibPracticeListReq;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.bo.LibPracticeListBo;
import com.cyberpeace.oj.practice.service.bo.LibPracticeQueryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "oj-后台课后练习和试卷相关api")
@RestController
@RequestMapping("/oj/admin/paper")
@RequiresAuthentication
public class AdminPaperController {

	@Autowired
	private PaperService paperService;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@ApiOperation("获取习题库习题列表")
	@GetMapping("/practice/list")
	public BaseRsp selectPracticeInfo(LibPracticeListReq practiceListReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		Page page = practiceListReq.toPageParam();

		LibPracticeQueryBo queryBo = BeanUtils.copy(practiceListReq, LibPracticeQueryBo.class);

		if (StringUtils.isNotBlank(practiceListReq.getCategoryId())) {
			List<String> categoryIds = practiceCategoryService
					.selectChildrenIds(practiceListReq.getCategoryId(), true);
			queryBo.setCategoryIds(categoryIds);
		}

		IPage<LibPracticeListBo> iPage = paperService.selectLibPractice(page, queryBo, authContext);

		return new BaseRsp(PageUtil.getListResult(iPage));
	}

}
