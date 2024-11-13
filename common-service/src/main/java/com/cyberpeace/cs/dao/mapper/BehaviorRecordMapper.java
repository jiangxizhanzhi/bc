package com.cyberpeace.cs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.BehaviorRecordEntity;

/**
 * 行为记录Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
@Repository
public interface BehaviorRecordMapper extends BaseMapper<BehaviorRecordEntity> {

	/**
	 * 查询全部
	 *
	 * @return {@code List<BehaviorRecordEntity> }
	 */
	List<BehaviorRecordEntity> selectAll();

	/**
	 * 查询由模块和行动
	 *
	 * @param module 模块
	 * @param action 行动
	 * @return {@code List<BehaviorRecordEntity> }
	 */
	List<BehaviorRecordEntity> selectByModuleAndAction(@Param("module") Integer module,
			@Param("action") Integer action);
}
