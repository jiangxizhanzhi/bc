package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.RoomDeviceApplyListRsp;
import com.cyberpeace.cr.crbase.controller.rsp.RoomDeviceOccupyRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrRoomDeviceApply;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoomDeviceApplyMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrRoomDeviceApplyService;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CrRoomDeviceApplyServiceImpl extends ServiceImpl<CrRoomDeviceApplyMapper, CrRoomDeviceApply> implements CrRoomDeviceApplyService {
    @Autowired
    private CrRoleService crRoleService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private RemoteTerminalService remoteTerminalService;

    private CrRoomDeviceApply getObject(String resourceId) {
        CrRoomDeviceApply crRoomDeviceApply = getById(resourceId);
        if (crRoomDeviceApply == null) {
            throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
        }
        return crRoomDeviceApply;
    }

    @Override
    public Boolean apply(String roomId, String roomName, String deviceId) {

        String userId = UserInfoContext.getUserContext().getUserId();
        CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
        if (crRole == null) {
            throw new BusinessException();
        }

        CrRoomDeviceApply crRoomDeviceApply = new CrRoomDeviceApply();

        crRoomDeviceApply.setRoomId(roomId);
        crRoomDeviceApply.setRoomName(roomName);
        crRoomDeviceApply.setDeviceId(deviceId);
        crRoomDeviceApply.setStatus(CrRoomDeviceApply.Status.APPLYING.getCode());
        crRoomDeviceApply.setApplyUserId(userId);
        crRoomDeviceApply.setApplyRoleId(crRole.getResourceId());
        crRoomDeviceApply.setApplyTime(new Date());

        Boolean result = save(crRoomDeviceApply);
        if (result) {
            remoteTerminalService.apply(crRoomDeviceApply.getDeviceId());
        }
        return result;
    }

    @Override
    public RoomDeviceOccupyRsp occupy(String deviceId) {
        CrRoomDeviceApply crRoomDeviceApply = lambdaQuery()
                .eq(CrRoomDeviceApply::getDeviceId, deviceId)
                .eq(CrRoomDeviceApply::getStatus, CrRoomDeviceApply.Status.AGREE.getCode())
                .one();

        if (crRoomDeviceApply == null) {
            return null;
        }

        RoomDeviceOccupyRsp roomDeviceOccupyRsp = new RoomDeviceOccupyRsp();
        BeanUtils.copyProperties(crRoomDeviceApply, roomDeviceOccupyRsp);
        UserInfo userInfo = remoteUserService.getById(roomDeviceOccupyRsp.getApplyUserId());
        roomDeviceOccupyRsp.setApplyUsername(userInfo.getUserName());

        return roomDeviceOccupyRsp;
    }

    @Override
    public ListRsp<RoomDeviceApplyListRsp> list(Page page, String deviceId) {
        IPage<RoomDeviceApplyListRsp> crRoomDeviceApplyIPage = this.getBaseMapper().paging(page, deviceId);

        // 获取用户ID列表
        List<String> userIdList = crRoomDeviceApplyIPage.getRecords().stream().map(
                RoomDeviceApplyListRsp::getApplyUserId
        ).collect(Collectors.toList());

        Map<String, String> usernameMap = remoteUserService.getByIdList(userIdList).stream().collect(
                Collectors.toMap(UserInfo::getUserId, UserInfo::getUserName)
        );
        crRoomDeviceApplyIPage.getRecords().forEach(
                item -> item.setApplyUsername(usernameMap.get(item.getApplyUserId()))
        );


        return PageUtil.getListResult(crRoomDeviceApplyIPage);
    }

    @Override
    public Boolean allow(String applyId) {
        CrRoomDeviceApply crRoomDeviceApply = getObject(applyId);

        String deviceId = crRoomDeviceApply.getDeviceId();
        List<CrRoomDeviceApply> crRoomDeviceApplyList = lambdaQuery().eq(CrRoomDeviceApply::getDeviceId, deviceId).list();
        crRoomDeviceApplyList.forEach(crRoomDeviceApplyItem -> {
            // 单个设备仅供一个角色使用
            if (crRoomDeviceApplyItem.getStatus().intValue() == CrRoomDeviceApply.Status.AGREE.getCode()) {
                throw new BusinessException(CrBusinessErrorCode.DEVICE_OCCUPIED);
            }
        });

        crRoomDeviceApply.setStatus(CrRoomDeviceApply.Status.AGREE.getCode());
        crRoomDeviceApply.setAllowUserId(UserInfoContext.getUserContext().getUserId());
        crRoomDeviceApply.setAllowTime(new Date());

        Boolean result = updateById(crRoomDeviceApply);
        if (result) {
            remoteTerminalService.allot(crRoomDeviceApply.getDeviceId());
        }
        return result;
    }

    @Override
    public Boolean refuse(String applyId) {
        CrRoomDeviceApply crRoomDeviceApply = getObject(applyId);
        crRoomDeviceApply.setStatus(CrRoomDeviceApply.Status.REFUSE.getCode());

        Boolean result = updateById(crRoomDeviceApply);
        if (result) {
            remoteTerminalService.refuse(crRoomDeviceApply.getDeviceId());
        }
        return result;
    }

    @Override
    public Boolean recycle(String roomId, String deviceId) {
        CrRoomDeviceApply crRoomDeviceApply = lambdaQuery()
                .eq(CrRoomDeviceApply::getRoomId, roomId)
                .eq(CrRoomDeviceApply::getDeviceId, deviceId)
                .eq(CrRoomDeviceApply::getStatus, CrRoomDeviceApply.Status.AGREE.getCode())
                .one();

        if (crRoomDeviceApply == null) {
            throw new BusinessException();
        }
        crRoomDeviceApply.setStatus(CrRoomDeviceApply.Status.RECYCLED.getCode());

        Boolean result = updateById(crRoomDeviceApply);
        if (result) {
            remoteTerminalService.recycle(crRoomDeviceApply.getDeviceId());
        }
        return result;
    }
}
