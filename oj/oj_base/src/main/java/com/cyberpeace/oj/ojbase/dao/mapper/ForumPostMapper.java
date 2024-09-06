package com.cyberpeace.oj.ojbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.ojbase.dao.entity.ForumPost;
import com.cyberpeace.oj.ojbase.service.bo.post.ForumPostBo;

@Mapper
@Repository
public interface ForumPostMapper extends BaseMapper<ForumPost> {

	IPage<ForumPostBo> page(@Param("page") Page page, @Param("type") Integer type);

	void deleteByIdsAndUserId(@Param("ids") List<String> ids, @Param("userId") String userId,
			@Param("type") Integer type);
}
