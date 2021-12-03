package com.cyberpeace.oj.ojbase.controller.web;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.oj.ojbase.controller.res.WebNoteRes;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.ojbase.controller.req.GetNoteReq;
import com.cyberpeace.oj.ojbase.controller.req.NoteAddReq;
import com.cyberpeace.oj.ojbase.controller.req.NoteUpdateReq;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.bo.NoteBo;
import com.cyberpeace.oj.ojbase.service.context.NoteAddContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "oj-前台笔记相关api")
@RestController
@RequestMapping("/oj/note")
@RequiresAuthentication
public class WebNoteController {

	@Autowired
	private NoteService noteService;

	@Autowired
	private NoteMapper noteMapper;

	@ApiOperation("笔记、实验报告新增")
	@PostMapping("")
	public BaseRsp<WebNoteRes> add(@RequestBody @Validated NoteAddReq noteReq) {

		NoteAddContext noteAddContext = BeanUtils.copy(noteReq, NoteAddContext.class);
		noteAddContext.setUserId(UserInfoContext.getUserContext().getUserId());
		Note newNote = noteService.addNote(noteAddContext);

		WebNoteRes webNoteRes = BeanUtils.copy(newNote, WebNoteRes.class);

		return new BaseRsp<>(webNoteRes);
	}

	@ApiOperation("实验报告笔记修改")
	@PatchMapping("")
	public BaseRsp<Integer> update(@RequestBody @Validated NoteUpdateReq noteUpdateReq) {

		Note note = BeanUtils.copy(noteUpdateReq, Note.class);

		Integer result = noteMapper.updateById(note);

		return new BaseRsp<>(result);

	}

	@ApiOperation("单条笔记查询")
	@GetMapping("")
	public BaseRsp<WebNoteRes> getNote(@Validated GetNoteReq getNoteReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		NoteUniqueContext uniqueContext = NoteUniqueContext.builder()
				.noteType(getNoteReq.getNoteType()).businessType(getNoteReq.getResourceType())
				.businessId(getNoteReq.getResourceId()).userId(userId).build();

		NoteBo noteBo = noteService.selectByUnique(uniqueContext);
		if (null == noteBo) {
			WebNoteRes webNoteRes = WebNoteRes.builder()
					.content(ConfigUtil.getContextProperty("oj.report.template.data")).build();
			return new BaseRsp<>(webNoteRes);
		}
		WebNoteRes noteRes = BeanUtils.copy(noteBo, WebNoteRes.class);
		return new BaseRsp<>(noteRes);
	}

}
