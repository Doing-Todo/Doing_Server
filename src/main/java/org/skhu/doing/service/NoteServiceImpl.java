package org.skhu.doing.service;

import org.skhu.doing.entity.Memo;
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
    public Memo createNote(Memo memo) {
        return noteRepository.save(memo);
    }

    @Override
    public Memo getNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
    }

    @Override
    public List<Memo> getNotesByFolderId(Long folderId) {
        return noteRepository.findByFolderId(folderId);
    }

    @Override
    public Memo updateNote(Long id, Memo updatedMemo) {
        Memo existingMemo = getNoteById(id);
        existingMemo.setTitle(updatedMemo.getTitle());
        existingMemo.setContent(updatedMemo.getContent());
        return noteRepository.save(existingMemo);
    }

    @Override
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}
