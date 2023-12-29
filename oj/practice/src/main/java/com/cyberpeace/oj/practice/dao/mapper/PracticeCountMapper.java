package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeCount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PracticeCountMapper extends BaseMapper<PracticeCount> {

	PracticeCount selectByUserId(String userId);

	Integer getAllPracticeCount();

	Integer getAllPeopleCount();
}
