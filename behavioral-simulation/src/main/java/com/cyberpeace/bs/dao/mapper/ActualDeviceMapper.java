package com.cyberpeace.bs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.bs.dao.entity.ActualDevice;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActualDeviceMapper extends BaseMapper<ActualDevice> {
    /**
     * 查询所有
     *
     * @return {@code List<ActualDevice>}
     */
    List<ActualDevice> selectAll();
}
