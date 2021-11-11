package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PracticeOperationMapper extends BaseMapper<PracticeOperation> {

	Integer batchDelete(@Param("resource_ids") List<String> resourceIds);

	List<PracticeOperation> batchGet(@Param("resource_ids") List<String> resourceIds);

	IPage<PracticeOperation> pageByQuery(Page page, @Param("query") OperationListQuery listQuery,
			@Param("isPublic") Boolean isPublic);
}
