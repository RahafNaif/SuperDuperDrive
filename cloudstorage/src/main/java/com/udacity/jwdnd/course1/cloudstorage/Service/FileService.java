package com.udacity.jwdnd.course1.cloudstorage.Service;

import com.udacity.jwdnd.course1.cloudstorage.Entity.File;
import com.udacity.jwdnd.course1.cloudstorage.Entity.User;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
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

        if(fileMapper.getFileByNameAndUserId(file.getFileName(), file.getUserId()) != null){
            model.addAttribute("resultError", "The file already uploaded, try again");
            return;
        }

        int fileAdded = fileMapper.insertFile(file);
        if( fileAdded > 0) {
            model.addAttribute("resultSuccess", true);
            return;
        }
        model.addAttribute("resultError", "There was an error, please try again");
    }

    public List<File> getFiles(String username) {
        Integer userId = userService.getUser(username).getUserId();
        return fileMapper.getAllFiles(userId);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFileById(fileId);
    }
}
