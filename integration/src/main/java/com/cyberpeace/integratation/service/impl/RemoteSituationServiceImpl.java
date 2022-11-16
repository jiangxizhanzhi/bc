package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.integratation.model.situation.SituationPlan;
import com.cyberpeace.integratation.model.situation.SituationSubject;
import com.cyberpeace.integratation.model.situation.SituationTask;
import com.cyberpeace.integratation.service.RemoteSituationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RemoteSituationServiceImpl implements RemoteSituationService {

    @Override
    public List<SituationSubject> getSubjectByTask(String taskId) {
        return new ArrayList<>();
    }

    @Override
    public List<SituationTask> getTasks(String type, String resourceId) {return new ArrayList<>();}

    @Override
    public List<SituationPlan> getPlans() {return new ArrayList<>();};
}

