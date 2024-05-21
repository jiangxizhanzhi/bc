package com.cyberpeace.cs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.BehaviorRecordEntity;

@Mapper
@Repository
public interface BehaviorRecordMapper extends BaseMapper<BehaviorRecordEntity> {

	List<BehaviorRecordEntity> selectAll();

	List<BehaviorRecordEntity> selectByModuleAndAction(@Param("module") Integer module,
			@Param("action") Integer action);
}
