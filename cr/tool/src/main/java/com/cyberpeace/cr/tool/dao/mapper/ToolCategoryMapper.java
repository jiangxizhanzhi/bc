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

/**
 * 工具类别Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface ToolCategoryMapper extends BaseMapper<ToolCategory> {

	/**
	 * 列表
	 *
	 * @param page    分页
	 * @param pattern 模式
	 * @param type    类型
	 * @return {@code IPage<ToolCategory> }
	 */
	IPage<ToolCategory> list(@Param("page") Page page, @Param("pattern") String pattern, @Param("type") String type);

	/**
	 * 查询由资源id列表
	 *
	 * @param ids id列表
	 * @return {@code List<ToolCategory> }
	 */
	List<ToolCategory> selectByResourceIds(@NotEmpty @Param("ids") List<String> ids);

	/**
	 * 查询由id列表
	 *
	 * @param ids id列表
	 * @return {@code List<ToolCategory> }
	 */
	List<ToolCategory> selectByIds(@NotEmpty @Param("ids") List<Integer> ids);

	/**
	 * 获取由中文名称
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return {@code ToolCategory }
	 */
	ToolCategory getByCnName(@NotEmpty @Param("name") String name, @Param("resourceId") String resourceId);
	List<ToolCategory> getByType(@NotEmpty @Param("type") String type);

	/**
	 * 获取由中文名称
	 *
	 * @param enName     中文名称
	 * @param resourceId 资源id
	 * @return {@code ToolCategory }
	 */
	ToolCategory getByEnName(@Param("enName") String enName, @Param("resourceId") String resourceId);
}
