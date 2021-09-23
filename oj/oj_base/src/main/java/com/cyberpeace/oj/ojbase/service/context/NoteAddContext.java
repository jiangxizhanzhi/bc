package com.cyberpeace.oj.ojbase.service.context;

import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;

import lombok.Data;

@Data
public class NoteAddContext extends NoteUniqueContext {

    private String resourceId;

    private String content;

}
