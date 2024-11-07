package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import com.cyberpeace.rtks.dao.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 任务Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 列出任务
     *
     * @param page    分页
     * @param pattern 模式
     * @return {@code IPage<Task> }
     */
    IPage<Task> listTask(Page page, @Param("pattern") String pattern);

    /**
     * 滤波器id列表
     *
     * @param ids id列表
     * @return {@code List<String> }
     */
    List<String> filterIds(List<String> ids);
}
