package com.cyberpeace.cr.crbase.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.Wiki;
import com.cyberpeace.cr.crbase.service.context.wiki.WikiListRsp;

/**
 * 百科mapper
 *
 * @author SN
 * @date 2023/05/15
 */
@Repository
@Mapper
public interface WikiMapper extends BaseMapper<Wiki> {

	IPage<WikiListRsp> page(Page page, String search);
}
