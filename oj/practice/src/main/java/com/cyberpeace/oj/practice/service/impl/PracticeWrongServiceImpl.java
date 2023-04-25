package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.practice.controller.res.PracticeWrongRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.entity.PracticeWrong;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeWrongMapper;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.PracticeWrongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PracticeWrongServiceImpl extends ServiceImpl<PracticeWrongMapper, PracticeWrong> implements PracticeWrongService {

	@Autowired
	private PracticeWrongMapper practiceWrongMapper;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public List<PracticeWrongRes> getAllPracticeWrong() {
		List<PracticeWrong> practiceWrongList = practiceWrongMapper.selectByUserId(null);
		List<String> optionPracticeIds = practiceWrongList.stream()
														  .filter(practiceWrong -> practiceWrong.getPracticeType().equals(PracticeCategory.PracticeType.OPERATION.getCode()))
														  .map(PracticeWrong::getPracticeId)
														  .collect(Collectors.toList());
		List<String> theoryPracticeIds = practiceWrongList.stream()
														  .filter(practiceWrong -> practiceWrong.getPracticeType().equals(PracticeCategory.PracticeType.THEORY.getCode()))
														  .map(PracticeWrong::getPracticeId)
														  .collect(Collectors.toList());
		List<PracticeOperation> practiceOperationList = new ArrayList<>();
		if (optionPracticeIds.size() > 0 ) {
			practiceOperationList = practiceOperationMapper.selectBatchIds(optionPracticeIds);
		}
		List<PracticeTheory> practiceTheoryList = new ArrayList<>();
		if (theoryPracticeIds.size() > 0 ) {
			practiceTheoryList = practiceTheoryMapper.selectBatchIds(theoryPracticeIds);
		}

		Map<String, String> contentMap = new HashMap<>();
		for (PracticeOperation practiceOperation : practiceOperationList) {
			contentMap.put(practiceOperation.getResourceId(), practiceOperation.getContent());
		}
		for (PracticeTheory practiceTheory : practiceTheoryList) {
			contentMap.put(practiceTheory.getResourceId(), practiceTheory.getContent());
		}

		List<String> userIds = practiceWrongList.stream().map(PracticeWrong::getSubmitUserId).distinct().collect(Collectors.toList());
		Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);

		List<PracticeWrongRes> practiceWrongResList = new ArrayList<>();
		for (PracticeWrong practiceWrong : practiceWrongList) {
			PracticeWrongRes practiceWrongRes = BeanUtils.copy(practiceWrong, PracticeWrongRes.class);
			String content = contentMap.get(practiceWrong.getPracticeId());
			if (content == null) {
				continue;
			}
			practiceWrongRes.setContent(content);
			practiceWrongRes.setUserName(idNameMap.get(practiceWrong.getSubmitUserId()));
			practiceWrongRes.setSubmitTime(TimeUtil.format1(practiceWrong.getCreateTime()));
			practiceWrongResList.add(practiceWrongRes);
		}
		return practiceWrongResList;
	}
}
