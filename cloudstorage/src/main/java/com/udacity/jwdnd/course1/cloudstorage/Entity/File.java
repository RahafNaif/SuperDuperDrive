package com.udacity.jwdnd.course1.cloudstorage.Entity;

import lombok.Data;

@Data
public class File {
    private Integer fileId;
    private String fileName;
    private Integer userId;
}
