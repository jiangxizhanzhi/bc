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
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface ToolMapper extends BaseMapper<Tool> {

    /**
     * 列表
     *
     * @param page          分页
     * @param toolListQuery 工具列表查询
     * @param isAdmin       是管理员
     * @return {@code IPage<Tool> }
     * @throws
     * @see Page
     * @see ToolListQuery
     * @see Boolean
     * @see IPage<Tool>
     */
    IPage<Tool> list(Page page, @Param("listQuery") ToolListQuery toolListQuery, @Param("isAdmin") Boolean isAdmin);

    /**
     * 查询由名称
     *
     * @param name 名称
     * @return {@code Tool }
     * @throws
     * @see String
     * @see Tool
     */
    Tool selectByName(@NotEmpty @Param("name") String name);

    /**
     * 查询由类别
     *
     * @param resourceId 资源id
     * @return {@code List<Tool> }
     * @throws
     * @see String
     * @see List<Tool>
     */
    List<Tool> selectByCategory(@Param("resourceId") String resourceId);

    /**
     * 查询其他由名称
     *
     * @param name       名称
     * @param resourceId 资源id
     * @return {@code Tool }
     * @throws
     * @see String
     * @see String
     * @see Tool
     */
    Tool selectOtherByName(@Param("name") String name,@Param("resourceId") String resourceId);

}
