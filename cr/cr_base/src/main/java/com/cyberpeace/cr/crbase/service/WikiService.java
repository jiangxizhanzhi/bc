package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.service.context.wiki.WikiCreateReq;
import com.cyberpeace.cr.crbase.service.context.wiki.WikiListRsp;
import com.cyberpeace.cr.crbase.service.context.wiki.WikiModifyReq;
import com.cyberpeace.cr.crbase.service.context.wiki.WikiRsp;

/**
 * 百科Service
 *
 * @author SN
 * @date 2023/05/15
 */
public interface WikiService {

	void create(WikiCreateReq req);

	void deleteByIds(List<String> resourceIds);

	void update(WikiModifyReq req);

	WikiRsp getById(String resource_id);

	ListRsp<WikiListRsp> page(Page page, String search);
}
