package com.cyberpeace.oj.ojbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.oj.ojbase.constant.AuthShare;
import com.cyberpeace.oj.ojbase.dao.context.NoteListQuery;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.service.bo.NoteListBo;
import com.cyberpeace.oj.ojbase.service.context.AdminNoteListQuery;
import com.cyberpeace.oj.ojbase.service.context.NoteAddContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.ojbase.constant.NoteType;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.bo.NoteBo;
import com.cyberpeace.oj.ojbase.service.bo.ReportBo;

@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

	@Autowired
	private NoteMapper noteMapper;

	@Override
	public NoteBo selectByUnique(NoteUniqueContext uniqueContext) {
		Note note = noteMapper.selectByCondition(uniqueContext);
		if (null == note) {
			return null;
		}

		return uniqueContext.getNoteType().intValue() == NoteType.NOTE.getCode()
				? BeanUtils.copy(note, NoteBo.class)
				: BeanUtils.copy(note, ReportBo.class);
	}

	@Override
	public Note addNote(NoteAddContext noteAddContext) {
		Note note = BeanUtils.copy(noteAddContext, Note.class);
		note.setReadAuthType(AuthShare.ALL.getCode());
		note.setOperationAuthType(AuthShare.ALL.getCode());
		note.setIsPublic(CommonConstant.TRUE);
		updateOrSave(note);
		return note;
	}

	@Override
	public Note updateNote(NoteAddContext context) {
		Note note = BeanUtils.copy(context, Note.class);
		updateOrSave(note);
		return note;
	}

	@Override
	public IPage<NoteListBo> pageByUserNameAndcontent(Page page, AdminNoteListQuery noteListQuery,
			Boolean isWeb) {
		// 查询用户

		NoteListQuery listQuery = BeanUtils.copy(noteListQuery, NoteListQuery.class);
		IPage<Note> iPage = noteMapper.pageByUserIdsAndContent(page, listQuery, isWeb);
		return null;
	}

	/**
	 * 新增或修改笔记信息
	 * @param note
	 */
	private void updateOrSave(Note note) {
		try {
			noteMapper.insert(note);
		} catch (DuplicateKeyException e) {
			noteMapper.updateById(note);
		}
	}

}
