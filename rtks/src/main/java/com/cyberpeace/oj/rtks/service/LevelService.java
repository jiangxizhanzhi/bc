package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.rtks.dao.entity.Level;

import java.util.List;

public interface LevelService extends IService<Level> {

    /**
     * 给定一个resource_id，先校验其是否为框架(Frame)，如果不是，抛出异常，如果是则返回该框架
     */
    Level getFrameById(String resource_id);


    /**
     * 获取所有的框架对象
     */
    List<Level> getFrames();


    /**
     * 指定框架id 获取所属所有层级
     */
    List<Level> getLevelsByFrameId(String frameId);

    /**
     * 给定一个层级对象，先校验其parent和frame字段是否合法 再保存
     */
    void addLevel(Level level);

    /**
     * 给定一个层级对象，先校验其parent和frame字段是否合法 再更新
     */
    void modifyLevel(Level level);

    /**
     * 给定层级resource_id, 返回树状层级结构
     */
    Level getLevelTree(String frameId);
}
