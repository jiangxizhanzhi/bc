package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.oj.practice.dao.entity.TestPaper;
import lombok.Data;

import java.util.List;

@Data
public class TestPaperInfo extends TestPaper {
    /**
     * 关联练习列表
     */
    private List<TestPaperPractice> practiceList;
}
