package com.cyberpeace.iov.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.iov.dao.entity.VehicleTraffic;
import com.cyberpeace.iov.service.bo.frame.VehicleFrameListBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VehicleTrafficMapper extends BaseMapper<VehicleTraffic> {
    IPage<VehicleFrameListBo> page(@Param("page") Page page, @Param("pattern") String pattern);
}
