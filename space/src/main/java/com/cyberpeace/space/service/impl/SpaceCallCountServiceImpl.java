package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceCallCount;
import com.cyberpeace.space.dao.mapper.SpaceCallCountMapper;
import com.cyberpeace.space.service.SpaceCallCountService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间调用计数Service实现
 *
 * @author SN
 * @date 2024/11/12
 */
@Service
@Slf4j
public class SpaceCallCountServiceImpl extends ServiceImpl<SpaceCallCountMapper, SpaceCallCount> implements SpaceCallCountService {

    /**
     * 新增计数
     *
     * @param name 名称
     */
    @Override
	public void addCount(String name) {
		SpaceCallCount count = this.selectByName(name);
		if (count == null) {
			count = new SpaceCallCount();
			count.setName(name);
			count.setCalls(1);
			this.save(count);
		} else {
			count.setCalls(count.getCalls() + 1);
			this.updateById(count);
		}
	}

	/**
	 * 获取计数由名称
	 *
	 * @param name 名称
	 * @return {@code Integer }
	 */
	@Override
	public Integer getCountByName(String name) {
		SpaceCallCount count = this.selectByName(name);
		if (count == null) {
			return 0;
		}
		return count.getCalls();
	}

	/**
     * 查询由名称
     *
     * @param name 名称
     * @return {@code SpaceCallCount }
     */
    private SpaceCallCount selectByName(String name) {
		return this.lambdaQuery().eq(SpaceCallCount::getName, name).one();
	}
}
