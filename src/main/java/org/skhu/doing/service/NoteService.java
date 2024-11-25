package org.skhu.doing.service;

import org.skhu.doing.entity.Memo;

import java.util.List;

public interface NoteService {
    Memo createNote(Memo memo);
    Memo getNoteById(Long id);
    List<Memo> getNotesByFolderId(Long folderId);
    Memo updateNote(Long id, Memo memo);
    void deleteNoteById(Long id);
}
