package com.udacity.jwdnd.course1.cloudstorage.Service;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoteService {
    private NoteMapper noteMapper;
}
