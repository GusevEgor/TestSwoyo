package org.example.testswoyo.server.mapper;

import org.example.testswoyo.server.dto.request.CreateTopicRequest;
import org.example.testswoyo.server.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TopicMapper {
    Topic convertFromCreateTopicRequestToEntity(CreateTopicRequest createTopicRequest);
}
