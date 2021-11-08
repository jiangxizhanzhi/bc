package com.cyberpeace.oj.ojbase.controller.admin;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.ojbase.controller.req.AdminListNoteReq;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.bo.NoteListBo;
import com.cyberpeace.oj.ojbase.service.context.AdminNoteListQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "oj-后台笔记相关api")
@RestController
@RequestMapping("/oj/admin/note")
@RequiresAuthentication
public class AdminNoteController {

	@Autowired
	private NoteService noteService;

	@ApiOperation("分页查询实验报告")
	@GetMapping("/list")
	public ListRsp<NoteListBo> list(AdminListNoteReq noteReq) {

		Page page = noteReq.toPageParam();
		AdminNoteListQuery noteListQuery = BeanUtils.copy(noteReq, AdminNoteListQuery.class);
		noteListQuery.setUserId(UserInfoContext.getUserContext().getUserId());

		IPage<NoteListBo> noteListBos = noteService.pageByUserNameAndContent(page, noteListQuery, CommonConstant.FALSE);
		return PageUtil.getListResult(noteListBos);
	}

}
