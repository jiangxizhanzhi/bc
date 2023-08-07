package com.cyberpeace.cr.redvsblue.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;

public interface EvalRbRoomService extends IService<RbRoom> {

    /**
     * 获取得分趋势信息
     * @param roomId 红蓝对抗实例id
     * @param roleType 角色类型
     * @return 得分趋势信息
     */
    JSONObject getRbScoreProgress(String roomId, Integer roleType);


    StudentScoreRsp getStudentScore(String resource_id, String user_hash);
}
