package com.cyberpeace.assess.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.assess.dao.entity.AssessCaseConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author dell
* @description 针对表【assess_case_config】的数据库操作Mapper
* @createDate 2022-11-21 13:55:57
* @Entity com.cyberpeace.assess.dao.entity.AssessCaseConfig
*/
@Mapper
@Repository
public interface AssessCaseConfigMapper extends BaseMapper<AssessCaseConfig> {


}
