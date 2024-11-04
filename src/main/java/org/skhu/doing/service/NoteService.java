package org.skhu.doing.service;

import org.skhu.doing.entity.Note;

import java.util.List;

public interface NoteService {
    Note createNote(Note note);
    Note getNoteById(Long id);
    List<Note> getNotesByFolderId(Long folderId);
    Note updateNote(Long id, Note note);
    void deleteNoteById(Long id);
}
