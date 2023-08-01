package com.udacity.jwdnd.course1.cloudstorage.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credential {
    private Integer credentialId;
    private String url;
    private String username;
    private String password;
    private String key;
    private Integer userId;
}
