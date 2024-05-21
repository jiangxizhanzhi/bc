package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.BehaviorRecordEntity;
import com.cyberpeace.cs.service.context.behavior.BehaviorAnalyseResult;

public interface BehaviorRecordService extends IService<BehaviorRecordEntity> {

	BehaviorAnalyseResult getAnalyseResult();
}
