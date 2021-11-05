package com.cyberpeace.oj.ojbase.dao.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NoteListQuery implements Serializable {

    private List<String> userIds;

    private Integer noteType;

    private Integer businessType;

    private String pattern;

}
