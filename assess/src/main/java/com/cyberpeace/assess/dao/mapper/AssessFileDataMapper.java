package com.cyberpeace.assess.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.assess.dao.entity.AssessFileData;

/**
 * @Entity com.cyberpeace.assess.AssessFileData
 */
@Mapper
@Repository
public interface AssessFileDataMapper extends BaseMapper<AssessFileData> {

	String getDataBaseTableByRelatedId(String relatedId);

	void deleteByRelatedId(String relatedId);
}
