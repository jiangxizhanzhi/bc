package com.cyberpeace.cr.tool.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.tool.dao.context.ToolStatistics;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Mapper
@Repository
public interface ToolMapper extends BaseMapper<Tool> {

    IPage<Tool> list(Page page, @Param("listQuery") ToolListQuery toolListQuery, @Param("isAdmin") Boolean isAdmin);

    Tool selectByName(@NotEmpty @Param("name") String name);

    List<Tool> selectByCategory(@Param("resourceId") String resourceId);

    Tool selectOtherByName(@Param("name") String name,@Param("resourceId") String resourceId);

    List<ToolStatistics> statistics();

}
