package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceCallCount;

/**
 * 调用计数Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/13
 * @see IService
 * @since 1.0.0
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

    /**
     * 新增计数
     *
     * @param name 名称
     * @param num  数量
     * @throws
     * @see String
     * @see int
     */
    void addCount(String name, int num);
}
