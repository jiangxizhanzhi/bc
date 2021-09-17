package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.evaluate.dao.EstimateIndexLinksDao;
import com.cyberpeace.evaluate.dao.entity.EstimateIndexLinks;
import com.cyberpeace.evaluate.dao.mapper.EstimateIndexLinksMapper;

@Service
public class EstimateIndexLinksDaoImpl implements EstimateIndexLinksDao {

	@Autowired
	private EstimateIndexLinksMapper estimateIndexLinksMapper;

	@Override
	public void insert(EstimateIndexLinks estimateIndexLinks) {
		estimateIndexLinksMapper.insert(estimateIndexLinks);
	}

	@Override
	public List<EstimateIndexLinks> selectBySchemeAndItem(String schemeId, String estimateItemsId) {
		List<EstimateIndexLinks> estimateIndexLinks = estimateIndexLinksMapper
				.selectBySchemeAndItem(schemeId, estimateItemsId);
		return estimateIndexLinks;
	}

	@Override
	public void batchAdd(List<EstimateIndexLinks> indexLinks) {
		estimateIndexLinksMapper.batchAdd(indexLinks);
	}

	@Override
	public void batchDelete(List<String> estimateIndexIds) {
		estimateIndexLinksMapper.batchDelete(estimateIndexIds);
	}

	@Override
	public void deleteByScheme(String schemeId) {
		estimateIndexLinksMapper.deleteByScheme(schemeId);
	}
}
