package com.cyberpeace.oj.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.rtks.dao.entity.RoleRelated;

@Mapper
@Repository
public interface RoleRelatedMapper extends BaseMapper<RoleRelated> {

	List<RoleRelated> listRoleRelated(String roleId, int type);
}
