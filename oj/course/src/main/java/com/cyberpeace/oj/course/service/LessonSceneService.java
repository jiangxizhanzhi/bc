package com.cyberpeace.oj.course.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.AuthContext;

public interface LessonSceneService {

    /**
     * 课时场景启动
     * @param resourceId
     * @param authContext
     * @return
     */
    JSONObject startScene(String resourceId, AuthContext authContext);

    /**
     * 课时场景删除
     * @param resourceId
     * @param authContext
     * @return
     */
    Boolean deleteScene(String resourceId, AuthContext authContext);

    /**
     * 课时场景节点重启
     * @param resourceId
     * @param nodeId
     * @param authContext
     * @return
     */
    Boolean restartNode(String resourceId, String nodeId, AuthContext authContext);

    /**
     * 课时场景节点重建
     * @param resourceId
     * @param nodeId
     * @param authContext
     * @return
     */
    Boolean recreateNode(String resourceId, String nodeId, AuthContext authContext);

    /**
     * 课时场景暂停与恢复
     * @param resourceId
     * @param status
     * @param authContext
     * @return
     */
    Boolean updateSceneStatus(String resourceId, Integer status, AuthContext authContext);
}
