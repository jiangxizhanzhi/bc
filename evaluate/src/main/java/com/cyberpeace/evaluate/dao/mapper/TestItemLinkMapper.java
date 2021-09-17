package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestItemLink;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestItemLinkMapper {

    void insert(TestItemLink testItemLink);

    void update(TestItemLink testItemLink);

    List<TestItemLink> selectBySchemeId(String schemeId);

    void deleteBySchemeId(String schemeId);
}
