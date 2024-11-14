package com.cyberpeace.oj.practice.service.externalbo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 外部理论计数Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ExternalTheoryCountBo implements Serializable {
    
    public ExternalTheoryCountBo() {
        this.single = 0;
        this.multiple = 0;
        this.judgement = 0;
        this.fillBlank = 0;
        this.shortAnswer = 0;
    }
    
    public ExternalTheoryCountBo(Map<Integer, List<TheorySimpleBo>> typeMap) {
        initSingle(typeMap);
        initMultiple(typeMap);
        initJudgement(typeMap);
        initFillBlank(typeMap);
        initShortAnswer(typeMap);
    }
    
	private void initShortAnswer(Map<Integer, List<TheorySimpleBo>> typeMap) {
        this.shortAnswer = typeMap
				.getOrDefault(PracticeTheory.TheoryType.SHORT_ANSWER.getCode(), new ArrayList<>())
				.size();
	}
    
    private void initFillBlank(Map<Integer, List<TheorySimpleBo>> typeMap) {
        this.fillBlank = typeMap
                .getOrDefault(PracticeTheory.TheoryType.FILL_BLANK.getCode(), new ArrayList<>())
                .size();
    }
    
	private void initJudgement(Map<Integer, List<TheorySimpleBo>> typeMap) {
        this.judgement = typeMap
				.getOrDefault(PracticeTheory.TheoryType.JUDGMENT.getCode(), new ArrayList<>())
				.size();
	}
    
	private void initMultiple(Map<Integer, List<TheorySimpleBo>> typeMap) {
        this.multiple = typeMap
				.getOrDefault(PracticeTheory.TheoryType.MULTIPLE.getCode(), new ArrayList<>())
				.size();
	}
    
	private void initSingle(Map<Integer, List<TheorySimpleBo>> typeMap) {
        this.single = typeMap
				.getOrDefault(PracticeTheory.TheoryType.SINGLE.getCode(), Lists.newArrayList())
				.size();
	}

    /**
     * 单选题数量
     */
    @ApiModelProperty("单选题数量")
    private Integer single;

    /**
     * 多选题数量
     */
    @ApiModelProperty("多选题数量")
    private Integer multiple;

    /**
     * 判断题数量
     */
    @ApiModelProperty("判断题数量")
    private Integer judgement;

    /**
     * 填空题数量
     */
    @ApiModelProperty("填空题数量")
    private Integer fillBlank;

    /**
     * 简答题数量
     */
    @ApiModelProperty("简答题数量")
    private Integer shortAnswer;
    
    
}
