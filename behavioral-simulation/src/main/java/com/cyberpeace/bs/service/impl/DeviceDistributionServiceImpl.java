package com.cyberpeace.bs.service.impl;

import com.cyberpeace.bs.constant.BsMessage;
import com.cyberpeace.bs.dao.entity.DeviceOccupation;
import com.cyberpeace.bs.dao.entity.UserModel;
import com.cyberpeace.bs.dao.mapper.DeviceOccupationMapper;
import com.cyberpeace.bs.service.DeviceDistributionService;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionReq;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionRes;
import com.cyberpeace.bs.service.bo.device.DeviceTypeNum;
import com.cyberpeace.bs.service.bo.lc.LcActualDevice;
import com.cyberpeace.bs.utils.RandomUtil;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceDistributionServiceImpl implements DeviceDistributionService {

    @Autowired
    LcService lcService;

    @Autowired
    DeviceOccupationMapper deviceOccupationMapper;

    @Override
    public DeviceDistributionRes distribute(DeviceDistributionReq req) {
        DeviceDistributionRes res = new DeviceDistributionRes();
        List<Integer> types = req.getRequest().stream().filter(deviceTypeNum -> deviceTypeNum.getNum() > 0)
                .map(DeviceTypeNum::getDeviceType).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(types)) {
            return res;
        }
        List<LcActualDevice> lcActualDeviceList = lcService.getActualDevices(types);
        // 没有查询到设备
        if (CollectionUtils.isEmpty(lcActualDeviceList)) {
            res.addMessage(BsMessage.NO_DEVICES);
            return res;
        }
        Set<String> deviceIds = lcActualDeviceList.stream().map(LcActualDevice::getDeviceId).collect(Collectors.toSet());
        // get devices
        List<DeviceOccupation> deviceOccupationList = deviceOccupationMapper.batchGetByIds(deviceIds);
        // 未知设备
        Set<String> recordedIds = deviceOccupationList.stream().map(DeviceOccupation::getDeviceId).collect(Collectors.toSet());
        Set<String> notRecordedIds = getNotRecorded(deviceIds, recordedIds);
        if (notRecordedIds.size() > 0) {
            res.addMessage(String.format(BsMessage.UNKNOWN_DEVICE, notRecordedIds));
            lcActualDeviceList = lcActualDeviceList.stream().filter(device ->
                    !notRecordedIds.contains(device.getDeviceId())).collect(Collectors.toList());
        }

        Map<Integer, List<LcActualDevice>> deviceMap = StreamApiUtils.groupBy(lcActualDeviceList, LcActualDevice::getSystem);

        // get occupied device ids
        Set<String> occupiedIds = deviceOccupationList.stream().filter(DeviceOccupation::getIsOccupied)
                .map(DeviceOccupation::getDeviceId).collect(Collectors.toSet());

        List<String> allTypeSelected = Lists.newArrayList();
        boolean flag = true;
        for (DeviceTypeNum deviceTypeNum : req.getRequest()) {
            Integer type = deviceTypeNum.getDeviceType();
            List<LcActualDevice> deviceList = deviceMap.getOrDefault(type, Lists.newArrayList());
            int size = deviceList.size();
            int num = deviceTypeNum.getNum();
            List<String> distributed = Lists.newArrayList();
            for (LcActualDevice lcActualDevice : deviceList) {
                if (occupiedIds.contains(lcActualDevice.getDeviceId())) {
                    size--;
                } else {
                    distributed.add(lcActualDevice.getDeviceId());
                }
            }
            // 设备数不足
            if (size < num) {
                res.addMessage(String.format(BsMessage.DEVICE_INSUFFICIENT,
                        EnumUtils.getEnumDescByCode(type, UserModel.AccessOs.class), num, size));
                flag = false;
            } else {
                List<LcActualDevice> selected = RandomUtil.randomSelect(deviceList, num);
                List<String> selectedIds = selected.stream().map(LcActualDevice::getDeviceId).collect(Collectors.toList());
                // 分配id
                res.addSelectedIds(type, selectedIds);
                allTypeSelected.addAll(selectedIds);
            }
        }

        if (flag) {
            // 获取port
            Map<String, String> idPortMap = StreamApiUtils.toMap(deviceOccupationList,
                    DeviceOccupation::getDeviceId, DeviceOccupation::getPort);
            // 写入port
            res.getDevice().forEach(deviceTypePort -> {
                deviceTypePort.getDeviceList().forEach(devicePortId -> {
                    devicePortId.setPort(idPortMap.get(devicePortId.getId()));
                });
            });
            // 设备充足
            res.setSuccess(true);
            // 占用信息写入数据库
            deviceOccupationMapper.occupy(allTypeSelected, req.getSceneId(), new Date());
        }
        return res;
    }

    private Set<String> getNotRecorded(Collection<String> remote, Collection<String> local) {
        Set<String> notRecorded = Sets.newHashSet();
        for (String element : remote) {
            if (!local.contains(element)) {
                notRecorded.add(element);
            }
        }
        return notRecorded;
    }
}
