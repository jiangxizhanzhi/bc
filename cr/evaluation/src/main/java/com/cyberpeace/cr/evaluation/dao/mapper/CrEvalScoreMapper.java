package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalScore;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndex
 */
@Mapper
@Repository
public interface CrEvalScoreMapper extends BaseMapper<CrEvalScore> {

    void batchInsert(@Param("list") List<CrEvalScore> list);

    CrEvalScore getByRoomIdAndUser(@Param("roomId") String roomId,@Param("userId") String userId);
}
