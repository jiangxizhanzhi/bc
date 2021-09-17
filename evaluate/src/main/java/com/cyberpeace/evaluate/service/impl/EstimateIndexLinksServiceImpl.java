package com.cyberpeace.evaluate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.evaluate.dao.EstimateIndexLinksDao;
import com.cyberpeace.evaluate.dao.entity.EstimateIndexLinks;
import com.cyberpeace.evaluate.service.EstimateIndexLinksService;

@Service
public class EstimateIndexLinksServiceImpl implements EstimateIndexLinksService {

	@Autowired
	private EstimateIndexLinksDao estimateIndexLinksDao;

	@Override
	public void add(EstimateIndexLinks estimateIndexLinks) {
		estimateIndexLinksDao.insert(estimateIndexLinks);
	}
}
