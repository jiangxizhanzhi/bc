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
 * @version 1.0.0
 * @date 2024/11/12
 * @see ServiceImpl
 * @see SpaceCallCountService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceCallCountServiceImpl extends ServiceImpl<SpaceCallCountMapper, SpaceCallCount> implements SpaceCallCountService {

	/**
	 * 新增计数
	 *
	 * @param name 名称
	 * @throws
	 * @see String
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
	 * @throws
	 * @see String
	 * @see Integer
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
	 * 新增计数
	 *
	 * @param name 名称
	 * @param num  数量
	 * @throws
	 * @see String
	 * @see int
	 */
	@Override
	public void addCount(String name, int num) {
		SpaceCallCount count = this.selectByName(name);
		if (count == null) {
			count = new SpaceCallCount();
			count.setName(name);
			count.setCalls(num);
			this.save(count);
		} else {
			count.setCalls(count.getCalls() + num);
			this.updateById(count);
		}
	}

	/**
	 * 查询由名称
	 *
	 * @param name 名称
	 * @return {@code SpaceCallCount }
	 * @throws
	 * @see String
	 * @see SpaceCallCount
	 */
	private SpaceCallCount selectByName(String name) {
		return this.lambdaQuery().eq(SpaceCallCount::getName, name).one();
	}
}
