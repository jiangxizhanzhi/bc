package com.cyberpeace.assess.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.assess.dao.entity.AssessFileData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.cyberpeace.assess.AssessFileData
 */
@Mapper
@Repository
public interface AssessFileDataMapper extends BaseMapper<AssessFileData> {

	String getDataBaseTableByRelatedId(String relatedId);
}
