package com.majiang.mgtest.mapper;

import com.majiang.mgtest.model.Question;
import org.springframework.stereotype.Component;

@Component
public interface QuestionMapper {
    void create(Question question);
}
