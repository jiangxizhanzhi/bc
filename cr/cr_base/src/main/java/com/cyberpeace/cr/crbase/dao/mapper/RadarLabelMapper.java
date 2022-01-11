package com.cyberpeace.cr.crbase.dao.mapper;

import com.cyberpeace.cr.crbase.dao.entity.RadarLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;

import java.util.List;

@Mapper
@Repository
public interface RadarLabelMapper extends BaseMapper<RadarLabel> {


	List<RadarLabel> selectList();
}
