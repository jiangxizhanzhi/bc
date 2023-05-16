package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.dao.Wiki;
import com.cyberpeace.cr.crbase.dao.mapper.WikiMapper;
import com.cyberpeace.cr.crbase.service.WikiService;
import com.cyberpeace.cr.crbase.service.context.wiki.WikiCreateReq;
import com.cyberpeace.cr.crbase.service.context.wiki.WikiListRsp;
import com.cyberpeace.cr.crbase.service.context.wiki.WikiModifyReq;
import com.cyberpeace.cr.crbase.service.context.wiki.WikiRsp;
import com.cyberpeace.integratation.service.RemoteUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 百科Service实现
 *
 * @author SN
 * @date 2023/05/15
 */
@Service
@Slf4j
public class WikiServiceImpl implements WikiService {

	@Autowired
	private WikiMapper wikiMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public void create(WikiCreateReq req) {
		Wiki wiki = new Wiki();
		wiki.setTitle(req.getTitle());
		wiki.setContent(req.getContent());
		wiki.setDescription(req.getDescription());
		wiki.setLogo(req.getLogo());
		wiki.setCreator(UserInfoContext.getUserContext().getUserId());
		wikiMapper.insert(wiki);
	}

	@Override
	public void deleteByIds(List<String> resourceIds) {
		wikiMapper.deleteBatchIds(resourceIds);
	}

	@Override
	public void update(WikiModifyReq req) {
		if (StringUtils.isBlank(req.getResourceId())) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		Wiki wiki = BeanUtils.copy(req, Wiki.class);
		wikiMapper.updateById(wiki);
	}

	@Override
	public WikiRsp getById(String resource_id) {
		Wiki wiki = wikiMapper.selectById(resource_id);
		if (null == wiki) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		WikiRsp wikiRsp = BeanUtils.copy(wiki, WikiRsp.class);
		String creatorName = remoteUserService.getById(wiki.getCreator()).getName();
		wikiRsp.setCreatorName(creatorName);
		return wikiRsp;
	}

	@Override
	public ListRsp<WikiListRsp> page(Page page, String search) {
		IPage<WikiListRsp> iPage = wikiMapper.page(page, search);
		return PageUtil.getListResult(iPage);
	}
}
