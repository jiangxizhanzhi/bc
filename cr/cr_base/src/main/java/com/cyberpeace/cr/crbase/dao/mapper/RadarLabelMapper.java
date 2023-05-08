package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.RadarLabel;

/**
 * 雷达标签映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface RadarLabelMapper extends BaseMapper<RadarLabel> {

	/**
	 * 获取列表
	 *
	 * @return {@link List}<{@link RadarLabel}>
	 */
	List<RadarLabel> selectList();
}
