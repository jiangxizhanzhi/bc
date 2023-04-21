package com.cyberpeace.situation.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.integratation.constant.TaskStatus;
import com.cyberpeace.integratation.constant.TaskType;
import com.cyberpeace.integratation.model.situation.SubjectContext;
import com.cyberpeace.integratation.model.situation.TaskContext;
import com.cyberpeace.integratation.model.situation.req.SubjectListReq;
import com.cyberpeace.integratation.model.situation.req.TaskListReq;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.integratation.service.bo.situation.SchemeRspBo;
import com.cyberpeace.integratation.service.bo.situation.SchemeVisualizationBo;
import com.cyberpeace.integratation.service.bo.situation.SituationTaskReq;
import com.cyberpeace.integratation.service.bo.situation.TopoHistoryBo;
import com.cyberpeace.situation.controller.req.TaskListPageReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "外部数据-任务信息")
@RestController
@RequiresAuthentication
@RequestMapping("/third/")
public class ExternalDataTaskController {

	@Autowired
	private RemoteSituationService remoteSituationService;

	@ApiOperation("获取任务类型字典")
	@GetMapping("/task_type/list/")
	public BaseRsp<List<HashMap<String, String>>>
			getTaskType(@ApiParam(name = "all", value = "是否返回全部类型",
					required = false) @RequestParam(value = "all", required = false) Boolean all) {

		if (all == null || !all) {
			return new BaseRsp<>(TaskType.getTaskTypeMap(true));
		}

		return new BaseRsp<>(TaskType.getTaskTypeMap(false));
	}

	@ApiOperation("获取任务状态字典")
	@GetMapping("/task_status/list/")
	public BaseRsp<List<HashMap<String, String>>> getTaskStatus() {

		return new BaseRsp<>(TaskStatus.getTaskStatusMap());
	}

	@ApiOperation("态势-获取任务列表")
	@GetMapping("/task/list/")
	public BaseRsp<ListRsp<TaskContext>> getTaskList(TaskListPageReq taskListPageReq) {
		return new BaseRsp<>(remoteSituationService
				.getTaskList(BeanUtils.copy(taskListPageReq, TaskListReq.class)));
	}

	@ApiOperation("态势-获取科目列表")
	@GetMapping("/subject/list/")
	public BaseRsp<List<SubjectContext>> getSubjectList(SubjectListReq subjectListReq) {
		return new BaseRsp<>(remoteSituationService.getSubjectList(subjectListReq));
	}

	@ApiOperation("想定-获取科目可视化方案列表")
	@GetMapping("/scheme/list")
	public BaseRsp<List<SchemeRspBo>> getSchemeList(SituationTaskReq req) {
		List<SchemeRspBo> res = remoteSituationService.getSchemeList(req);
		return new BaseRsp<>(res);
	}

	@ApiOperation("想定-获取科目可视化方案")
	@GetMapping("/scheme/visualization")
	public BaseRsp<SchemeVisualizationBo> getSchemeVisualization(
			@ApiParam(value = "可视化方案id", name = "scheme_id") String scheme_id) {
		SchemeVisualizationBo scheme = remoteSituationService.getScheme(scheme_id);
		return new BaseRsp<>(scheme);
	}

	@ApiOperation("配管-获取科目拓扑变更记录")
	@GetMapping("/task/topo/history")
	public BaseRsp<List<TopoHistoryBo>> getTopoHistory(SituationTaskReq req) {
		List<TopoHistoryBo> res = remoteSituationService.getTopoHistory(req);
		return new BaseRsp<>(res);
	}

}
