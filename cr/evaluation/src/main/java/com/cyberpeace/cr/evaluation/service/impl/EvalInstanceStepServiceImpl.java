package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.dao.entity.EvalInstanceStep;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalInstanceStepMapper;
import com.cyberpeace.cr.evaluation.service.EvalInstanceStepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EvalInstanceStepServiceImpl extends ServiceImpl<EvalInstanceStepMapper, EvalInstanceStep> implements EvalInstanceStepService {

	@Autowired
	private RemoteLoadService remoteLoadService;

	@Autowired
	private EvalTestInstanceMapper evalTestInstanceMapper;

	@Autowired
	private EvalInstanceStepMapper evalInstanceStepMapper;

	@Override
	public List<CodeNameRsp> getAllStages(String instanceId) {
		return evalInstanceStepMapper.getAllStages(instanceId);
	}


}
