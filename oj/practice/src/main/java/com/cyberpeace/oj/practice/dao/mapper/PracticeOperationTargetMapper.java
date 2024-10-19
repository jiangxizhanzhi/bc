package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationTarget;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticeOperationTargetMapper extends BaseMapper<PracticeOperationTarget> {

	List<PracticeOperationTarget> getAllByOperationIds(@Param("operationIds") List<String> operationIds);

}
