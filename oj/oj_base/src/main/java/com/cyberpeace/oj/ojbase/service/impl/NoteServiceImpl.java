package com.cyberpeace.oj.ojbase.service.impl;

import java.util.List;
import java.util.Map;

import com.cyberpeace.common.model.AuthTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.dao.context.NoteListQuery;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.bo.NoteBo;
import com.cyberpeace.oj.ojbase.service.bo.NoteListBo;
import com.cyberpeace.oj.ojbase.service.bo.ReportBo;
import com.cyberpeace.oj.ojbase.service.context.AdminNoteListQuery;
import com.cyberpeace.oj.ojbase.service.context.NoteAddContext;

@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

	@Autowired
	private NoteMapper noteMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public NoteBo selectByUnique(NoteUniqueContext uniqueContext) {
		Note note = noteMapper.selectByCondition(uniqueContext);
		if (null == note) {
			return null;
		}

		return uniqueContext.getNoteType().intValue() == Note.NoteType.NOTE.getCode()
				? BeanUtils.copy(note, NoteBo.class)
				: BeanUtils.copy(note, ReportBo.class);
	}

	@Override
	public Note addNote(NoteAddContext noteAddContext) {
		Note note = BeanUtils.copy(noteAddContext, Note.class);
		note.setReadAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		note.setOperationAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		note.setIsPublic(CommonConstant.TRUE);
		try {
			noteMapper.insert(note);
		} catch (DuplicateKeyException e) {
			noteMapper.updateNote(note);
		}

		return note;
	}

	@Override
	public IPage<NoteListBo> pageByUserNameAndContent(Page page, AdminNoteListQuery noteListQuery,
			Boolean isWeb) {

		// 查询用户
		NoteListQuery listQuery = BeanUtils.copy(noteListQuery, NoteListQuery.class);
		Map<String, String> userInfoMap = null;
		if (StringUtils.isNotBlank(noteListQuery.getUserName())) {
			// todo 名称筛选
			List<UserInfo> userList = remoteUserService.getLazyUserList(null);
			List<String> userIds = StreamApiUtils.map(userList, UserInfo::getUserId);
			listQuery.setUserIds(userIds);

			userInfoMap = StreamApiUtils.toMap(userList, UserInfo::getUserId,
					UserInfo::getUserName);
		}

		IPage<Note> iPage = noteMapper.pageByUserIdsAndContent(page, listQuery, isWeb);

		if (StringUtils.isNotBlank(noteListQuery.getUserName())) {

			Map<String, String> finalUserInfoMap = userInfoMap;
			return iPage.convert(note -> {
				NoteListBo noteBo = BeanUtils.copy(note, NoteListBo.class);
				noteBo.setUserName(finalUserInfoMap.getOrDefault(note.getUserId(), ""));
				return noteBo;
			});
		} else {

			return iPage.convert(note -> {
				UserInfo userInfo = remoteUserService.getById(note.getUserId());
				NoteListBo noteBo = BeanUtils.copy(note, NoteListBo.class);
				noteBo.setUserName(userInfo.getUserName());
				return noteBo;
			});
		}

	}
}
