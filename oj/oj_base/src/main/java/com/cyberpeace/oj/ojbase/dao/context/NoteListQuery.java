package com.cyberpeace.oj.ojbase.dao.context;

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
public class NoteListQuery implements Serializable {

    private List<String> userIds;

    private Integer noteType;

    private Integer businessType;

    private String pattern;

    private String businessId;

}
