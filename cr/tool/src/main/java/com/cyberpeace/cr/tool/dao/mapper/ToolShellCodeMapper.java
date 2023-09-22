package com.cyberpeace.cr.tool.dao.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.tool.dao.entity.ToolShellCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ToolShellCodeMapper extends BaseMapper<ToolShellCode> {

	IPage<ToolShellCode> list(Page<ToolShellCode> page, @Param("author") String author,
			@Param("platform") String platform, @Param("edbVerified") Boolean edbVerified);
}
