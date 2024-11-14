package com.cyberpeace.cr.tool.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工具Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface ToolMapper extends BaseMapper<Tool> {

    /**
     * 列表
     *
     * @param page          分页
     * @param toolListQuery 工具列表查询
     * @param isAdmin       是否管理
     * @return {@code IPage<Tool> }
     */
    IPage<Tool> list(Page<Tool> page, @Param("listQuery") ToolListQuery toolListQuery, @Param("isAdmin") Boolean isAdmin);

    /**
     * 查询由名称
     *
     * @param name 名称
     * @return {@code Tool }
     */
    Tool selectByName(@NotEmpty @Param("name") String name);

    /**
     * 查询由类别
     *
     * @param resourceId 资源id
     * @return {@code List<Tool> }
     */
    List<Tool> selectByCategory(@Param("resourceId") String resourceId);

    /**
     * 查询其他由名称
     *
     * @param name       名称
     * @param resourceId 资源id
     * @return {@code Tool }
     */
    Tool selectOtherByName(@Param("name") String name,@Param("resourceId") String resourceId);

}
