package com.cyberpeace.oj.practice.controller.web;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.practice.controller.req.PersonalPaperCreateReq;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.PaperSummaryRes;
import com.cyberpeace.oj.practice.dao.entity.PersonalPaper;
import com.cyberpeace.oj.practice.service.PersonalPaperService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Api(tags = "oj-前台自由组卷相关api")
@RestController
@RequestMapping("/oj/web/paper")
@RequiresAuthentication
public class PersonalPaperController {

	@Autowired
	private PersonalPaperService personalPaperService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@ApiOperation("统计所有方向的做题进度")
	@GetMapping("/category/progress")
	public BaseRsp<List<CategoryProgressRes>> getProgressByCategory() {
		return new BaseRsp<>(practiceTheoryService.getProgressByCategory());
	}

	@ApiOperation("获取上次的组卷信息")
	@GetMapping("/paper_setting")
	public BaseRsp<PersonalPaperCreateReq> getPaperSetting() {
		return new BaseRsp<>(personalPaperService.getLastPaperSetting());
	}

	@ApiOperation("组卷")
	@GetMapping("/create")
	public BaseRsp<PersonalPaper> create(PersonalPaperCreateReq personalPaperCreateReq) {
		return new BaseRsp(personalPaperService.createPaper(personalPaperCreateReq));
	}

	@ApiOperation("知识技能Top6")
	@GetMapping("/category_top6")
	public BaseRsp<List<CategoryProgressRes>> getCategoryTop6() {
		List<CategoryProgressRes> categoryProgressResList = practiceTheoryService.getProgressByCategory();
		categoryProgressResList.sort(Comparator.comparing(categoryProgressRes -> categoryProgressRes.getCount() * 1.0 / categoryProgressRes.getSum()));
		Collections.reverse(categoryProgressResList);
		if (categoryProgressResList.size() > 6) {
			return new BaseRsp<>(categoryProgressResList.subList(0, 6));
		}
		return new BaseRsp<>(categoryProgressResList);
	}

	@ApiOperation("试卷概要")
	@GetMapping("/summary")
	public BaseRsp<PaperSummaryRes> getPaperSummary(@ApiParam(value = "试卷id") @RequestParam(value = "paper_id") String paperId) {
		return new BaseRsp(personalPaperService.getPaperSummaryRes(paperId));
	}

	@ApiOperation("查看练习详情")
	@GetMapping("/practice_detail")
	public BaseRsp<WebTheoryBo> getWebTheoryBo(@ApiParam(value = "练习id") @RequestParam(value = "practice_id") String practiceId) {
		return new BaseRsp<>(personalPaperService.getPracticeDetail(practiceId));
	}

}
