package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.Service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class NoteController {
    private NoteService noteService;

    @PostMapping("/add-edit-note")
    public String createUpdateNote(@ModelAttribute("note") Note note, Model model, Authentication authentication){
        noteService.createUpdateNote(note, model, authentication.getPrincipal().toString());
        return "result";
    }

    @GetMapping("/delete-note")
    public String deleteNote(@RequestParam("noteId") Integer noteId, Model model){
        noteService.deleteNote(noteId);
        model.addAttribute("success",true);
        return "result";
    }
}
