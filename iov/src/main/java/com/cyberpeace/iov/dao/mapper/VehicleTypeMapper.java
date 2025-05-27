package com.cyberpeace.iov.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.iov.dao.entity.VehicleType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VehicleTypeMapper extends BaseMapper<VehicleType> {}
