package com.cyberpeace.cr.singletrain.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 单模板Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SingleTemplateMapper extends BaseMapper<SingleTemplate> {

    /**
     * 获取由业务id
     *
     * @param trainBusinessId 训练业务id
     * @return {@code SingleTemplate }
     * @throws
     * @see String
     * @see SingleTemplate
     */
    SingleTemplate getByBusinessId(@Param("resource_id") String trainBusinessId);

    /**
     * 批量获取由业务id
     *
     * @param businessTemplateIds 业务模板id列表
     * @return {@code List<SingleTemplate> }
     * @throws
     * @see List<String>
     * @see List<SingleTemplate>
     */
    List<SingleTemplate> batchGetByBusinessId(List<String> businessTemplateIds);

}
