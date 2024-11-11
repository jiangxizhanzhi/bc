package com.cyberpeace.oj.ojbase.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.ojbase.controller.req.CommentReq;
import com.cyberpeace.oj.ojbase.controller.req.PageChildrenCommentReq;
import com.cyberpeace.oj.ojbase.controller.req.PageCommentReq;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.service.CommentService;
import com.cyberpeace.oj.ojbase.service.bo.CommentBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 管理评论Controller
 *
 * @author SN
 * @date 2024/11/11
 */
@Api(tags = "oj-后台留言相关api")
@RestController
@RequestMapping("/oj/admin/comment")
@RequiresAuthentication
public class AdminCommentController {

	/**
	 * 评论Service
	 */
	@Autowired
	private CommentService commentService;

	/**
	 * 新增评论
	 *
	 * @param commentReq 评论请求体
	 * @return {@code BaseRsp<Integer> }
	 */
	@ApiOperation("留言新增")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_COMMENT_MANAGE)
	public BaseRsp<Integer> addComment(@RequestBody @Validated CommentReq commentReq) {

		Comment comment = BeanUtils.copy(commentReq, Comment.class);
		comment.setUserId(UserInfoContext.getUserContext().getUserId());
		comment.setContent(comment.getContent().trim());

		Integer result = commentService.adminAddNewComment(comment);
		return new BaseRsp<>(result);
	}

	/**
	 * 批量删除
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("留言删除")
	@DeleteMapping("")
	@RequiresPermissions(MenuConstant.CMS_COMMENT_MANAGE)
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {

		commentService.bathDeleteComment(deleteReq.getIds());

		return new BaseRsp<>();
	}

	/**
	 * 父列表
	 *
	 * @param pageCommentReq 分页评论请求体
	 * @return {@code BaseRsp<ListRsp<CommentBo>> }
	 */
	@ApiOperation("分页查询一级留言")
	@GetMapping("list")
	@RequiresPermissions(MenuConstant.CMS_COMMENT_MANAGE)
	public BaseRsp<ListRsp<CommentBo>>
			parentList(@ApiParam @Validated PageCommentReq pageCommentReq) {
		Page page = pageCommentReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.CREATE_TIME, false);
		}
		ListRsp<CommentBo> listRsp = commentService.pageParentComment(page,
				pageCommentReq.getBusinessType(), pageCommentReq.getPattern());
		return new BaseRsp(listRsp);
	}

	/**
	 * 子级列表
	 *
	 * @param pageChildrenCommentReq 分页子级评论请求体
	 * @return {@code BaseRsp<ListRsp<CommentBo>> }
	 */
	@ApiOperation("分页查询二级留言")
	@GetMapping("children_list")
	@RequiresPermissions(MenuConstant.CMS_COMMENT_MANAGE)
	public BaseRsp<ListRsp<CommentBo>>
			childrenList(@ApiParam @Validated PageChildrenCommentReq pageChildrenCommentReq) {
		Page page = pageChildrenCommentReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.CREATE_TIME, false);
		}
		ListRsp<CommentBo> listRsp = commentService.pageChildrenComment(page,
				pageChildrenCommentReq.getResourceId());
		return new BaseRsp(listRsp);
	}

}
