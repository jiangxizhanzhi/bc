package com.cyberpeace.cs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;

/**
 * 系统用状态映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
public interface SystemUseStatusMapper extends BaseMapper<SystemUseStatus> {

	/**
	 * 选择列表
	 *
	 * @return {@link List}<{@link SystemUseStatus}>
	 */
	List<SystemUseStatus> selectList();

	/**
	 * 删除过期数据
	 */
	void deleteOverdueData();
}
