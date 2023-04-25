package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeWrong;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PracticeWrongMapper extends BaseMapper<PracticeWrong> {

	List<PracticeWrong> selectByUserId(String userId);
}
