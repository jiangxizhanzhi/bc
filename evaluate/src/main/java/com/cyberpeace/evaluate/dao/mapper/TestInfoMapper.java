package com.cyberpeace.evaluate.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.entity.TestInfo;

@Mapper
public interface TestInfoMapper {

	void insert(TestInfo testInfo);

	void update(TestInfo testInfo);

	TestInfo selectByInfoId(String infoId);

	IPage<TestInfo> pageByUserId(Page page, @Param("userId") String userId);

	Integer countByUserId(String userId);

	void deleteByInfoId(String infoId);
}
