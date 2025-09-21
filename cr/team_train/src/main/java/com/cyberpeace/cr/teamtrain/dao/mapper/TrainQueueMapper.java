package com.cyberpeace.cr.teamtrain.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TrainQueue;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TrainQueueMapper extends BaseMapper<TrainQueue> {
}
