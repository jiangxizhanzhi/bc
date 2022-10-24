package com.cyberpeace.oj.practice.service.externalBo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
		int shortAnswer = typeMap
				.getOrDefault(PracticeTheory.TheoryType.SHORT_ANSWER.getCode(), new ArrayList<>())
				.size();
		this.shortAnswer = shortAnswer;
	}
    
    private void initFillBlank(Map<Integer, List<TheorySimpleBo>> typeMap) {
        int fillBlank = typeMap
                .getOrDefault(PracticeTheory.TheoryType.FILL_BLANK.getCode(), new ArrayList<>())
                .size();
        this.fillBlank = fillBlank;
    }
    
	private void initJudgement(Map<Integer, List<TheorySimpleBo>> typeMap) {
		int judgement = typeMap
				.getOrDefault(PracticeTheory.TheoryType.JUDGMENT.getCode(), new ArrayList<>())
				.size();
		this.judgement = judgement;
	}
    
	private void initMultiple(Map<Integer, List<TheorySimpleBo>> typeMap) {
		int multiple = typeMap
				.getOrDefault(PracticeTheory.TheoryType.MULTIPLE.getCode(), new ArrayList<>())
				.size();
		this.multiple = multiple;
	}
    
	private void initSingle(Map<Integer, List<TheorySimpleBo>> typeMap) {
		int single = typeMap
				.getOrDefault(PracticeTheory.TheoryType.SINGLE.getCode(), Lists.newArrayList())
				.size();
		this.single = single;
	}
    
    @ApiModelProperty("单选题数量")
    private Integer single;

    @ApiModelProperty("多选题数量")
    private Integer multiple;

    @ApiModelProperty("判断题数量")
    private Integer judgement;

    @ApiModelProperty("填空题数量")
    private Integer fillBlank;

    @ApiModelProperty("简答题数量")
    private Integer shortAnswer;
    
    
}
