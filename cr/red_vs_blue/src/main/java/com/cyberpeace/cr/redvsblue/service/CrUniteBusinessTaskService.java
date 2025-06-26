package com.cyberpeace.cr.redvsblue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessTask;
import com.cyberpeace.cr.redvsblue.vo.CrUniteBusinessTaskVO;
import com.cyberpeace.cr.redvsblue.vo.NetworkTaskVO;
import com.cyberpeace.cr.redvsblue.vo.NetworkTopoVO;

public interface CrUniteBusinessTaskService extends IService<CrUniteBusinessTask> {

    void saveUniteBusinessTask(NetworkTaskVO networkTaskVO);

    void saveUniteBusinessTopo(NetworkTopoVO networkTopoVO);

    CrUniteBusinessTaskVO getTaskById(String taskId);

    NetworkTopoVO reportBuildResult(NetworkTopoVO networkTopoVO);

    String start(NetworkTopoVO networkTopoVO);

    Boolean stop(NetworkTopoVO networkTopoVO);
}
