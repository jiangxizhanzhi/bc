package com.cyberpeace.cr.hld.controller;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.hld.controller.req.HldRoomAddReq;
import com.cyberpeace.cr.hld.controller.req.HldRoomModifyReq;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomMapper;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.HldSceneService;
import com.cyberpeace.cr.hld.service.context.CrRoleConfigHld;
import com.cyberpeace.cr.hld.service.context.HldRoomContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "葫芦岛房间")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/hld/rooms/")
public class HldRoomController {

	@Autowired
	private HldRoomService hldRoomService;

	@Autowired
	private HldRoomMapper hldRoomMapper;

	@Autowired
	private HldSceneService hldSceneService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<String> create(@RequestBody @Validated HldRoomAddReq addReq) {

		CrRoleConfigHld roleConfigHld = BeanUtils.copy(addReq, CrRoleConfigHld.class);

		HldRoomContext hldRoomContext = new HldRoomContext();

		hldRoomContext.setRoleConfig(CrCommonUtil.roleConfig2DbJson(roleConfigHld));
		hldRoomContext.setSceneConfigData(addReq.getSceneConfigData());

		HldRoom hldRoom = hldRoomService.create(hldRoomContext);

		return new BaseRsp<>(hldRoom.getResourceId());
	}

	@ApiOperation("修改")
	@PatchMapping()
	public BaseRsp update(@RequestBody @Validated HldRoomModifyReq modifyReq) {
		String resourceId = modifyReq.getResourceId();
		HldRoom hldRoom = hldRoomService.getById(resourceId);

		if (hldRoom == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		// 已开启的训练不允许修改
		if (!hldRoom.isUnopened()) {
			throw new BusinessException(CrBusinessErrorCode.TRAIN_HAS_BEGUN);
		}

		// update
		CrRoleConfigHld roleConfigHld = BeanUtils.copy(modifyReq, CrRoleConfigHld.class);

		HldRoomContext hldRoomContext = new HldRoomContext();

		hldRoomContext.setRoleConfig(CrCommonUtil.roleConfig2DbJson(roleConfigHld));
		hldRoomContext.setSceneConfigData(modifyReq.getSceneConfigData());

		hldRoomService.update(hldRoomContext, hldRoom);

		// save
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				hldRoomService.updateById(hldRoom);
			}
		});
		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {

		List<HldRoom> hldRooms = hldRoomMapper.selectBatchIds(deleteReq.getIds());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				for (HldRoom hldRoom : hldRooms) {
					hldSceneService.stopScene(hldRoom.getResourceId());
					hldRoomService.removeById(hldRoom.getResourceId());
				}
			}

		});

		return new BaseRsp<>();
	}

}
