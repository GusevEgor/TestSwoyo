package org.example.testswoyo.server.mapper;

import org.example.testswoyo.server.dto.request.CreateVoteRequestS;
import org.example.testswoyo.server.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VoteMapper {

    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "title", source = "create.voteTitle")
    @Mapping(target = "description", source = "create.voteDescription")
    Vote voteFromRequest(CreateVoteRequestS create );


}
