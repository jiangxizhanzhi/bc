package com.cyberpeace.oj.ojbase.controller.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.oj.ojbase.controller.req.GetNoteReq;
import com.cyberpeace.oj.ojbase.controller.req.NoteAddReq;
import com.cyberpeace.oj.ojbase.controller.req.NoteAttachmentReq;
import com.cyberpeace.oj.ojbase.controller.req.NoteUpdateReq;
import com.cyberpeace.oj.ojbase.controller.res.WebNoteRes;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteEvaluateMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
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

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private NoteEvaluateMapper noteEvaluateMapper;

	@Autowired
	private UtilService utilService;

	@ApiOperation("笔记、实验报告新增")
	@PostMapping("")
	@RequiresPermissions(
			value = { MenuConstant.WEB_OJ_ALL_COURSE, MenuConstant.WEB_OJ_PRACTICE_OPERATION },
			logical = Logical.OR)
	public BaseRsp<WebNoteRes> add(@RequestBody @Validated NoteAddReq noteReq) {

		NoteAddContext noteAddContext = BeanUtils.copy(noteReq, NoteAddContext.class);
		noteAddContext.setUserId(UserInfoContext.getUserContext().getUserId());
		Note newNote = noteService.addNote(noteAddContext);

		WebNoteRes webNoteRes = BeanUtils.copy(newNote, WebNoteRes.class);

		return new BaseRsp<>(webNoteRes);
	}

	@ApiOperation("实验报告笔记修改")
	@PatchMapping("")
	@RequiresPermissions(
			value = { MenuConstant.WEB_OJ_ALL_COURSE, MenuConstant.WEB_OJ_PRACTICE_OPERATION },
			logical = Logical.OR)
	public BaseRsp<Integer> update(@RequestBody @Validated NoteUpdateReq noteUpdateReq) {

		Note note = BeanUtils.copy(noteUpdateReq, Note.class);

		Integer result = noteMapper.updateById(note);

		return new BaseRsp<>(result);

	}

	@ApiOperation("单条笔记查询")
	@GetMapping("")
	@RequiresPermissions(
			value = { MenuConstant.WEB_OJ_ALL_COURSE, MenuConstant.WEB_OJ_PRACTICE_OPERATION, MenuConstant.CMS_CLASS_ROOM },
			logical = Logical.OR)
	public BaseRsp<WebNoteRes> getNote(@Validated GetNoteReq getNoteReq) {

		NoteUniqueContext uniqueContext = NoteUniqueContext.builder()
				.noteType(getNoteReq.getNoteType()).businessType(getNoteReq.getResourceType())
				.businessId(getNoteReq.getResourceId())
				.userId(UserInfoContext.getUserContext().getUserId()).build();

		NoteBo noteBo = noteService.selectByUnique(uniqueContext);

		// 无笔记存在 实验报告返回默认值
		if (null == noteBo || StringUtils.isBlank(noteBo.getContent())) {
			BaseRsp<WebNoteRes> rsp = new BaseRsp<>();
			if (getNoteReq.getNoteType().intValue() == Note.NoteType.REPORT.getCode().intValue()) {
				String template_name = "oj.report.template_en.data";
				if (CommonContext.isChinese()) {
					template_name = "oj.report.template.data";
				}
				WebNoteRes webNoteRes = WebNoteRes.builder()
						.content(ConfigUtil.getContextProperty(template_name)).build();

				if (null == noteBo) {
					rsp.setData(webNoteRes);
					return rsp;
				}
				String note_content = webNoteRes.getContent();
				noteBo.setContent(note_content);

			} else if (null == noteBo) {
				return rsp;
			}

		}

		WebNoteRes noteRes = BeanUtils.copy(noteBo, WebNoteRes.class);
		// 实验报告数据评估结果组装
		if (getNoteReq.getNoteType().intValue() == Note.NoteType.REPORT.getCode().intValue()) {
			NoteEvaluate noteEvaluate = noteEvaluateMapper.selectByNote(noteRes.getResourceId());
			if (null != noteEvaluate) {
				noteRes.setScore(noteEvaluate.getScore());
				noteRes.setPassed(noteEvaluate.getPassed());
				noteRes.setEvaluateComment(noteEvaluate.getEvaluateComment());
			}
		}

		return new BaseRsp<>(noteRes);
	}

	@ApiOperation("实验报告/笔记文件上传")
	@PostMapping("/upload_attachment")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE,
			MenuConstant.WEB_OJ_PRACTICE_OPERATION}, logical = Logical.OR)
	public BaseRsp<AttachmentBo> uploadAttachment(NoteAttachmentReq attachmentReq) {

		Integer type;

		if (attachmentReq.getNoteType().intValue() == Note.NoteType.NOTE.getCode().intValue()) {
			if (attachmentReq.getResourceType().intValue() == Note.NoteResourceType.LESSON_PAPER
					.getCode().intValue()) {
				type = AttachmentPath.PRACTICE_KEY.getCode();
			} else {
				type = AttachmentPath.NOTE.getCode();
			}
		} else {
			type = AttachmentPath.REPORT.getCode();
		}

		AttachmentBo uploadFile = attachmentService.uploadFile(type, false, attachmentReq.getFile(),
				true);

		// 文件路径处理
		String viewPath = utilService.handlerFileUrl(uploadFile.getFilePath());
		uploadFile.setFilePath(viewPath);
		return new BaseRsp(uploadFile);
	}

}
