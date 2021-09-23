package com.cyberpeace.oj.ojbase.service.impl;

import java.util.Date;

import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.ojbase.service.bo.NoteBo;
import com.cyberpeace.oj.ojbase.service.bo.ReportBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.context.NoteAddContext;

@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

	@Autowired
	private NoteMapper noteMapper;

	@Override
	public NoteAddContext updateOrSave(NoteAddContext addContext) {
		if (StringUtils.isBlank(addContext.getResourceId())) {
			insert(addContext);
		} else {
			Note note = noteMapper.selectById(addContext.getResourceId());
			update(note, addContext);
		}

		return addContext;
	}

	@Override
	public NoteBo selectByUnique(NoteUniqueContext uniqueContext) {
		Note note = noteMapper.selectByCondition(uniqueContext);
		if (null == note) {
			return null;
		}

		return uniqueContext.getNoteType().intValue() == Note.NOTE
				? BeanUtils.copy(note, NoteBo.class)
				: BeanUtils.copy(note, ReportBo.class);
	}

	/**
	 * 新增笔记
	 * @param addContext
	 */
	private void insert(NoteAddContext addContext) {

		NoteUniqueContext uniqueContext = BeanUtils.copy(addContext, NoteUniqueContext.class);

		Note existNote = noteMapper.selectByCondition(uniqueContext);
		if (existNote == null) {
			Note note = BeanUtils.copy(addContext, Note.class);
			note.setSubmitTime(new Date());
			note.setIsPublic(Boolean.TRUE);
			note.setScore(0d);
			note.setIsPass(Boolean.FALSE);
			noteMapper.insert(note);

		} else {
			update(existNote, addContext);
		}
	}

	/**
	 * 更新笔记
	 * @param existNote
	 * @param addContext
	 */
	private void update(Note existNote, NoteAddContext addContext) {
		org.springframework.beans.BeanUtils.copyProperties(addContext, existNote);
		noteMapper.updateById(existNote);
	}

}
