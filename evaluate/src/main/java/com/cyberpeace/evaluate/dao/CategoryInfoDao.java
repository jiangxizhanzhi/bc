package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.CategoryInfo;

public interface CategoryInfoDao {

	List<CategoryInfo> selectByType(Integer type);
}
