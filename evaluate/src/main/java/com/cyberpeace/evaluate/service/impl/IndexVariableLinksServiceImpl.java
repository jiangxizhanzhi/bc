package com.cyberpeace.evaluate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.evaluate.dao.IndexVariableLinksDao;
import com.cyberpeace.evaluate.dao.entity.IndexVariableLinks;
import com.cyberpeace.evaluate.service.IndexVariableLinksService;

@Service
public class IndexVariableLinksServiceImpl implements IndexVariableLinksService {

    @Autowired
    private IndexVariableLinksDao indexVariableLinksDao;

    @Override
    public void add(IndexVariableLinks indexVariableLinks) {
        indexVariableLinksDao.insert(indexVariableLinks);
    }
}
