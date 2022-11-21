package com.cyberpeace.assess.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.assess.dao.entity.AssessCase;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author dell
* @description 针对表【assess_case】的数据库操作Mapper
* @createDate 2022-11-21 13:52:10
* @Entity com.cyberpeace.assess.dao.entity.AssessCase
*/

@Mapper
@Repository
public interface AssessCaseMapper extends BaseMapper<AssessCase> {


}
