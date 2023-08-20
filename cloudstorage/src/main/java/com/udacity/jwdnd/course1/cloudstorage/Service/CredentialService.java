package com.udacity.jwdnd.course1.cloudstorage.Service;

import com.udacity.jwdnd.course1.cloudstorage.Entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.Util.EncryptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserService userService;
    private EncryptionService encryptionService;

    public void createUpdateCredential(Credential credential, String username, Model model){
        credential.setUserId(userService.getUser(username).getUserId());
        String encodedKey = getEncodedKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        if(credentialMapper.getCredential(credential.getCredentialId()) != null) {
            credentialMapper.updateCredential(credential);
            model.addAttribute("resultSuccess", true);
            return;
        }

        if(credentialMapper.getCredentialByUsername(credential.getUsername()) != null){
            model.addAttribute("resultError", "the username already exists");
            return;
        }

        int rowAdded= credentialMapper.insertCredential(credential);
        System.out.println(credential);
        if(rowAdded > 0 ){
            model.addAttribute("resultSuccess", true);
            return;
        }
        model.addAttribute("resultError", "There was an error, please try again");
    }

    private String getEncodedKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encodedKey;
    }

    public List<Credential> getAllCredentials(String username){
        return credentialMapper.getAllCredentials(userService.getUser(username).getUserId());
    }

    public void deleteCredential(Integer credentialId ){
        credentialMapper.deleteCredential(credentialId);
    }
}
