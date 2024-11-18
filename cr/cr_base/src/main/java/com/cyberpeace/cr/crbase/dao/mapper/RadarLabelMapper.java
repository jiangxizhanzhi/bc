package com.cyberpeace.cr.crbase.dao.mapper;

import com.cyberpeace.cr.crbase.dao.entity.RadarLabel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 雷达标签映射器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface RadarLabelMapper extends BaseMapper<RadarLabel> {


	/**
	 * 查询列表
	 *
	 * @return {@code List<RadarLabel> }
	 * @throws
	 * @see List<RadarLabel>
	 */
	List<RadarLabel> selectList();
}
