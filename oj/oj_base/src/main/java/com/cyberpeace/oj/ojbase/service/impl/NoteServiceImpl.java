package com.cyberpeace.oj.ojbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.req.ReportAddReq;
import com.cyberpeace.oj.ojbase.dao.context.NoteListQuery;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteEvaluateMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.bo.NoteBo;
import com.cyberpeace.oj.ojbase.service.bo.NoteListBo;
import com.cyberpeace.oj.ojbase.service.context.AdminNoteListQuery;
import com.cyberpeace.oj.ojbase.service.context.NoteAddContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

	@Autowired
	private NoteMapper noteMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private NoteEvaluateMapper noteEvaluateMapper;

	@Override
	public NoteBo selectByUnique(NoteUniqueContext uniqueContext) {
		Note note = noteMapper.selectByCondition(uniqueContext);
		if (null == note) {
			return null;
		}

		return BeanUtils.copy(note, NoteBo.class);
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
			UserPageContext pageContext = UserPageContext.builder()
					.username(noteListQuery.getUserName()).build();
			List<UserInfo> userList = remoteUserService.getFuzzyQueryUserList(pageContext);
			if (userList.isEmpty()) {
				return page;
			}
			List<String> userIds = StreamApiUtils.map(userList, UserInfo::getUserId);
			listQuery.setUserIds(userIds);

			userInfoMap = StreamApiUtils.toMap(userList, UserInfo::getUserId,
					UserInfo::getName);
		}
		IPage<Note> iPage;
		if (listQuery.getNoteType().equals(Note.NoteType.REPORT.getCode())) {
			iPage = noteMapper.pageByUserIdsAndContent(page, listQuery, isWeb, true);
		}else {
			iPage = noteMapper.pageByUserIdsAndContent(page, listQuery, isWeb, null);
		}

		if (StringUtils.isNotBlank(noteListQuery.getUserName())) {

			Map<String, String> finalUserInfoMap = userInfoMap;
			return iPage.convert(note -> {
				NoteListBo noteBo = BeanUtils.copy(note, NoteListBo.class);
				noteBo.setUserName(finalUserInfoMap.getOrDefault(note.getUserId(), ""));
				return noteBo;
			});
		} else {
			List<String> userIds = iPage.getRecords().stream().map(Note::getUserId)
					.collect(Collectors.toList());
			Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);

			return iPage.convert(note -> {
				NoteListBo noteBo = BeanUtils.copy(note, NoteListBo.class);
				noteBo.setUserName(idNameMap.get(note.getUserId()));
				return noteBo;
			});
		}

	}

	@Override
	public Note addAndUpdateReport(ReportAddReq reportAddReq) {
		NoteAddContext noteAddContext = BeanUtils.copy(reportAddReq, NoteAddContext.class);
		noteAddContext.setNoteType(Note.NoteType.REPORT.getCode());
		noteAddContext.setBusinessType(Note.NoteResourceType.LESSON.getCode());
		noteAddContext.setUserId(UserInfoContext.getUserContext().getUserId());

		Note note = BeanUtils.copy(noteAddContext, Note.class);
		note.setReadAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		note.setOperationAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		note.setIsPublic(CommonConstant.TRUE);
		note.setIsSubmit(reportAddReq.getOperate());
		try {
			noteMapper.insert(note);
		} catch (DuplicateKeyException e) {
			Note report = noteMapper.selectByUnique(note.getUserId(), note.getNoteType(), note.getBusinessType(), note.getBusinessId());
			//已经提交过的无法再存草稿
			if (report.getIsSubmit() != null && report.getIsSubmit() && Boolean.FALSE.equals(reportAddReq.getOperate())) {
				log.error("report [{}] can't be duplicate submit", report.getContent());
				throw new BusinessException(OjBusinessErrorCode.CANT_SAVE_SUBMIT_REPORT);
			}
			//已经通过的无法再提交
			NoteEvaluate noteEvaluate = noteEvaluateMapper.selectByNote(report.getResourceId());
			if (noteEvaluate != null && noteEvaluate.getPassed()) {
				log.error("[{}] can't be duplicate submit", noteEvaluate.getResourceId());
				throw new BusinessException(OjBusinessErrorCode.CANT_SUBMIT_EVALUATE_REPORT);
			}
			note.setIsSubmit(reportAddReq.getOperate());
			note.setResourceId(report.getResourceId());
			noteMapper.updateReportByID(note);
		}
		return note;
	}

	@Override
	public Integer getCount(String userId, Integer noteType, List<String> resourceIds) {
		return noteMapper.countByUserId(userId, noteType, resourceIds);
	}

	@Override
	public void deleteByBusiness(String businessId, Integer noteType, Integer businessType) {
		noteMapper.deleteByBusiness(businessId,noteType,businessType);
	}
}
