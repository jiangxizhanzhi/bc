package com.cyberpeace.situation.dao.mapper;


 import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SituationBusinessComponentCategory;

 import java.util.List;


/**
 * 业务组件类别映射器
 *
 * @author dell
 * @date 2023/06/20
 */
public interface SituationBusinessComponentCategoryMapper extends BaseMapper<SituationBusinessComponentCategory> {

    /**
     * 查找所有
     *
     * @return {@link List}<{@link SituationBusinessComponentCategory}>
     */
    List<SituationBusinessComponentCategory> selectAll();

    /**
     * 查找名字
     *
     * @param name 名字
     * @return {@link String}
     */
    String selectByName(String name);
}




