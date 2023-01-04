package com.cyberpeace.bs.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.dao.entity.UserModel;

@Mapper
@Repository
public interface UserModelMapper extends BaseMapper<UserModel> {

	IPage<UserModel> pageUserBehaviors(@Param("pageUserBehaviors") Page page,
			@Param("userTypeId") String userTypeId, @Param("pattern") String pattern,
			@Param("accessOs") Integer accessOs);
}