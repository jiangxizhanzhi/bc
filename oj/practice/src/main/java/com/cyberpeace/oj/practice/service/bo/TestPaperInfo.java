package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.oj.practice.entity.Paper;
import lombok.Data;

import java.util.List;

@Data
public class TestPaperInfo extends Paper {
    /**
     * 关联练习列表
     */
    private List<TestPaperPractice> practiceList;
}
