package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestItemMapper {

    void insert(TestItem testItem);

    void update(TestItem testItem);

    List<TestItem> selectBySchemeId(String schemeId);

    void deleteBySchemeId(String schemeId);

    TestItem selectByItemId(String itemId);
}
