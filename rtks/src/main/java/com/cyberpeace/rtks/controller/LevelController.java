package com.cyberpeace.rtks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.util.StreamApiUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 层级Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Api(tags = "TRKS-框架层级管理")
@RestController
@RequestMapping("/rtks/frame/level")
@RequiresAuthentication
public class LevelController {

	/**
	 * 水平Service
	 *
	 * @see LevelService
	 */
	@Autowired
	private LevelService levelService;

	/**
	 * 岗位Service
	 *
	 * @see RoleService
	 */
	@Autowired
	private RoleService roleService;

	/**
	 * 框架Service
	 *
	 * @see FrameService
	 */
	@Autowired
	private FrameService frameService;

	/**
	 * 远程日志Service
	 *
	 * @see RemoteLogService
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 创建
	 *
	 * @param input 输入
	 * @return {@code BaseRsp<LevelOutput> }
	 * @throws
	 * @see LevelAddInput
	 * @see BaseRsp<LevelOutput>
	 */
	@ApiOperation("新增")
	@PostMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<LevelOutput> create(@RequestBody @Validated LevelAddInput input) {
		CrCommonValidator.validateNameLen(input.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		Level entity = getEntityFromAddInput(input);

		getService().save(entity);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.CREATE, entity.getName(), LogContent.CR_FORMAT
						.format(OperationType.CREATE, LogContent.LEVEL.getMsg(), entity.getName()));

		return retrieve(entity.getResourceId());
	}

	/**
	 * 更新
	 *
	 * @param input 输入
	 * @return {@code BaseRsp<LevelOutput> }
	 * @throws
	 * @see LevelModifyInput
	 * @see BaseRsp<LevelOutput>
	 */
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

	/**
	 * 取回
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<LevelOutput> }
	 * @throws
	 * @see String
	 * @see BaseRsp<LevelOutput>
	 */
	@ApiOperation("查询")
	@GetMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<LevelOutput> retrieve(String resource_id) {
		Level t = getOr404(resource_id);
		return new BaseRsp<>(getOutputFromEntity(t));
	}

	/**
	 * 批量删除
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<String> }
	 * @throws
	 * @see DeleteReq
	 * @see BaseRsp<String>
	 */
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

	/**
	 * 获取Service
	 *
	 * @return {@code LevelService }
	 * @throws
	 * @see LevelService
	 */
	protected LevelService getService() {
		return levelService;
	}

	/**
	 * 获取OR404
	 *
	 * @param resourceId 资源id
	 * @return {@code Level }
	 * @throws
	 * @see String
	 * @see Level
	 */
	protected Level getOr404(String resourceId) {
		return levelService.getOr404(resourceId);
	}

	/**
	 * 获取实体从新增输入
	 *
	 * @param levelAddInput 水平新增输入
	 * @return {@code Level }
	 * @throws
	 * @see LevelAddInput
	 * @see Level
	 */
	protected Level getEntityFromAddInput(LevelAddInput levelAddInput) {
		return getLevelFromInput(levelAddInput, null);
	}

	/**
	 * 获取实体从修改输入
	 *
	 * @param levelModifyInput Level Modify 输入
	 * @param originEntity     源实体
	 * @return {@code Level }
	 * @throws
	 * @see LevelModifyInput
	 * @see Level
	 * @see Level
	 */
	protected Level getEntityFromModifyInput(LevelModifyInput levelModifyInput,
			Level originEntity) {
		Level level = getLevelFromInput(levelModifyInput, originEntity);
		level.setResourceId(levelModifyInput.getResourceId());
		return level;
	}

	/**
	 * 获取水平从输入
	 *
	 * @param input        输入
	 * @param originEntity 源实体
	 * @return {@code Level }
	 * @throws
	 * @see LevelInput
	 * @see Level
	 * @see Level
	 */
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

	/**
	 * 获取输出从实体
	 *
	 * @param level 水平
	 * @return {@code LevelOutput }
	 * @throws
	 * @see Level
	 * @see LevelOutput
	 */
	protected LevelOutput getOutputFromEntity(Level level) {
		LevelOutput output = new LevelOutput();

		output.setResourceId(level.getResourceId());
		output.setName(level.getName());
		output.setDescription(level.getDescription());
		output.setParent(level.getParent());
		output.setFrame(level.getFrame());

		return output;
	}

	/**
	 * 执行批量删除
	 *
	 * @param ids id列表
	 * @throws
	 * @see List<String>
	 */
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

	/**
	 * 检查水平子级
	 *
	 * @param level 水平
	 * @return boolean
	 * @throws
	 * @see Level
	 * @see boolean
	 */// 检查层级的子层级是否有岗位
	private boolean checkLevelChildren(Level level) {
		boolean result = false;
		if (!StringUtils.isBlank(level.getFrame())) {

			LevelTreeOutput frame = frameService.getTree(level.getFrame());// 获取框架树
			LevelTreeOutput levelTreeOutput = getLevelTreeChild(frame, level.getResourceId());// 获取层级树
			result = levelService.checkHasRole(levelTreeOutput);
		}
		return result;

	}

	/**
	 * 获取水平树孩子
	 *
	 * @param frame   框架
	 * @param levelId 水平id
	 * @return {@code LevelTreeOutput }
	 * @throws
	 * @see LevelTreeOutput
	 * @see String
	 * @see LevelTreeOutput
	 */
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
	 *
	 * @param level    水平
	 * @param levelIds 水平id列表
	 * @return {@code String }
	 * @throws
	 * @see Level
	 * @see List<String>
	 * @see String
	 */
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
