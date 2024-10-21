package com.cyberpeace.cr.tool.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Mapper
@Repository
public interface ToolCategoryMapper extends BaseMapper<ToolCategory> {

	IPage<ToolCategory> list(@Param("page") Page page, @Param("pattern") String pattern, @Param("type") String type);

	List<ToolCategory> selectByResourceIds(@NotEmpty @Param("ids") List<String> ids);

	List<ToolCategory> selectByIds(@NotEmpty @Param("ids") List<Integer> ids);

	ToolCategory getByCnName(@NotEmpty @Param("name") String name, @Param("resourceId") String resourceId);

	ToolCategory getByEnName(@Param("enName") String enName, @Param("resourceId") String resourceId);
}
