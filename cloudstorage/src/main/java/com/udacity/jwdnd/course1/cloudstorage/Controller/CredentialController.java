package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Service.CredentialService;
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
public class CredentialController {
    private CredentialService credentialService;

    @PostMapping("/add-edit-credential")
    public String addUpdateCredential(@ModelAttribute("credential") Credential credential, Authentication authentication, Model model){
        credentialService.createUpdateCredential(credential, authentication.getPrincipal().toString(), model);
        return "result";
    }

    @GetMapping("/delete-credential")
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId, Model model){
        credentialService.deleteCredential(credentialId);
        model.addAttribute("resultSuccess",true);
        return "result";
    }
}
