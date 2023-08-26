package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.Role
 */
@Mapper
@Repository
public interface CrEvalRoleMapper extends BaseMapper<CrEvalRole> {
    void batchInsert(@Param("list") List<CrEvalRole> list);

    List<CrEvalRole> selectCrRoleByRoomId(RoleQuery roleQuery);
}
