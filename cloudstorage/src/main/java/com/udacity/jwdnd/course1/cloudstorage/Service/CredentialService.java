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

//    public String decryptPassword(String password, String key){
//        System.out.println(password);
//        System.out.println(key);
//        String decryptedPassword = encryptionService.decryptValue(password, key);
//        return decryptedPassword;
//    }
//    Credential(credentialId=1, url=htt, username=ergterter, password=0Vkg/8bIVduyAhVKE4k4pQ==, key=F8o2v2aPsdqgu6eMol+4Gw==, userId=3)
}
