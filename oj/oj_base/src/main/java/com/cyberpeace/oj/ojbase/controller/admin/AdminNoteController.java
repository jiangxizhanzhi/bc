package com.cyberpeace.oj.ojbase.controller.admin;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.ojbase.controller.req.DeleteResourceReq;
import com.cyberpeace.oj.ojbase.controller.req.EvaluateNoteReq;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteEvaluateMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

import java.util.Date;

/**
 * 管理笔记Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "oj-后台笔记相关api")
@RestController
@RequestMapping("/oj/admin/note")
@RequiresAuthentication
public class AdminNoteController {

	/**
	 * 笔记Service
	 */
	@Autowired
	private NoteService noteService;

	/**
	 * 笔记Mapper
	 */
	@Autowired
	private NoteMapper noteMapper;

	/**
	 * 笔记评估Mapper
	 */
	@Autowired
	private NoteEvaluateMapper noteEvaluateMapper;

	/**
	 * 列表
	 *
	 * @param noteReq 笔记请求体
	 * @return {@code BaseRsp<ListRsp<NoteListBo>> }
	 */
	@ApiOperation("分页查询实验报告")
	@GetMapping("/list")
	public BaseRsp<ListRsp<NoteListBo>> list(AdminListNoteReq noteReq) {

		Page<NoteListBo> page = noteReq.toPageParam();
		AdminNoteListQuery noteListQuery = BeanUtils.copy(noteReq, AdminNoteListQuery.class);
		noteListQuery.setUserId(UserInfoContext.getUserContext().getUserId());

		IPage<NoteListBo> noteListBos = noteService.pageByUserNameAndContent(page, noteListQuery,
				CommonConstant.FALSE);
		return new BaseRsp<>(PageUtil.getListResult(noteListBos));
	}

	/**
	 * 删除id列表
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("笔记/实验报告删除")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteIds(@RequestBody @Validated DeleteResourceReq deleteReq) {

		int row = noteMapper.deleteBatchIds(deleteReq.getResourceIds());
		return new BaseRsp<>(row > 0);
	}

	/**
	 * 评估报告
	 *
	 * @param evaluateNoteReq 评估笔记请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("实验报告评估")
	@PatchMapping("/evaluate")
	public BaseRsp<Boolean>
			evaluateReport(@RequestBody @Validated EvaluateNoteReq evaluateNoteReq) {

		// 查询当前笔记评估
		NoteEvaluate currentNoteEvaluate = noteEvaluateMapper
				.selectByNote(evaluateNoteReq.getNoteId());

		NoteEvaluate noteEvaluate = BeanUtils.copy(evaluateNoteReq, NoteEvaluate.class);
		noteEvaluate.setEvaluateTime(new Date());
		noteEvaluate.setEvaluatorId(UserInfoContext.getUserContext().getUserId());

		if (null == currentNoteEvaluate) {
			noteEvaluateMapper.insert(noteEvaluate);
		} else {
			noteEvaluateMapper.updateByNote(noteEvaluate);
		}

		return new BaseRsp<>(true);
	}

}
