package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleTemplateDetailRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalStepTemplateListRsp;
import lombok.Data;

import java.util.List;

@Data
public class EvalCloudTemplateDumpReq extends EvalCloudTemplateAddReq{
    private List<EvalStepTemplateListRsp> stepList;

    private List<EvalRuleTemplateDetailRsp> ruleList;
}
