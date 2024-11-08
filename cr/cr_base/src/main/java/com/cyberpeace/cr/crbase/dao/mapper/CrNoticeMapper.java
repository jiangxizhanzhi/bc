package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 靶场通知Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface CrNoticeMapper extends BaseMapper<CrNotice> {



}
