package com.cyberpeace.cr.evaluation.dao.mapper.rb;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalScore;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndex
 */
public interface CrEvalScoreMapper extends BaseMapper<CrEvalScore> {

    CrEvalScore getByRoomIdAndUser(@Param("roomId") String roomId,@Param("userId") String userId);
}
