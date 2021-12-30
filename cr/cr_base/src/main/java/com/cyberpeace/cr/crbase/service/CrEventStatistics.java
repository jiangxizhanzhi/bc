package com.cyberpeace.cr.crbase.service;

import static com.cyberpeace.cr.crbase.service.impl.CrBaseServiceImpl.register;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cs.dao.entity.LocalScene;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service(value = "CrEventStatistics")
public class CrEventStatistics {

	@PostConstruct
	public void register() {
		if (this.getCode() != null) {
			register.put(this.getCode(), this);
		}
	}

	public LocalScene.BusinessCode getCode() {
		return null;
	}

	public TrainingCount getTrainingCount(String userId) {
		throw new NotImplementedException();
	}

	public List<TrainingData> getRelatedTraining(String userId) {
		throw new NotImplementedException();
	}
}
