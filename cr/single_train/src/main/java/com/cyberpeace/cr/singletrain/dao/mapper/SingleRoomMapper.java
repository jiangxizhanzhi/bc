package com.cyberpeace.cr.singletrain.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoomList;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTrainStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SingleRoomMapper extends BaseMapper<SingleRoom> {

    SingleRoom getSingleRoom(@Param("business_template_id") String businessTemplateId, @Param("user_id") String userId);

    IPage<SingleRoomList> singleTrainSearch(
            @Param("page") Page page,
            @Param("user_id") String userId,
            @Param("pattern") String pattern,
            @Param("category") Integer category,
            @Param("difficult") Integer difficult,
            @Param("business_type") Integer businessType
    );

    List<SingleTrainStats> singleTrainStats(
            @Param("business_template_ids") List<String> businessTemplateIds,
            @Param("date_range") List<String> dateRange,
            @Param("business_type") Integer businessType
    );
}
