package com.cyberpeace.oj.ojbase.controller.web;

import javax.validation.Valid;

import com.cyberpeace.oj.ojbase.constant.AuthShare;
import com.cyberpeace.oj.ojbase.controller.req.NoteAddReq;
import com.cyberpeace.oj.ojbase.controller.req.NoteUpdateReq;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.service.context.NoteAddContext;
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
	public BaseRsp<Note> add(@RequestBody @Valid NoteAddReq noteReq) {

		NoteAddContext noteAddContext = BeanUtils.copy(noteReq, NoteAddContext.class);
		noteAddContext.setUserId(UserInfoContext.getUserContext().getUserId());
		Note newNote = noteService.addNote(noteAddContext);

		return new BaseRsp<>(newNote);
	}

	@ApiOperation("实验报告笔记修改")
	@PatchMapping("")
	public BaseRsp<Note> update(@RequestBody @Valid NoteUpdateReq noteUpdateReq) {

		NoteAddContext context = BeanUtils.copy(noteUpdateReq, NoteAddContext.class);
		Note note = noteService.updateNote(context);

		return new BaseRsp<>(note);

	}

	@ApiOperation("单条笔记查询")
	@GetMapping("")
	public BaseRsp<NoteBo> getNote(@Valid GetNoteReq getNoteReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		NoteUniqueContext uniqueContext = BeanUtils.copy(getNoteReq, NoteUniqueContext.class);
		uniqueContext.setUserId(userId);

		NoteBo noteBo = noteService.selectByUnique(uniqueContext);
		return new BaseRsp<>(noteBo);
	}



}
