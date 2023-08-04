package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.Service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.Service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.Util.EncryptionService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    @GetMapping("/home")
    public String homeView(Model model, Authentication authentication) {
        model.addAttribute("files", fileService.getFiles(authentication.getPrincipal().toString()));
        model.addAttribute("notes", noteService.getAllNotes(authentication.getPrincipal().toString()));
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication.getPrincipal().toString()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
}
