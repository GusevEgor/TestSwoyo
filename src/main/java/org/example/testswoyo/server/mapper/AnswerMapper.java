package org.example.testswoyo.server.mapper;


import org.example.testswoyo.server.dto.request.AnswerRequestS;
import org.example.testswoyo.server.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnswerMapper {
    @Mapping(target = "titleOfAnswer", source = "answerRequest.answer")
    Answer answerFromRequest(AnswerRequestS answerRequest);
}
