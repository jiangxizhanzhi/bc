package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestItemIndexLink;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestItemIndexLinkMapper {

    void insert(TestItemIndexLink testItemIndexLink);

    void update(TestItemIndexLink testItemIndexLink);

    List<TestItemIndexLink> selectByItemId(String itemId);

    void batchDelete(List<String> ids);
}
