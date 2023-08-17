package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeListRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CrNoticeMapper extends BaseMapper<CrNotice> {
    IPage<CrNoticeListRsp> pageAdmin(@Param("room_id") String roomId, Page page);

    List<CrNotice> page(@Param("room_id") String roomId, @Param("group") String group);

    List<CrNotice> getByRoom(@Param("room_id") String roomId, @Param("group") String group);
}
