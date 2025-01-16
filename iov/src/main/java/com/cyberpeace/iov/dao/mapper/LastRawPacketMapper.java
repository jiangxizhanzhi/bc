package com.cyberpeace.iov.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.iov.dao.entity.LastRawPacket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LastRawPacketMapper extends BaseMapper<LastRawPacket> {

    /**
     * 更新报文
     *
     * @param type 类型
     * @param raw  原始报文
     * @throws
     * @see Integer
     * @see String
     */
    void updatePacket(Integer type, String raw);
}
