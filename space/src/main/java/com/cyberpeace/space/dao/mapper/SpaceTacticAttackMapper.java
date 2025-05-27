package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpaceTacticAttackMapper extends BaseMapper<SpaceTacticAttack> {

     List<String> idsByCategoryids(@Param("ids") List<String> ids) ;
}
