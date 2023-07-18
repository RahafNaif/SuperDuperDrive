package com.udacity.jwdnd.course1.cloudstorage.Service;

import com.udacity.jwdnd.course1.cloudstorage.Entity.File;
import com.udacity.jwdnd.course1.cloudstorage.Entity.User;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    private UserService userService;
    private FileMapper fileMapper;

    public void uploadFile(MultipartFile fileToUpload, Model model, String username) throws IOException {
        if (fileToUpload.isEmpty()) {
            model.addAttribute("fileError", "The file you upload is empty, try again");
            return;
        }

        File file = new File();
        User user = userService.getUser(username);

        try {
            file.setFileName(fileToUpload.getOriginalFilename());
            file.setContentType(fileToUpload.getContentType());
            file.setFileSize(Long.toString(fileToUpload.getSize()));
            file.setFileData(fileToUpload.getBytes());
            file.setUserId(user.getUserId());
        } catch (IOException exception) {
            throw exception;
        }

        if(fileMapper.getFile(file.getFileName(), file.getUserId()) != null){
            model.addAttribute("fileError", "The file already uploaded, try again");
            return;
        }

        int fileAdded = fileMapper.insertFile(file);
        if( fileAdded > 0) {
            model.addAttribute("uploadSuccess", true);
            return;
        }
        model.addAttribute("fileError", "There was an error, please try again");
    }
}
