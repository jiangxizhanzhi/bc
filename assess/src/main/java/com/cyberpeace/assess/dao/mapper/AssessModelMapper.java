package com.cyberpeace.assess.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.assess.dao.entity.AssessModel;

/**
 * @Entity com.cyberpeace.assess.src/main/dao.AssessModel
 */
@Mapper
@Repository
public interface AssessModelMapper extends BaseMapper<AssessModel> {

	IPage<AssessModel> listAssessModel(Page page, String pattern);
}
