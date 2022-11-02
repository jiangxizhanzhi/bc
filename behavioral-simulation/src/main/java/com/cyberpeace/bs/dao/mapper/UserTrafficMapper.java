package com.cyberpeace.bs.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.bs.dao.entity.UserTraffic;

import java.util.List;

@Mapper
@Repository
public interface UserTrafficMapper extends BaseMapper<UserTraffic> {

    List<UserTraffic> selectAll();
}
