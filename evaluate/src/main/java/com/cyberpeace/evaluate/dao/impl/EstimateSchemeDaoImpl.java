package com.cyberpeace.evaluate.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.EstimateSchemeDao;
import com.cyberpeace.evaluate.dao.entity.EstimateScheme;
import com.cyberpeace.evaluate.dao.mapper.EstimateSchemeMapper;

@Repository
public class EstimateSchemeDaoImpl implements EstimateSchemeDao {

	@Autowired
	private EstimateSchemeMapper estimateSchemeMapper;

	@Override
	public IPage<EstimateScheme> selectList(Page pageBase) {
		return estimateSchemeMapper.selectList(pageBase);
	}

	@Override
	public EstimateScheme selectById(String schemeId) {
		return estimateSchemeMapper.selectById(schemeId);
	}

	@Override
	public void insert(EstimateScheme estimateScheme) {
		estimateSchemeMapper.insert(estimateScheme);
	}

	@Override
	public void delete(String schemeId) {
		estimateSchemeMapper.delete(schemeId);
	}
}
