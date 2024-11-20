package com.cyberpeace.integratation.service.bo;

public class TargetListReq {

    private String imageStatus;

    private String category;

    private String parentSystemType;

    private String imageTypeList;

    private String preTypeList;

    private String name;

    private String secondCategoryList;

    public String toQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append("?imageStatus=").append(this.imageStatus);
        builder.append("&category=").append(this.category);
        if (this.parentSystemType != null) {
            builder.append("&parentSystemType=").append(this.parentSystemType);
        }
        if (this.imageTypeList != null) {
            builder.append("&imageTypeList=").append(this.imageTypeList);
        }
        if (this.preTypeList != null) {
            builder.append("&preTypeList=").append(this.preTypeList);
        }
        if (this.name != null) {
            builder.append("&name=").append(this.name);
        }
        if (this.secondCategoryList != null) {
            builder.append("&secondCategoryList=").append(this.secondCategoryList);
        }
        return builder.toString();
    }
}
