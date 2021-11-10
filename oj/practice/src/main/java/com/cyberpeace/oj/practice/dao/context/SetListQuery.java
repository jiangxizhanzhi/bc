package com.cyberpeace.oj.practice.dao.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetListQuery implements Serializable {

    private List<String> setIds;

    private Integer practiceType;

    private String pattern;

}
