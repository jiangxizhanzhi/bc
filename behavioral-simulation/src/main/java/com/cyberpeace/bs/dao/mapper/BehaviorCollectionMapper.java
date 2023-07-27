package com.cyberpeace.bs.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.dao.entity.BehaviorCollection;

@Mapper
@Repository
public interface BehaviorCollectionMapper extends BaseMapper<BehaviorCollection> {

	IPage<BehaviorCollection> pageCollection(@Param("page") Page page,
			@Param("accessOs") Integer accessOs, @Param("pattern") String pattern);

	boolean existByName(@Param("name") String name, @Param("resourceId") String resourceId);
}
