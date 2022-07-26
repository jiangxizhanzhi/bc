package com.cyberpeace.integratation.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.gx.TrainResult;
import com.cyberpeace.integratation.model.mp.RemoteUser;

public interface RemoteTaskService {

	String getTaskUsers(String taskId, String token);

	Boolean stopExamTask(String taskId, String examId, Double duration);

	List<JSONObject> getTasks(String userId, String softId, String token);

	String getAdminToken(List<RemoteUser> remoteUsers);

	Boolean stopClassroom(String taskId, String userId, String token);

	Boolean stopClassroomTask(TrainResult trainResult, String token);

}
