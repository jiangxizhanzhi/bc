package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceCallCount;

/**
 * 调用计数Service
 *
 * @author SN
 * @date 2024/11/13
 */
public interface SpaceCallCountService extends IService<SpaceCallCount> {
    /**
     * 新增计数
     *
     * @param name 名称
     */
    void addCount(String name);

    /**
     * 获取计数由名称
     *
     * @param name 名称
     * @return {@code Integer }
     */
    Integer getCountByName(String name);

    void addCount(String name, int num);
}
