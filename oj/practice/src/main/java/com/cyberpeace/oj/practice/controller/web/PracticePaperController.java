package com.cyberpeace.oj.practice.controller.web;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
	public BaseRsp<List<CategoryProgressRes>> getProgressByCategory() {
		return new BaseRsp<>(practiceTheoryService.getProgressByCategory());
	}

	@ApiOperation("获取上次的组卷信息")
	@GetMapping("/paper_setting")
	public BaseRsp<PracticePaperCreateReq> getPaperSetting() {
		return new BaseRsp<>(practicePaperService.getLastPaperSetting());
	}

	@ApiOperation("组卷")
	@PostMapping("/create")
	public BaseRsp<PracticePaper> create(@RequestBody @Validated PracticePaperCreateReq practicePaperCreateReq) {
		return new BaseRsp(practicePaperService.createPaper(practicePaperCreateReq));
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
		return new BaseRsp(practicePaperService.getPaperSummaryRes(paperId));
	}

	@ApiOperation("查看练习详情")
	@GetMapping("/practice_detail")
	public BaseRsp<WebTheoryBo> getWebTheoryBo(@ApiParam(value = "练习id") @RequestParam(value = "practice_id") String practiceId) {
		return new BaseRsp<>(paperPracticeTheoryService.getPracticeDetail(practiceId));
	}

	@ApiOperation("提交答案")
	@PostMapping("/submit_answer")
	public BaseRsp<EvaluateResultTheory> submitAnswer(@RequestBody @Validated SubmitAnswerReq answerReq) {
		return new BaseRsp<>(paperPracticeTheoryService.submitAnswer(answerReq.getResourceId(), answerReq.getSubmitAnswer()));
	}

	@ApiOperation("(取消)收藏")
	@PostMapping("/practice_collection")
	public BaseRsp<Boolean> collection(@RequestBody @Validated CollectionReq collectionReq) {
		return new BaseRsp<>(paperPracticeTheoryService.collection(collectionReq));
	}

	@ApiOperation("交卷")
	@GetMapping("/submit_paper")
	public BaseRsp<Boolean> submitPaper(@ApiParam(value = "试卷id") @RequestParam(value = "paper_id") String paperId) {
		PracticePaper practicePaper = practicePaperMapper.selectById(paperId);
		practicePaper.setIsFinish(true);
		practicePaperMapper.updateById(practicePaper);
		return new BaseRsp<>(true);
	}

	@ApiOperation("最近5条组卷记录")
	@GetMapping("/record")
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
	public BaseRsp<List<PaperRecordSummaryResByDay>> getRecordSummary(@ApiParam("是否已结束") @RequestParam(value = "is_finish" , required = false) Boolean isFinish) {
		return new BaseRsp<>(practicePaperService.getAllPaperRecord(isFinish));
	}

	@ApiOperation("查看组卷详情")
	@GetMapping("/record_detail")
	public BaseRsp<PaperRecordDetailRes> getPaperRecordDetail(@ApiParam("试卷id") @RequestParam(value = "paper_id") String paperId) {
		return new BaseRsp<>(practicePaperService.getPaperRecordDetail(paperId));
	}

	@ApiOperation("再练一次")
	@GetMapping("/practice_again")
	public BaseRsp<PracticePaper> practiceAgain(@ApiParam("试卷id") @RequestParam(value = "paper_id") String paperId) {
		return new BaseRsp<>(practicePaperService.practiceAgain(paperId));
	}

}
