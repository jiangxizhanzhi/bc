package com.cyberpeace.oj.course.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.MyRepository;

@Mapper
@Repository
public interface MyRepositoryMapper extends BaseMapper<MyRepository> {}
