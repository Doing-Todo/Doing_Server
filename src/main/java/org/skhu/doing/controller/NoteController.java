package org.skhu.doing.controller;

import org.skhu.doing.entity.Memo;
import org.skhu.doing.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<Memo> createNote(@RequestBody Memo memo) {
        Memo createdMemo = noteService.createNote(memo);
        return new ResponseEntity<>(createdMemo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Memo> getNoteById(@PathVariable Long id) {
        Memo memo = noteService.getNoteById(id);
        return new ResponseEntity<>(memo, HttpStatus.OK);
    }

    @GetMapping("/folder/{folderId}")
    public ResponseEntity<List<Memo>> getNotesByFolderId(@PathVariable Long folderId) {
        List<Memo> memos = noteService.getNotesByFolderId(folderId);
        return new ResponseEntity<>(memos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Memo> updateNote(@PathVariable Long id, @RequestBody Memo memo) {
        Memo updatedMemo = noteService.updateNote(id, memo);
        return new ResponseEntity<>(updatedMemo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}