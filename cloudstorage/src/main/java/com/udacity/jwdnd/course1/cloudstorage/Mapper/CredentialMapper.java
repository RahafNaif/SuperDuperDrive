package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Entity.Credential;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, password, userId) VALUES (#{url}, #{username}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userId =#{userId}")
    List<Credential> getAllCredentials(Integer userId);

    @Update("UPDATE CREDENTIALS SET username =#{username}, password=#{password} WHERE credentialId =#{credentialId}")
    int updateCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId =#{credentialId}")
    Credential getCredential(Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void deleteCredential(Integer credentialId);
}
