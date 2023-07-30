package com.udacity.jwdnd.course1.cloudstorage.Service;

import com.udacity.jwdnd.course1.cloudstorage.Entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {
    private NoteMapper noteMapper;
    private UserService userService;

    public void createUpdateNote(Note note, Model model, String username){
        note.setUserId(userService.getUser(username).getUserId());
        if (noteMapper.getNote(note.getNoteId()) != null) {
            updateNote(note);
            model.addAttribute("success", true);
            return;
        }

        if (noteMapper.getNoteByTitle(note.getNoteTitle()) != null){
            model.addAttribute("resultError", "the note title already exist");
            return;
        }

        int addedNote= noteMapper.insertNote(note);
        if (addedNote > 0 ){
            model.addAttribute("success", true);
            return;
        }
        model.addAttribute("resultError", "There was an error, please try again");
    }

    public List<Note> getAllNotes(String username){
        return noteMapper.getAllNotes(userService.getUser(username).getUserId());
    }

    public void updateNote(Note note){
        noteMapper.updateNote(note);
    }

    public void deleteNote(Integer noteId){
        noteMapper.deleteNote(noteId);
    }
}
