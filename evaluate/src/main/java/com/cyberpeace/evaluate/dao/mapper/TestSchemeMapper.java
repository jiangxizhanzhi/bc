package com.cyberpeace.evaluate.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.evaluate.dao.entity.TestScheme;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestSchemeMapper extends BaseMapper<TestScheme> {

	int insert(TestScheme testScheme);

	void update(TestScheme testScheme);

	TestScheme selectBySchemeId(String testSchemeId);

	void deleteBySchemeId(String testSchemeId);

	int countAll();
}
