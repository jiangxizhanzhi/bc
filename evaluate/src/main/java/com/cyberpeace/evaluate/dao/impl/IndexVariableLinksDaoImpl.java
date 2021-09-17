package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.IndexVariableLinksDao;
import com.cyberpeace.evaluate.dao.entity.IndexVariableLinks;
import com.cyberpeace.evaluate.dao.mapper.IndexVariableLinksMapper;

@Repository
public class IndexVariableLinksDaoImpl implements IndexVariableLinksDao {

	@Autowired
	private IndexVariableLinksMapper indexVariableLinksMapper;

	@Override
	public void insert(IndexVariableLinks indexVariableLinks) {
		indexVariableLinksMapper.insert(indexVariableLinks);
	}

	@Override
	public List<IndexVariableLinks> selectBySchemeAndIndex(String schemeId, String estimateIndexId) {
		List<IndexVariableLinks> indexVariableLinks = indexVariableLinksMapper
				.selectBySchemeAndIndex(schemeId, estimateIndexId);

		return indexVariableLinks;
	}

	@Override
	public void batchAdd(List<IndexVariableLinks> indexVariableLinks) {
		indexVariableLinksMapper.batchAdd(indexVariableLinks);
	}

	@Override
	public void batchDelete(List<String> indexVariableIds) {
		indexVariableLinksMapper.batchDelete(indexVariableIds);
	}

	@Override
	public void deleteByScheme(String schemeId) {
		indexVariableLinksMapper.deleteByScheme(schemeId);
	}
}
