package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.CrTitle;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleTrainProgress;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleUser;
import com.cyberpeace.cr.crbase.service.impl.bo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CrTitleMapper extends BaseMapper<CrTitle> {

    IPage<CrTitleBo> list(@Param("page") Page page, @Param("name") String name);

    IPage<CrTitleBo> listUserTitle(@Param("page") Page page,
                                   @Param("user_id") String userId,
                                   @Param("level_id") String levelId,
                                   @Param("title") String title);

    List<CrTitleBo> listUserTitleByDepartment(@Param("user_ids") List<String> userIds);

    CrTitleUser getUserTitle(@Param("user_id") String userId, @Param("title_id") String titleId);

    IPage<CrTitleStandardBo> listTitleStandard(@Param("page") Page page,
                                               @Param("user_id") String userId,
                                               @Param("title_id") String titleId);

    CrTitleDetailBo titleDetail(@Param("level_id") String levelId,
                                @Param("title") String title);

    IPage<CrTitleTrainByDepartmentBo> listTitleStandards(@Param("page") Page page,
                                                         @Param("user_ids") List<String> userIds,
                                                         @Param("title") String title,
                                                         @Param("level_id") String levelId);

    List<CrTitleTrainRecordBo> listTitleStandardByUsers();

    List<CrTitleDetailBo> titleDetails(@Param("level_id") String levelId,
                                @Param("title") String title);

    int insertScore(@Param("objects") List<CrTitleTrainProgress> objects);

    int getTodayIndex();

    int insertTitleUser(@Param("objects") List<CrTitleUser> objects, @Param("today_index") Integer todayIndex);

    int deleteTitleUser(@Param("title_ids") List<String> titleIds);

    List<CrTitleTrainProgress> listUserTrainSkill(@Param("user_id") String userId);

    List<String> listTitleNameByTrain(@Param("business_template_id") String businessTemplateId);

    List<String> listTitleNameByRbTemplate(@Param("template_id") String templateId);

    List<String> listTitleNameByTeamTemplate(@Param("template_id") String templateId);
}
