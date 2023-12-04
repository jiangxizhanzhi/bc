package com.cyberpeace.rtks.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.frame.*;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.service.FrameService;
import com.cyberpeace.rtks.service.LevelService;
import com.cyberpeace.rtks.service.RoleService;
import com.cyberpeace.rtks.util.RtksCommonValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Api(tags = "TRKS-框架层级管理")
@RestController
@RequestMapping("/rtks/frame/level")
@RequiresAuthentication
public class LevelController {

	@Autowired
	private LevelService levelService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private FrameService frameService;

	@Autowired
	private RemoteLogService remoteLogService;

	@ApiOperation("新增")
	@PostMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<LevelOutput> create(@RequestBody @Validated LevelAddInput input) {
		RtksCommonValidator.validateNameLen(input.getName(), RtksBusinessErrorCode.ILLEGAL_NAME_LEN);
		Level entity = getEntityFromAddInput(input);

		getService().save(entity);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.CREATE, entity.getName(), LogContent.CR_FORMAT
						.format(OperationType.CREATE, LogContent.LEVEL.getMsg(), entity.getName()));

		return retrieve(entity.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<LevelOutput> update(@RequestBody @Validated LevelModifyInput input) {
		String resourceId = ObjUtil.getCertainFieldValue(input, "resourceId");

		Level originEntity = getOr404(resourceId);

		Level entity = getEntityFromModifyInput(input, originEntity);

		getService().updateById(entity);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.UPDATE, entity.getName(), LogContent.CR_FORMAT
						.format(OperationType.UPDATE, LogContent.LEVEL.getMsg(), entity.getName()));

		return retrieve(resourceId);
	}

	@ApiOperation("查询")
	@GetMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<LevelOutput> retrieve(String resource_id) {
		Level t = getOr404(resource_id);
		return new BaseRsp<>(getOutputFromEntity(t));
	}

	@ApiOperation("删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {

		List<Level> levelList = levelService.listByIds(deleteReq.getIds());

		performBatchDelete(deleteReq.getIds());

		List<String> columns = StreamApiUtils.getColumn(levelList, Level::getName);
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.DELETE, LogContent.BATCH_DELETE_FORMAT.format(LogContent.LEVEL.getMsg()), LogContent.CR_FORMAT.format(
						OperationType.DELETE, LogContent.LEVEL.getMsg(), columns.toString()));


		return new BaseRsp<>();
	}

	protected LevelService getService() {
		return levelService;
	}

	protected Level getOr404(String resourceId) {
		return levelService.getOr404(resourceId);
	}

	protected Level getEntityFromAddInput(LevelAddInput levelAddInput) {
		return getLevelFromInput(levelAddInput, null);
	}

	protected Level getEntityFromModifyInput(LevelModifyInput levelModifyInput,
			Level originEntity) {
		Level level = getLevelFromInput(levelModifyInput, originEntity);
		level.setResourceId(levelModifyInput.getResourceId());
		return level;
	}

	private Level getLevelFromInput(LevelInput input, Level originEntity) {
		Level level = new Level();
		level.setName(input.getName());
		level.setDescription(input.getDescription());

		String parentId = input.getParent();
		level.setParent(parentId);

		// 修改时parentId参数未传，无改动
		if (parentId == null) {
			return level;
		}

		// 修改时parentId参数无改动
		if (originEntity != null && originEntity.getParent().equals(parentId)) {
			return level;
		}

		// 已经绑定了岗位的层级不允许新增子层级
		List<Role> roles = roleService.getRolesByLevelId(parentId);
		if (!CollectionUtils.isEmpty(roles)) {
			throw new BusinessException(RtksBusinessErrorCode.UNABLE_ADD_FRAME);
		}

		// 确保parentId对应的框架/层级存在
		Level parentLevel = levelService.getLevelOrFrameOr404(parentId);

		// 确保当前不超过第二级
		String maxTwoLevelConfig = ConfigUtil.getContextProperty("maxTwoLevel");
		if (Objects.equals(maxTwoLevelConfig, "true")) {
			levelService.ensureLessThanTwoLevel(parentLevel);
		}

		// 迭代获取当前层级所属框架
		level.setFrame(levelService.getFrame(parentLevel).getResourceId());

		//检验二层框架是否已满
		if(level.getParent().equals("default")){
			levelService.checkFull();
		}

		return level;
	}

	protected LevelOutput getOutputFromEntity(Level level) {
		LevelOutput output = new LevelOutput();

		output.setResourceId(level.getResourceId());
		output.setName(level.getName());
		output.setDescription(level.getDescription());
		output.setParent(level.getParent());
		output.setFrame(level.getFrame());

		return output;
	}

	protected void performBatchDelete(List<String> ids) {
		// 验证输入层级id合法
		List<Level> levels = levelService.getLevelsByIds(ids);
		List<String> levelIds = new ArrayList<>();
		for (Level level : levels) {

			if (!checkLevelChildren(level)) {
				levelIds.add(level.getResourceId());
			}
		}

		if (levelIds.size() == 0) {
			throw new BusinessException(RtksBusinessErrorCode.UNABLE_DELETE_FRAME);
		}

		levelService.removeByIds(levelIds);
	}

	// 检查层级的子层级是否有岗位
	private boolean checkLevelChildren(Level level) {
		boolean result = false;
		if (!StringUtils.isBlank(level.getFrame())) {

			LevelTreeOutput frame = frameService.getTree(level.getFrame());// 获取框架树
			LevelTreeOutput levelTreeOutput = getLevelTreeChild(frame, level.getResourceId());// 获取层级树
			result = levelService.checkHasRole(levelTreeOutput);
		}
		return result;

	}

	private LevelTreeOutput getLevelTreeChild(LevelTreeOutput frame, String levelId) {

		if (StringUtils.equals(levelId, frame.getResourceId())) {
			return frame;
		} else {
			List<LevelTreeOutput> children = frame.getChildren();
			if (!CollectionUtils.isEmpty(children)) {
				for (LevelTreeOutput child : children) {
					LevelTreeOutput levelTreeChild = getLevelTreeChild(child, levelId);
					if (levelTreeChild != null) {
						return levelTreeChild;
					}
				}
			}

			return null;
		}

	}

	/**
	 * 给定层级(非框架) 获取其父层级/框架id 如果该id在给定levelIds数组中，则再迭代查找
	 * */
	private String getAncestorExclude(@NotNull Level level, @NotNull List<String> levelIds) {
		String parentId = level.getParent();
		if (!levelIds.contains(parentId)) {
			return parentId;
		}

		levelIds.remove(parentId);

		Level parent = levelService.getById(parentId);
		return getAncestorExclude(parent, levelIds);
	}

}
