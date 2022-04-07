package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.CrTitle;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleBo;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleStandardBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CrTitleMapper extends BaseMapper<CrTitle> {

    IPage<CrTitleBo> list(@Param("page") Page page, @Param("name") String name);

    IPage<CrTitleBo> listUserTitle(@Param("page") Page page,
                                   @Param("user_id") String userId,
                                   @Param("level_id") String levelId,
                                   @Param("title") String title);

    IPage<CrTitleStandardBo> listTitleStandard(@Param("page") Page page,
                                               @Param("user_id") String userId,
                                               @Param("title_id") String titleId);

    CrTitle titleDetail(@Param("level_id") String levelId,
                        @Param("title") String title);
}
