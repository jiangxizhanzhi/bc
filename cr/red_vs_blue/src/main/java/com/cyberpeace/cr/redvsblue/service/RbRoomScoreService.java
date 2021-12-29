package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.controller.XYData;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.TimeData;
import com.cyberpeace.cr.redvsblue.service.context.mission.RoleScore;

public interface RbRoomScoreService extends IService<RbRoom> {

	List<RoleScore> getRoleScoreInfo(String roomId);

	TimeData getRbTimeData(String roomId);

	XYData getRbMissionCategory(String roomId, Integer roleType);

	JSONObject getRbMembersRanking(String roomId, Integer roleType);

	JSONObject getRbMissionEvaluation(String roomId, Integer roleType);

	JSONObject getRbLeaderBoard(String roomId, Integer roleType);

	JSONObject getRbMissionProgress(String roomId, Integer roleType);
}
