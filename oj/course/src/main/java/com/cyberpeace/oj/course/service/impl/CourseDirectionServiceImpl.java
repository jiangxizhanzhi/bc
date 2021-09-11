package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.oj.course.dao.oj_entity.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.oj.course.dao.mapper.DirectionMapper;
import com.cyberpeace.oj.course.service.CourseDirectionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseDirectionServiceImpl implements CourseDirectionService {

	@Autowired
	private DirectionMapper directionMapper;

	@Override
	public List<String> getAllChildrenIds(String directionId) {
		List<Direction> directions = directionMapper.selectChirdlentByCode(directionId);
		return directions.stream().map(Direction::getResourceId).collect(Collectors.toList());
	}
}
