package com.cyberpeace.oj.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.rtks.dao.entity.Task;

@Mapper
@Repository
public interface TaskMapper extends BaseMapper<Task> {

	IPage<Task> listTask(Page page, @Param("pattern") String pattern);

    List<String> filterIds(List<String> ids);
}
