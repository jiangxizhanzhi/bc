package com.cyberpeace.cr.tool.dao.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Mapper
@Repository
public interface ToolFileMapper extends BaseMapper<ToolFile> {

	List<ToolFile> selectByIds(@NotEmpty @Param("ids") List<Integer> ids);

	void batchInsert(@Param("list") List<ToolFile> list);
}
