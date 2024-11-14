package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.oj.practice.entity.Paper;
import lombok.Data;

import java.util.List;

/**
 * 测试试卷信息
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TestPaperInfo extends Paper {
    /**
     * 关联练习列表
     */
    private List<TestPaperPractice> practiceList;
}
