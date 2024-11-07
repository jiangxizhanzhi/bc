package com.cyberpeace.cr.tool.dao.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工具文件Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface ToolFileMapper extends BaseMapper<ToolFile> {

	/**
	 * 查询由id列表
	 *
	 * @param ids id列表
	 * @return {@code List<ToolFile> }
	 */
	List<ToolFile> selectByIds(@NotEmpty @Param("ids") List<Integer> ids);
}
