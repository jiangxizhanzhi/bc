package com.cyberpeace.cr.crbase.service.impl;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cr.crbase.controller.rsp.TrainUserTreeRsp;
import com.cyberpeace.cr.crbase.service.TrainUserService;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TrainUserServiceImpl implements TrainUserService {

    @Autowired
    private RemoteOrganizationService remoteOrganizationService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public List<TrainUserTreeRsp> getOrgUsers() {
        // 全量组织树结构数据
        List<TrainUserTreeRsp> result = remoteOrganizationService.getOrgTree(TrainUserTreeRsp.class);

        // 读取全量用户
        List<UserInfo> userInfoList = remoteUserService.getAllUsers();
        // key: organizationId
        Map<String, List<UserInfo>> userMap = userInfoList.stream().filter(
                userInfo -> StringUtils.isNotEmpty(userInfo.getOrganizationId())
        ).map(
                UserInfo::getOrganizationId
        ).collect(Collectors.toSet()).stream().collect(
                Collectors.toMap(String::toString, string -> Lists.newArrayList())
        );

        userInfoList.forEach(userInfo -> {
            if (StringUtils.isEmpty(userInfo.getOrganizationId())) {
                return;
            }
            userMap.get(userInfo.getOrganizationId()).add(userInfo);
        });

        // 迭代拼接用户至组织数据内
        genOrgData(result, userMap);

        return result;
    }

    private void genOrgData(List<TrainUserTreeRsp> data, Map<String, List<UserInfo>> userMap) {
        for (TrainUserTreeRsp trainUserTreeRsp: data) {
            if (StringUtils.isNotEmpty(trainUserTreeRsp.getAvatar())) {
                trainUserTreeRsp.setType(TrainUserTreeRsp.Type.USER.getCode());
                continue;
            }
            trainUserTreeRsp.setType(TrainUserTreeRsp.Type.ORGANIZATION.getCode());

            List<UserInfo> userInfoList = userMap.get(trainUserTreeRsp.getId());
            if (userInfoList != null) {
                List<TrainUserTreeRsp> trainUserTreeRspList = Lists.newArrayList();
                userInfoList.forEach(userInfo -> {
                    TrainUserTreeRsp trainUserTreeRsp1 = new TrainUserTreeRsp();
                    trainUserTreeRsp1.setId(userInfo.getUserId());
                    trainUserTreeRsp1.setAvatar(userInfo.getAvatarUrl());
                    trainUserTreeRsp1.setName(userInfo.getName());
                    trainUserTreeRspList.add(trainUserTreeRsp1);
                });
                if (trainUserTreeRsp.getChildren() == null) {
                    trainUserTreeRsp.setChildren(trainUserTreeRspList);
                } else {
                    trainUserTreeRsp.getChildren().addAll(trainUserTreeRspList);
                }
            }

            if (trainUserTreeRsp.getChildren() != null) {
                genOrgData(trainUserTreeRsp.getChildren(), userMap);
            }
        }
    }
}
