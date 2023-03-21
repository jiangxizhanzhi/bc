package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.controller.rsp.TrainUserTreeRsp;

import java.util.List;

public interface TrainUserService {
    List<TrainUserTreeRsp> getOrgUsers();
}
