package com.cyberpeace.oj.practice.controller.web;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.ojbase.controller.req.CollectionReq;
import com.cyberpeace.oj.practice.controller.req.PracticePaperCreateReq;
import com.cyberpeace.oj.practice.controller.req.SubmitAnswerReq;
import com.cyberpeace.oj.practice.controller.res.*;
import com.cyberpeace.oj.practice.dao.entity.PracticePaper;
import com.cyberpeace.oj.practice.dao.mapper.PracticePaperMapper;
import com.cyberpeace.oj.practice.service.PaperPracticeTheoryService;
import com.cyberpeace.oj.practice.service.PracticePaperService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.EvaluateResultTheory;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "oj-前台自由组卷相关api")
@RestController
@RequestMapping("/oj/web/paper")
@RequiresAuthentication
public class PracticePaperController {

	@Autowired
	private PracticePaperService practicePaperService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private PaperPracticeTheoryService paperPracticeTheoryService;

	@Autowired
	private PracticePaperMapper practicePaperMapper;

	@ApiOperation("统计所有方向的做题进度")
	@GetMapping("/category/progress")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<List<CategoryProgressRes>> getProgressByCategory() {
		return new BaseRsp<>(practiceTheoryService.getProgressByCategory());
	}

	@ApiOperation("获取上次的组卷信息")
	@GetMapping("/paper_setting")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<PracticePaperCreateReq> getPaperSetting() {
		return new BaseRsp<>(practicePaperService.getLastPaperSetting());
	}

	@ApiOperation("组卷")
	@PostMapping("/create")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<PracticePaper> create(@RequestBody @Validated PracticePaperCreateReq practicePaperCreateReq) {
		return new BaseRsp<>(practicePaperService.createPaper(practicePaperCreateReq));
	}

	@ApiOperation("知识技能Top6")
	@GetMapping("/category_top6")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<List<CategoryProgressRes>> getCategoryTop6() {
		List<CategoryProgressRes> list = practiceTheoryService.getProgressByCategory();
		//总题数可能为0,无法正常排序,先过滤sum不为0的排序,后加入为0的数据
		LinkedHashSet<CategoryProgressRes> existSet = list.stream().filter(item -> 0 != item.getSum()).sorted((o1, o2) -> {
			Double one = o1.getCount() * 1.0 / o1.getSum();
			Double two = o2.getCount() * 1.0 / o2.getSum();
			return two.compareTo(one);
		}).collect(Collectors.toCollection(LinkedHashSet::new));
		existSet.addAll(list);
		List<CategoryProgressRes> result = existSet.stream().limit(6).collect(Collectors.toList());
		return new BaseRsp<>(result);
	}

	@ApiOperation("试卷概要")
	@GetMapping("/summary")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<PaperSummaryRes> getPaperSummary(@ApiParam(value = "试卷id") @RequestParam(value = "paper_id") String paperId) {
		return new BaseRsp<>(practicePaperService.getPaperSummaryRes(paperId));
	}

	@ApiOperation("查看练习详情")
	@GetMapping("/practice_detail")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<WebTheoryBo> getWebTheoryBo(@ApiParam(value = "练习id") @RequestParam(value = "practice_id") String practiceId) {
		return new BaseRsp<>(paperPracticeTheoryService.getPracticeDetail(practiceId));
	}

	@ApiOperation("提交答案")
	@PostMapping("/submit_answer")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<EvaluateResultTheory> submitAnswer(@RequestBody @Validated SubmitAnswerReq answerReq) {
		return new BaseRsp<>(paperPracticeTheoryService.submitAnswer(answerReq.getResourceId(), answerReq.getSubmitAnswer()));
	}

	@ApiOperation("(取消)收藏")
	@PostMapping("/practice_collection")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<Boolean> collection(@RequestBody @Validated CollectionReq collectionReq) {
		return new BaseRsp<>(paperPracticeTheoryService.collection(collectionReq));
	}

	@ApiOperation("交卷")
	@GetMapping("/submit_paper")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<Boolean> submitPaper(@ApiParam(value = "试卷id") @RequestParam(value = "paper_id") String paperId) {
		PracticePaper practicePaper = practicePaperMapper.selectById(paperId);
		practicePaper.setIsFinish(true);
		practicePaperMapper.updateById(practicePaper);
		return new BaseRsp<>(true);
	}

	@ApiOperation("最近5条组卷记录")
	@GetMapping("/record")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<List<PaperRecordRes>> getLast5PaperRecord() {
		List<PaperRecordRes> paperRecordResList = practicePaperService.getPaperRecord();
		if (paperRecordResList.size() > 5) {
			return new BaseRsp<>(paperRecordResList.subList(0, 5));
		}else {
			return new BaseRsp<>(paperRecordResList);
		}
	}

	@ApiOperation("组卷记录概要")
	@GetMapping("/record_summary")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<List<PaperRecordSummaryResByDay>> getRecordSummary(@ApiParam("是否已结束") @RequestParam(value = "is_finish" , required = false) Boolean isFinish) {
		return new BaseRsp<>(practicePaperService.getAllPaperRecord(isFinish));
	}

	@ApiOperation("查看组卷详情")
	@GetMapping("/record_detail")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<PaperRecordDetailRes> getPaperRecordDetail(@ApiParam("试卷id") @RequestParam(value = "paper_id") String paperId) {
		return new BaseRsp<>(practicePaperService.getPaperRecordDetail(paperId));
	}

	@ApiOperation("再练一次")
	@GetMapping("/practice_again")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<PracticePaper> practiceAgain(@ApiParam("试卷id") @RequestParam(value = "paper_id") String paperId) {
		return new BaseRsp<>(practicePaperService.practiceAgain(paperId));
	}

}
