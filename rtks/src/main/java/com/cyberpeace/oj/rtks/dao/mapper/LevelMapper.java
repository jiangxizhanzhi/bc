package com.cyberpeace.oj.rtks.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.rtks.dao.entity.Level;

@Mapper
@Repository
public interface LevelMapper extends BaseMapper<Level> {

	IPage<Level> listFrame(Page<Level> page, String keyword);
}
