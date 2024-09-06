package com.cyberpeace.oj.ojbase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.ojbase.dao.entity.ForumPost;
import com.cyberpeace.oj.ojbase.service.bo.post.ForumPostBo;
import com.cyberpeace.oj.ojbase.service.bo.post.PostAddReq;
import com.cyberpeace.oj.ojbase.service.bo.post.PostModifyReq;

public interface ForumPostService extends IService<ForumPost> {

	ListRsp<ForumPostBo> pagePost(PageReqBase req, Integer type);

	ForumPostBo getDetail(String resourceId);

	void createPost(PostAddReq req, Integer type);

	void modify(PostModifyReq req);

	void deleteByIds(List<String> ids, Integer type);
}
