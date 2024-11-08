package com.cyberpeace.cr.tool.dao.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.tool.dao.entity.ToolShellCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 工具壳代号Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface ToolShellCodeMapper extends BaseMapper<ToolShellCode> {

	/**
	 * 列表
	 *
	 * @param page        分页
	 * @param author      作者
	 * @param platform    平台
	 * @param edbVerified EDB 验证
	 * @return {@code IPage<ToolShellCode> }
	 */
	IPage<ToolShellCode> list(Page page, @Param("author") String author,
			@Param("platform") String platform, @Param("edbVerified") Boolean edbVerified);
}
