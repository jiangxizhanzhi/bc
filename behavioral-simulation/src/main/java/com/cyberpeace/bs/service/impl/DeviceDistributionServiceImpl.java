package com.cyberpeace.bs.service.impl;

import com.cyberpeace.bs.constant.BsMessage;
import com.cyberpeace.bs.dao.mapper.DeviceOccupationMapper;
import com.cyberpeace.bs.service.DeviceDistributionService;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionReq;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionRes;
import com.cyberpeace.bs.service.bo.device.DevicePortId;
import com.cyberpeace.bs.service.bo.device.DeviceTypeNum;
import com.cyberpeace.bs.service.bo.lc.LcActualDevice;
import com.cyberpeace.bs.utils.RandomUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
        List<LcActualDevice> lcActualDeviceList = lcService.getActualDevices(types);
        // 没有查询到设备
        if (CollectionUtils.isEmpty(lcActualDeviceList)) {
            res.setMessage(BsMessage.NO_DEVICES);
            return res;
        }

        Map<Integer, List<LcActualDevice>> deviceMap = StreamApiUtils.groupBy(lcActualDeviceList, LcActualDevice::getSystem);
        Set<String> deviceIds = lcActualDeviceList.stream().map(LcActualDevice::getDeviceId).collect(Collectors.toSet());

        List<String> allTypeSelected = Lists.newArrayList();
        Set<String> occupiedIds = deviceOccupationMapper.checkOccupation(deviceIds);
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
                res.setMessage(String.format("Type %d need %d, get %d", type, num, size));
                return res;
            } else {
                List<LcActualDevice> selected = RandomUtil.randomSelect(deviceList, num);
                List<String> selectedIds = selected.stream().map(LcActualDevice::getDeviceId).collect(Collectors.toList());
                // 分配id
                res.addSelectedIds(type, selectedIds);
                allTypeSelected.addAll(selectedIds);
            }
        }
        // 获取port
        List<DevicePortId> devicePortIds = deviceOccupationMapper.getPortByIds(allTypeSelected);
        Map<String, String> idPortMap = StreamApiUtils.toMap(devicePortIds, DevicePortId::getId, DevicePortId::getPort);
        // 写入port
        res.getDevice().forEach(deviceTypePort -> {
            deviceTypePort.getDeviceList().forEach(devicePortId -> {
                devicePortId.setPort(idPortMap.get(devicePortId.getId()));
            });
        });
        res.setSuccess(true);
        return res;
    }
}
