package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbRoomServiceImpl extends ServiceImpl<RbRoomMapper, RbRoom> implements RbRoomService {

	@Autowired
	RbRoomMapper rbRoomMapper;

	@Override
	public List<RbRoom> getByIds(List<String> roomIds) {
		if (roomIds.size() == 0) {
			return Lists.newArrayList();
		}
		return rbRoomMapper.selectBatchIds(roomIds);
	}

	@Override
	public List<RbRoom> getAll() {
		return rbRoomMapper.selectAll();
	}

	@Override
	public Integer countAll() {
		return rbRoomMapper.countAll();
	}
}
