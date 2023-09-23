package com.cyberpeace.cr.evaluation.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时一周清理一次不再使用的备份算法
 */
@Slf4j
@Component
public class AlgorithmJob {

	@Autowired
	private EvalAlgorithmService evalAlgorithmService;

	@Autowired
	private EvalAlgorithmMapper evalAlgorithmMapper;

	@Scheduled(fixedRate = 7 * 24 * 60 * 60 * 1000)
	public void deleteAllUnUsedBackup() {

		log.info("delete all unused backup algorithms!");

		List<EvalAlgorithm> evalAlgorithms = evalAlgorithmMapper.getAll();

		List<String> allAlgorithmIds = StreamApiUtils.map(evalAlgorithms,
				EvalAlgorithm::getResourceId);
		// "evalAlgorithms"为空, 传入"selectUsedAlgorithmIds"报错
		if (evalAlgorithms.isEmpty()) {
			return;
		}
		List<String> usedAlgorithmIds = evalAlgorithmService.selectUsedAlgorithmIds(
				StreamApiUtils.map(evalAlgorithms, EvalAlgorithm::getResourceId));

		List<String> unUsedAlgorithmIds = Lists.newArrayList();

		HashSet<String> h1 = new HashSet<>(allAlgorithmIds);
		HashSet<String> h2 = new HashSet<>(usedAlgorithmIds);
		h1.removeAll(h2);

		unUsedAlgorithmIds.addAll(h1);
		evalAlgorithmMapper.deleteAllUnUsedBackup(unUsedAlgorithmIds);

	}

}
