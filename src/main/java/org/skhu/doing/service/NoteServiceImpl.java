package org.skhu.doing.service;

import org.skhu.doing.entity.Note;
import org.skhu.doing.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
    }

    @Override
    public List<Note> getNotesByFolderId(Long folderId) {
        return noteRepository.findByFolderId(folderId);
    }

    @Override
    public Note updateNote(Long id, Note updatedNote) {
        Note existingNote = getNoteById(id);
        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());
        return noteRepository.save(existingNote);
    }

    @Override
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}
