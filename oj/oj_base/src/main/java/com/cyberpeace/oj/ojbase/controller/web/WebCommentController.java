package com.cyberpeace.oj.ojbase.controller.web;

import com.cyberpeace.common.model.DeleteReq;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.ojbase.controller.req.CommentIsLikeReq;
import com.cyberpeace.oj.ojbase.controller.req.CommentReq;
import com.cyberpeace.oj.ojbase.controller.req.PageCommentReq;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.service.CommentService;
import com.cyberpeace.oj.ojbase.service.bo.CommentBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "oj-前台评论相关api")
@RestController
@RequestMapping("/oj/comment/")
@RequiresAuthentication
public class WebCommentController {

	@Autowired
	private CommentService commentService;

	@ApiOperation("查询所有留言")
	@GetMapping("/list")
	@RequiresPermissions(value = { MenuConstant.WEB_OJ_PRACTICE_OPERATION , MenuConstant.WEB_OJ_ALL_COURSE }, logical = Logical.OR)
	public BaseRsp<ListRsp<CommentBo>>
			listComment(@ApiParam @Validated PageCommentReq pageCommentReq) {

		Page page = pageCommentReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.CREATE_TIME, false);
		}
		ListRsp<CommentBo> listRsp = commentService.pageComment(page,
				pageCommentReq.getBusinessId(), pageCommentReq.getBusinessType(), true);

		return new BaseRsp(listRsp);
	}

	@ApiOperation("留言点赞")
	@PostMapping("/is_like")
	@RequiresPermissions(value = { MenuConstant.WEB_OJ_PRACTICE_OPERATION , MenuConstant.WEB_OJ_ALL_COURSE }, logical = Logical.OR)
	public BaseRsp<Integer>
			commentIsLike(@ApiParam @RequestBody CommentIsLikeReq commentIsLikeReq) {

		String userId = UserInfoContext.getUserContext().getUserId();

		Integer result = commentService.commentIsLike(commentIsLikeReq.getResourceId(),
				commentIsLikeReq.getIsLike(), userId);

		return new BaseRsp<>(result);
	}

	@PostMapping("")
	@ApiOperation("留言新增")
	@RequiresPermissions(
			value = { MenuConstant.WEB_OJ_PRACTICE_OPERATION , MenuConstant.WEB_OJ_ALL_COURSE },
			logical = Logical.OR)
	public BaseRsp<Integer> getCourseComment(@RequestBody @Validated CommentReq commentReq) {

		Comment comment = BeanUtils.copy(commentReq, Comment.class);
		comment.setUserId(UserInfoContext.getUserContext().getUserId());

		Integer result = commentService.addNewComment(comment);
		return new BaseRsp<>(result);
	}

	@ApiOperation("留言删除")
	@DeleteMapping("")
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {

		commentService.bathDeleteComment(deleteReq.getIds());

		return new BaseRsp<>();
	}

}
