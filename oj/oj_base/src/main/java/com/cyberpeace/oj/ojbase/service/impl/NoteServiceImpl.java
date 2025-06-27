package com.cyberpeace.oj.ojbase.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

/**
 * 笔记Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Slf4j
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

	/**
	 * 笔记Mapper
	 */
	@Autowired
	private NoteMapper noteMapper;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 笔记评估Mapper
	 */
	@Autowired
	private NoteEvaluateMapper noteEvaluateMapper;

	/**
	 * 查询由独特
	 *
	 * @param uniqueContext 独特上下文
	 * @return {@code NoteBo }
	 */
	@Override
	public NoteBo selectByUnique(NoteUniqueContext uniqueContext) {
		Note note = noteMapper.selectByCondition(uniqueContext);
		if (null == note) {
			return null;
		}

		return BeanUtils.copy(note, NoteBo.class);
	}

	/**
	 * 新增笔记
	 *
	 * @param noteAddContext 笔记新增上下文
	 * @return {@code Note }
	 */
	@Override
	public Note addNote(NoteAddContext noteAddContext) {

		Note currentNote = noteMapper.selectByUnique(noteAddContext.getUserId(), noteAddContext.getNoteType(),
				noteAddContext.getBusinessType(), noteAddContext.getBusinessId());

		Note note = BeanUtils.copy(noteAddContext, Note.class);
		note.setReadAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		note.setOperationAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		note.setIsPublic(CommonConstant.TRUE);

		if (null != currentNote) {
			noteMapper.updateNote(note);
		} else {
			noteMapper.insert(note);
		}

		return note;
	}

	/**
	 * 分页由用户名称和内容
	 *
	 * @param page          分页
	 * @param noteListQuery 笔记列表查询
	 * @param isWeb         是否Web
	 * @return {@code IPage<NoteListBo> }
	 */
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

	/**
	 * 新增和更新报告
	 *
	 * @param reportAddReq 报告新增请求体
	 * @return {@code Note }
	 */
	@Override
	public Note addAndUpdateReport(ReportAddReq reportAddReq) {

		String userId = UserInfoContext.getUserContext().getUserId();
		Integer noteType = Note.NoteType.REPORT.getCode();
		Integer businessType = Note.NoteResourceType.LESSON.getCode();

		Note currentNote = noteMapper.selectByUnique(userId, noteType, businessType, reportAddReq.getBusinessId());

		NoteAddContext noteAddContext = BeanUtils.copy(reportAddReq, NoteAddContext.class);
		noteAddContext.setNoteType(noteType);
		noteAddContext.setBusinessType(businessType);
		noteAddContext.setUserId(userId);

		Note note = BeanUtils.copy(noteAddContext, Note.class);
		note.setReadAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		note.setOperationAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		note.setIsPublic(CommonConstant.TRUE);
		note.setIsSubmit(reportAddReq.getOperate());

		if (null == currentNote){
			noteMapper.insert(note);
		} else {
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

	/**
	 * 获取计数
	 *
	 * @param userId      用户id
	 * @param noteType    笔记类型
	 * @param resourceIds 资源id列表
	 * @return {@code Integer }
	 */
	@Override
	public Integer getCount(String userId, Integer noteType, List<String> resourceIds) {
		return noteMapper.countByUserId(userId, noteType, resourceIds);
	}

	/**
	 * 删除由业务
	 *
	 * @param businessId   业务id
	 * @param noteType     笔记类型
	 * @param businessType 业务类型
	 */
	@Override
	public void deleteByBusiness(String businessId, Integer noteType, Integer businessType) {
		noteMapper.deleteByBusiness(businessId,noteType,businessType);
	}
}
