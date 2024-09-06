package com.cyberpeace.oj.ojbase.controller.web;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cs.service.context.IdListReq;
import com.cyberpeace.oj.ojbase.dao.entity.ForumPost;
import com.cyberpeace.oj.ojbase.service.ForumPostService;
import com.cyberpeace.oj.ojbase.service.bo.post.ForumPostBo;
import com.cyberpeace.oj.ojbase.service.bo.post.PostAddReq;
import com.cyberpeace.oj.ojbase.service.bo.post.PostModifyReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "oj-公共论坛api")
@RestController
@RequestMapping("/oj/forum/")
@RequiresAuthentication
public class ForumController {

	@Autowired
	private ForumPostService forumPostService;

	@ApiOperation("分享列表")
	@GetMapping("/share/page")
	public BaseRsp<ListRsp<ForumPostBo>> pageShare(PageReqBase req) {
		ListRsp<ForumPostBo> res = forumPostService.pagePost(req, ForumPost.PostType.Share);
		return new BaseRsp<>(res);
	}

	@ApiOperation("分享详情")
	@GetMapping("/share")
	public BaseRsp<ForumPostBo> getShareDetail(@ApiParam(name = "resource_id") String resource_id) {
		ForumPostBo res = forumPostService.getDetail(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增分享")
	@PostMapping("/share")
	public BaseRsp addShare(@RequestBody PostAddReq req) {
		forumPostService.createPost(req, ForumPost.PostType.Share);
		return new BaseRsp();
	}

	@ApiOperation("编辑分享")
	@PatchMapping("/share")
	public BaseRsp updateShare(@RequestBody PostModifyReq req) {
		forumPostService.modify(req);
		return new BaseRsp();
	}

	@ApiOperation("删除分享")
	@DeleteMapping("/share")
	public BaseRsp deleteShare(@RequestBody IdListReq req) {
		forumPostService.deleteByIds(req.getIds(), ForumPost.PostType.Share);
		return new BaseRsp<>();
	}

	@ApiOperation("通知列表")
	@GetMapping("/notice/page")
	public BaseRsp<ListRsp<ForumPostBo>> pageNotification(PageReqBase req) {
		ListRsp<ForumPostBo> res = forumPostService.pagePost(req, ForumPost.PostType.Notification);
		return new BaseRsp<>(res);
	}

	@ApiOperation("通知详情")
	@GetMapping("/notice")
	public BaseRsp<ForumPostBo>
			getNotificationDetail(@ApiParam(name = "resource_id") String resource_id) {
		ForumPostBo res = forumPostService.getDetail(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增通知")
	@PostMapping("/notice")
	public BaseRsp addNotification(@RequestBody PostAddReq req) {
		forumPostService.createPost(req, ForumPost.PostType.Notification);
		return new BaseRsp();
	}

	@ApiOperation("编辑通知")
	@PatchMapping("/notice")
	public BaseRsp updateNotification(@RequestBody PostModifyReq req) {
		forumPostService.modify(req);
		return new BaseRsp();
	}

	@ApiOperation("删除通知")
	@DeleteMapping("/notice")
	public BaseRsp deleteNotification(@RequestBody IdListReq req) {
		forumPostService.deleteByIds(req.getIds(), ForumPost.PostType.Notification);
		return new BaseRsp();
	}
}
