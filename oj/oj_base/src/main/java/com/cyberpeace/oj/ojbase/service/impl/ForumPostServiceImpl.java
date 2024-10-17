package com.cyberpeace.oj.ojbase.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.dao.entity.ForumPost;
import com.cyberpeace.oj.ojbase.dao.mapper.ForumPostMapper;
import com.cyberpeace.oj.ojbase.service.ForumPostService;
import com.cyberpeace.oj.ojbase.service.bo.post.ForumPostBo;
import com.cyberpeace.oj.ojbase.service.bo.post.PostAddReq;
import com.cyberpeace.oj.ojbase.service.bo.post.PostModifyReq;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ForumPostServiceImpl extends ServiceImpl<ForumPostMapper, ForumPost> implements ForumPostService {

	@Autowired
	private ForumPostMapper forumPostMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public ListRsp<ForumPostBo> pagePost(PageReqBase req, Integer type) {
		Page page = req.toPageParam();
		IPage<ForumPostBo> iPage = forumPostMapper.page(page, type, req.getPattern());
		Map<String, String> userNameMap = remoteUserService
				.getIdNameMap(StreamApiUtils.map(iPage.getRecords(), ForumPostBo::getUserId));
		for (ForumPostBo bo : iPage.getRecords()) {
			bo.setUserName(userNameMap.get(bo.getUserId()));
			bo.setEditable(bo.getUserId().equals(UserInfoContext.getUserContext().getUserId()));
		}
		return PageUtil.getListResult(iPage);
	}

	@Override
	public ForumPostBo getDetail(String resourceId) {
		ForumPost post = this.getOrException(resourceId);
		ForumPostBo bo = BeanUtils.copy(post, ForumPostBo.class);
		UserInfo userInfo = remoteUserService.getById(bo.getUserId());
		bo.setUserName(null == userInfo ? "" : userInfo.getName());
		return bo;

	}

	@Override
	public void createPost(PostAddReq req, Integer type) {
		ForumPost post = this.req2Entity(req);
		post.setType(type);
		post.setUserId(UserInfoContext.getUserContext().getUserId());
		forumPostMapper.insert(post);
	}

	@Override
	public void modify(PostModifyReq req) {
		ForumPost post = getOrException(req.getResourceId());
		if (!post.getUserId().equals(UserInfoContext.getUserContext().getUserId())) {
			throw new BusinessException(ErrorCode.INTERFACE_UNAUTHORIZED);
		}
		post.setTitle(req.getTitle());
		post.setDescription(req.getDescription());
		post.setContent(req.getContent());
		forumPostMapper.updateById(post);
	}

	@Override
	public void deleteByIds(List<String> ids, Integer type) {
		if (UserInfoContext.getUserContext().isSuperuser()) {
			this.removeByIds(ids);
		}
		forumPostMapper.deleteByIdsAndUserId(ids, UserInfoContext.getUserContext().getUserId(),
				type);
	}

	private ForumPost getOrException(String resourceId) {
		ForumPost post = forumPostMapper.selectById(resourceId);
		if (null == post) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return post;
	}

	private ForumPost req2Entity(PostAddReq req) {
		ForumPost post = new ForumPost();
		post.setTitle(req.getTitle());
		post.setDescription(req.getDescription());
		post.setContent(req.getContent());
		return post;
	}
}
