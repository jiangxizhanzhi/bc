package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.oj.practice.dao.entity.Paper;
import lombok.Data;

import java.util.List;

/**
 * 试卷信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Paper
 * @since 1.0.0
 */
@Data
public class TestPaperInfo extends Paper {
    /**
     * 关联练习列表
     *
     * @see List<TestPaperPractice>
     */
    private List<TestPaperPractice> practiceList;
}
