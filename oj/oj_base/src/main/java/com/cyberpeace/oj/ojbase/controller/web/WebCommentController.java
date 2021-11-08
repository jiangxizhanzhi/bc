package com.cyberpeace.oj.ojbase.controller.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.oj.ojbase.constant.CommentType;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.req.CommentIsLikeReq;
import com.cyberpeace.oj.ojbase.controller.req.CommentReq;
import com.cyberpeace.oj.ojbase.controller.req.PageCommentReq;
import com.cyberpeace.oj.ojbase.controller.res.CommentRes;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.service.CommentService;
import com.cyberpeace.oj.ojbase.service.bo.CommentBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "oj-评论相关api")
@RestController
@RequestMapping("/oj/comment/")
@RequiresAuthentication
public class WebCommentController {

	@Autowired
	private CommentService commentService;

	@ApiOperation("查询所有留言")
	@GetMapping("/list")
	public BaseRsp<ListRsp<CommentBo>> listComment(@ApiParam @Valid PageCommentReq pageCommentReq) {

		Page page = pageCommentReq.toPageParam();
		ListRsp<CommentBo> listRsp = commentService.pageComment(page,
				pageCommentReq.getBusinessId(), pageCommentReq.getBusinessType(), true);

		return new BaseRsp(listRsp);
	}

	@ApiOperation("留言点赞")
	@PostMapping("/is_like")
	public BaseRsp<String> commentIsLike(@ApiParam @RequestBody CommentIsLikeReq commentIsLikeReq) {

		String userId = UserInfoContext.getUserContext().getUserId();
		String resource = commentService.commentIsLike(commentIsLikeReq.getResourceId(),
				commentIsLikeReq.getIsLike(), userId);

		return new BaseRsp<>(resource);
	}

	@PostMapping("")
	@ApiOperation("留言新增或者修改")
	public BaseRsp<CommentRes> getCourseComment(@RequestBody @Valid CommentReq commentReq) {

		Comment comment = BeanUtils.copy(commentReq, Comment.class);
		comment.setUserId(UserInfoContext.getUserContext().getUserId());

		Comment newComment = commentService.addNewComment(comment);
		CommentRes commentRes = BeanUtils.copy(newComment, CommentRes.class);
		return new BaseRsp<>(commentRes);
	}

}
