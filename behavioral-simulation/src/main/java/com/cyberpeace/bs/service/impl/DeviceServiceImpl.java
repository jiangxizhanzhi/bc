package com.cyberpeace.bs.service.impl;

import com.cyberpeace.bs.constant.BsMessage;
import com.cyberpeace.bs.dao.entity.ActualDevice;
import com.cyberpeace.bs.dao.entity.UserModel;
import com.cyberpeace.bs.dao.mapper.ActualDeviceMapper;
import com.cyberpeace.bs.dao.mapper.DeviceOccupationMapper;
import com.cyberpeace.bs.service.DeviceService;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.device.DeviceAddReq;
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
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    LcService lcService;

    @Autowired
    DeviceOccupationMapper deviceOccupationMapper;

    @Autowired
    ActualDeviceMapper actualDeviceMapper;

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
        List<ActualDevice> actualDeviceList = actualDeviceMapper.selectAll();
        // 未知设备
        Set<String> recordedIds = actualDeviceList.stream().map(ActualDevice::getDeviceId).collect(Collectors.toSet());
        Set<String> notRecordedIds = getNotRecorded(deviceIds, recordedIds);
        if (notRecordedIds.size() > 0) {
            res.addMessage(String.format(BsMessage.UNKNOWN_DEVICE, notRecordedIds));
            lcActualDeviceList = lcActualDeviceList.stream().filter(device ->
                    !notRecordedIds.contains(device.getDeviceId())).collect(Collectors.toList());
        }

        Map<Integer, List<LcActualDevice>> deviceMap = StreamApiUtils.groupBy(lcActualDeviceList, LcActualDevice::getSystem);

        // get occupied device ids
        Set<String> occupiedIds = deviceOccupationMapper.checkOccupation(deviceIds);

        List<String> allTypeSelected = Lists.newArrayList();
        boolean flag = true;
        for (DeviceTypeNum deviceTypeNum : req.getRequest()) {
            Integer type = deviceTypeNum.getDeviceType();
            List<LcActualDevice> deviceList = deviceMap.getOrDefault(type, Lists.newArrayList());
            int size = deviceList.size();
            int num = deviceTypeNum.getNum();
            List<String> toDistribute = Lists.newArrayList();
            for (LcActualDevice lcActualDevice : deviceList) {
                if (occupiedIds.contains(lcActualDevice.getDeviceId())) {
                    size--;
                } else {
                    toDistribute.add(lcActualDevice.getDeviceId());
                }
            }
            // 设备数不足
            if (size < num) {
                res.addMessage(String.format(BsMessage.DEVICE_INSUFFICIENT,
                        EnumUtils.getEnumDescByCode(type, UserModel.AccessOs.class), num, size));
                flag = false;
            } else {
                List<String> selectedIds = RandomUtil.randomSelect(toDistribute, num);
                // 分配id
                res.addSelectedIds(type, selectedIds);
                allTypeSelected.addAll(selectedIds);
            }
        }

        if (flag) {
            // 获取port
            Map<String, String> idPortMap = StreamApiUtils.toMap(actualDeviceList,
                    ActualDevice::getDeviceId, ActualDevice::getPort);
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

    @Override
    public void addDevice(DeviceAddReq req) {
        ActualDevice actualDevice = new ActualDevice();
        actualDevice.setDeviceId(req.getDeviceId());
        actualDevice.setDeviceType(req.getDeviceType());
        actualDevice.setDeviceName(req.getDeviceName());
        actualDevice.setPort(req.getPort());
        actualDevice.setCreateTime(new Date());
        actualDeviceMapper.insert(actualDevice);
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
