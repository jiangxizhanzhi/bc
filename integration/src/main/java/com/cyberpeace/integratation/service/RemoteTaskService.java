package com.cyberpeace.integratation.service;

public interface RemoteTaskService {

	String getTaskUsers(String taskId, String token);

	Boolean stopExamTask(String taskId, String examId, Double duration);

}
