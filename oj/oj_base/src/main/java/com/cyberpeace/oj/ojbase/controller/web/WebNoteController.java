package com.cyberpeace.oj.ojbase.controller.web;

import javax.validation.Valid;

import com.cyberpeace.oj.ojbase.controller.req.NoteReq;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.ojbase.controller.req.GetNoteReq;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.bo.NoteBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "oj-前台笔记相关api")
@RestController
@RequestMapping("/oj/note")
@RequiresAuthentication
public class WebNoteController {

	@Autowired
	private NoteService noteService;

	@ApiOperation("笔记、实验报告新增")
	@PostMapping("")
	public BaseRsp add(@RequestBody NoteReq noteReq) {
		// todo
		return null;
	}

	@ApiOperation("单条笔记查询")
	@GetMapping
	public BaseRsp<NoteBo> getNote(@Valid GetNoteReq getNoteReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		NoteUniqueContext uniqueContext = BeanUtils.copy(getNoteReq, NoteUniqueContext.class);
		uniqueContext.setUser(userId);

		NoteBo noteBo = noteService.selectByUnique(uniqueContext);
		return new BaseRsp<>(noteBo);
	}

}
