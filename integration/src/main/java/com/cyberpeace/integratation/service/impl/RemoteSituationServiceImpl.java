package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.situation.SituationPlan;
import com.cyberpeace.integratation.model.situation.SituationSubject;
import com.cyberpeace.integratation.model.situation.SituationTask;
import com.cyberpeace.integratation.service.RemoteSituationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class RemoteSituationServiceImpl implements RemoteSituationService {


    private String TASK_URL;

    private String PLAN_URL;

    private String SUBJECT_URL;

    @PostConstruct
    public void init() {
        String outerHost = "http://" + ConfigUtil.getContextProperty("outer.host");
        TASK_URL = outerHost + ConfigUtil.getContextProperty("task.list");
        PLAN_URL = outerHost + ConfigUtil.getContextProperty("plan.list");
        SUBJECT_URL = outerHost + ConfigUtil.getContextProperty("subject.list");
    }
    @Override
    public List<SituationSubject> getSubjectByTasks(List<String> taskIds) {
        JSONArray tasks = HttpClientUtil.rawGet(SUBJECT_URL, JSONArray.class);
        List<SituationSubject> list = JSONArray.parseArray(tasks.toJSONString(), SituationSubject.class);
        return list;
    }

    private  List<SituationSubject> getSubjectById(String taskId) {
        JSONObject tasks = HttpClientUtil.rawGet(SUBJECT_URL + taskId, JSONObject.class);
        List<SituationSubject> list = JSONArray.parseArray(JSONObject.toJSONString(tasks.getJSONArray("list")), SituationSubject.class);
        return list;
    }



    @Override
    public  List<SituationSubject> getSubjectByTask(String taskId) {
        JSONObject tasks = HttpClientUtil.rawGet(SUBJECT_URL + taskId, JSONObject.class);
        List<SituationSubject> list = JSONArray.parseArray(JSONObject.toJSONString(tasks.getJSONArray("list")), SituationSubject.class);
        return list;
    }


    @Override
    public List<SituationTask> getTasks(String type, String resourceId) {
        JSONObject tasks = HttpClientUtil.rawGet(TASK_URL + type, JSONObject.class);
        List<SituationTask> list = JSONArray.parseArray(JSONObject.toJSONString(tasks.getJSONArray("list")), SituationTask.class);
        return list;}

    @Override
    public List<SituationPlan> getPlans() {
        return new ArrayList<>();}
}

