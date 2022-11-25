package com.cyberpeace.assess.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.assess.dao.entity.AssessData;

/**
 * @Entity com.cyberpeace.assess.src/main/dao.AssessData
 */
@Mapper
@Repository
public interface AssessDataMapper extends BaseMapper<AssessData> {

	IPage<AssessData> listAssessData(Page page, String pattern, Integer type, String tag);
}
