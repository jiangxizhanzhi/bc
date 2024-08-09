package com.cyberpeace.iov.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoomList;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleRoomMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.integratation.model.scene.ResourceStatisticsRsp;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.iov.constant.IovTaskStatus;
import com.cyberpeace.iov.dao.mapper.DutMapper;
import com.cyberpeace.iov.service.IovIndexService;
import com.cyberpeace.iov.service.bo.index.ResourceCountBo;
import com.cyberpeace.iov.service.bo.index.RightDemo;
import com.cyberpeace.iov.service.bo.index.TaskBo;
import com.cyberpeace.oj.course.service.CourseService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IovIndexServiceImpl implements IovIndexService {

	@Autowired
	private DutMapper dutMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private ToolService toolService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private TeamRoomMapper teamRoomMapper;

	@Autowired
	private SingleRoomMapper singleRoomMapper;

	@Override
	public RightDemo getDemo(HttpServletRequest request) {
		RightDemo res = new RightDemo();
		String demoFilePath = "/WEB-INF/classes/static/demonstration/iov_index.json";
		StringBuilder builder = new StringBuilder();
		try (InputStream stream = request.getServletContext().getResourceAsStream(demoFilePath);
				BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			String content = "";
			while ((content = br.readLine()) != null) {
				builder.append(content).append(System.lineSeparator());
			}
			String demoJson = builder.toString();
			res = JsonUtil.toJsonObject(demoJson, RightDemo.class);
		} catch (Exception e) {
			log.error("index demo file doesn't exist");
		}
		return res;
	}

	@Override
	public ResourceCountBo getResourceCount() {
		ResourceCountBo res = new ResourceCountBo();
		Integer dutCount = dutMapper.selectCount(new LambdaQueryWrapper<>());
		ResourceStatisticsRsp remoteRsp = remoteSceneService.getResourceStatistics();

		int toolCount = toolService.count();

		int courseCount = courseService.count();

		res.setEntity(dutCount);
		res.setScene(remoteRsp.getScene());
		res.setVirtual(remoteRsp.getVm());
		res.setTool(toolCount);
		res.setCourse(courseCount);

		return res;
	}

	@Override
	public List<TaskBo> getTaskList() {
		List<TaskBo> res = Lists.newArrayList();
		TeamRoom teamRoom = teamRoomMapper.selectOnlyOne();
		if (null != teamRoom) {
			TaskBo team = new TaskBo(teamRoom.getName(), teamRoom.getTrainingStatus());
			res.add(team);
		}

		IPage<SingleRoomList> singlePage = singleRoomMapper.singleTrainSearch(
				new PageReqBase().toPageParam(), UserInfoContext.getUserContext().getUserId(), null,
				null, null, BusinessType.TRAINING_SINGLE.getCode());
		for (SingleRoomList singleRoom : singlePage.getRecords()) {
			TaskBo single = new TaskBo(singleRoom.getBusinessTemplate().getName(),
					singleRoom.getSingleRoom() == null ? IovTaskStatus.NOT_STARTED
							: IovTaskStatus.RUNNING);
			res.add(single);
		}

		return res;

	}

}
