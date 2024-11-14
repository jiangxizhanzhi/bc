package com.cyberpeace.rtks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.constant.BaseI18n;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.frame.FrameAddInput;
import com.cyberpeace.rtks.controller.frame.FrameModifyInput;
import com.cyberpeace.rtks.controller.frame.FrameOutput;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.service.FrameService;
import com.cyberpeace.rtks.service.LevelService;
import com.cyberpeace.rtks.service.RoleService;
import com.cyberpeace.rtks.service.SkillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

class FrameListReq extends PageReqBase {

	/**
	 * 所属框架id，可以为空
	 */
	@ApiModelProperty(value = "所属框架id，可以为空")
	@Getter
	@Setter
	private String frameId;

	/**
	 * 模糊查询关键词
	 */
	@ApiModelProperty(value = "模糊查询关键词")
	@Getter
	@Setter
	private String keyword;
}

@Api(tags = "TRKS-框架管理")
@RestController
@RequestMapping("/rtks/frame")
@RequiresAuthentication
public class FrameController {

	@Autowired
	private FrameService frameService;

	@Autowired
	private LevelService levelService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SkillService skillService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteLogService remoteLogService;

	private Boolean singleFrame;

	private void multiFrameOnly() {
		if (singleFrame) {
			throw new BusinessException(RtksBusinessErrorCode.SINGLE_FRAME_DISABLE);
		}
	}

	private void singleFrameOnly() {
		if (!singleFrame) {
			throw new BusinessException(RtksBusinessErrorCode.MULTI_FRAME_DISABLE);
		}
	}

	/**
	 * 单框架模式下 默认的框架id
	 */
	private final String DEFAULT_FRAME_ID = "default";

	@PostConstruct
	public void init() {
		String singleFrameConfig = ConfigUtil.getContextProperty("singleFrame");
		singleFrame = Objects.equals(singleFrameConfig, "true");
	}

	protected FrameService getService() {
		return frameService;
	}

	protected Level getOr404(String resourceId) {
		return frameService.getOr404(resourceId);
	}

	protected Level getEntityFromAddInput(FrameAddInput frameAddInput) {
		Level frame = new Level();
		frame.setName(frameAddInput.getName());
		frame.setDescription(frameAddInput.getDescription());
		return frame;
	}

	protected Level getEntityFromModifyInput(FrameModifyInput frameModifyInput) {
		Level frame = new Level();
		if (!singleFrame) {
			// 单框架模式下框架名称不可变
			frame.setName(frameModifyInput.getName());
		}
		frame.setDescription(frameModifyInput.getDescription());
		frame.setResourceId(frameModifyInput.getResourceId());
		return frame;
	}

	protected FrameOutput getOutputFromEntity(Level frame) {
		FrameOutput output = new FrameOutput();

		output.setResourceId(frame.getResourceId());
		output.setName(frame.getName());
		output.setDescription(frame.getDescription());

		return output;
	}

	protected IPage<Level> listEntity(Page<Level> page, String keyword) {
		multiFrameOnly();
		return frameService.listEntity(page, keyword);
	}

	protected FrameOutput getListOutputFromEntity(Level frame) {
		return getOutputFromEntity(frame);
	}

	protected void performBatchDelete(List<String> frameIds) {
		multiFrameOnly();
		// 检查输入
		frameIds = frameService.checkFrameIds(frameIds);

		// 获取框架对应层级
		List<String> levelIds = levelService.getLevelIdsByFrameIds(frameIds);

		// 获取层级对应岗位
		List<String> roleIds = roleService.getRoleIdsByLevelIds(levelIds);

		// 删除岗位
		roleService.removeByIds(roleIds);

		// 删除层级
		levelService.removeByIds(levelIds);

		// 删除框架
		frameService.removeByIds(frameIds);
	}

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<FrameOutput> create(@RequestBody @Validated FrameAddInput input) {
		Level entity = getEntityFromAddInput(input);

		getService().save(entity);

		return retrieve(entity.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<FrameOutput> update(@RequestBody @Validated FrameModifyInput input) {
		String resourceId = ObjUtil.getCertainFieldValue(input, "resourceId");
		getOr404(resourceId);
		Level entity = getEntityFromModifyInput(input);
		getService().updateById(entity);

		return retrieve(resourceId);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<FrameOutput> retrieve(String resource_id) {
		Level t = getOr404(resource_id);
		return new BaseRsp<>(getOutputFromEntity(t));
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		getService().removeByIds(deleteReq.getIds());
		return new BaseRsp<>();
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<FrameOutput>> list(FrameListReq listReq) {
		IPage<Level> page = listEntity(listReq.toPageParam(), listReq.getKeyword());

		List<FrameOutput> outputs = new ArrayList<>();
		for (Level level : page.getRecords()) {
			outputs.add(getListOutputFromEntity(level));
		}

		ListRsp<FrameOutput> listRsp = new ListRsp<>();
		listRsp.setList(outputs);
		listRsp.setTotal(page.getTotal());

		return new BaseRsp<>(listRsp);
	}

	@ApiOperation("指定框架层级数据的全量树形展示")
	@GetMapping("/tree")
	public BaseRsp<LevelTreeOutput> getTree(String resource_id) {
		return new BaseRsp<>(frameService.getTree(resource_id));
	}

	@ApiOperation("清空默认框架，仅单框架模式下有效")
	@PostMapping("/clear")
	public BaseRsp<FrameOutput> clearFrame() {
		singleFrameOnly();
		// 除根节点外删除所有框架、层级
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				roleService.clearRole();
				skillService.clearAll();

				levelService.removeAllLevels();
				roleService.removeAll("task");
				roleService.removeAll("knowledge_category");
				roleService.removeAll("knowledge");
				roleService.removeAll("course_related");
			}
		});

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.DELETE, LogContent.FRAME.getMsg(),
				LogContent.RTKS_FRAME.format(FrameOperation.EMPTY, LogContent.FRAME.getMsg()));

		return retrieve(DEFAULT_FRAME_ID);
	}

	@ApiOperation("重置默认框架，仅单框架模式下有效")
	@PostMapping("/reset")
	public BaseRsp<FrameOutput> resetFrame() {
		singleFrameOnly();
		// 重置所有框架、层级
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				roleService.clearRole();
				skillService.clearAll();

				levelService.initDefaultFromPreset();
				roleService.initDefaultFromPreset("role");
				roleService.initDefaultFromPreset("task");
				roleService.initDefaultFromPreset("knowledge_category");
				roleService.initDefaultFromPreset("knowledge");
				roleService.initDefaultFromPreset("course_related");
				// 导入预置的role-skill关系
				roleService.initDefaultFromPreset("role_skill_relation");
			}
		});

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.UPDATE, LogContent.FRAME.getMsg(),
				LogContent.RTKS_FRAME.format(FrameOperation.RESET, LogContent.FRAME.getMsg()));

		return retrieve(DEFAULT_FRAME_ID);
	}
}

enum FrameOperation implements BaseI18n {

	RESET(0, "重置", "Reset"),
	EMPTY(1, "清空", "Empty");

	@Getter
	private final Integer code;

	@Getter
	private final String chinese;

	@Getter
	private final String english;

	FrameOperation(Integer code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}
}
