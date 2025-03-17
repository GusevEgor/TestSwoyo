package org.example.testswoyo.server.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.testswoyo.server.dto.request.CreateTopicRequest;
import org.example.testswoyo.server.dto.request.ViewTopicRequest;
import org.example.testswoyo.server.dto.response.AllTopicResponse;
import org.example.testswoyo.server.dto.response.SmallTopicResponse;
import org.example.testswoyo.server.dto.response.TopicResponse;
import org.example.testswoyo.server.entity.Topic;
import org.example.testswoyo.server.entity.User;
import org.example.testswoyo.server.entity.Vote;
import org.example.testswoyo.server.mapper.TopicMapper;
import org.example.testswoyo.server.repository.TopicRepository;
import org.example.testswoyo.server.utils.ServerGlobalResponseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;
import java.util.stream.Collectors;

class TopicServiceTest {

    @Mock
    private TopicMapper topicMapper;

    @Mock
    private UserService userService;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private ServerGlobalResponseHandler serverGlobalResponseHandler;

    @InjectMocks
    private TopicService topicService;

    private CreateTopicRequest createTopicRequest;
    private Topic topic;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createTopicRequest = Mockito.mock(CreateTopicRequest.class);
        createTopicRequest.setTitle("testTopic");
        createTopicRequest.setUsername("testUser");
        when(createTopicRequest.getTitle()).thenReturn("testTopic");
        when(createTopicRequest.getUsername()).thenReturn("testUser");

        topic = Mockito.mock(Topic.class);
        topic.setTitle("testTopic");
        when(topic.getTitle()).thenReturn("testTopic");

        user = Mockito.mock(User.class);
        user.setName("testUser");
        when(user.getName()).thenReturn("testUser");
    }

    @Test
    void testCreateTopic_TopicAlreadyExists() {
        // Настроим мок для проверки существования темы
        when(topicRepository.existsByTitle(createTopicRequest.getTitle())).thenReturn(true);

        // Вызов метода
        String result = topicService.createTopic(createTopicRequest);

        verify(serverGlobalResponseHandler).sendBadRequest("Topic testTopic already exists, title must be unique");
    }

    @Test
    void testCreateTopic_UserDoesNotExist() {
        // Настроим мок для того, чтобы пользователь не существовал
        when(topicRepository.existsByTitle(createTopicRequest.getTitle())).thenReturn(false);
        when(userService.checkUserExists(createTopicRequest.getUsername())).thenReturn(false);

        // Вызов метода
        String result = topicService.createTopic(createTopicRequest);

        // Проверка взаимодействий с моком
        verify(userService).checkUserExists(createTopicRequest.getUsername());
        verify(serverGlobalResponseHandler).sendBadRequest("User does not exist");
    }

    @Test
    void testCreateTopic_Success() {
        // Настроим моки для того, чтобы тема не существовала и пользователь существовал
        when(topicRepository.existsByTitle(createTopicRequest.getTitle())).thenReturn(false);
        when(userService.checkUserExists(createTopicRequest.getUsername())).thenReturn(true);
        when(topicMapper.convertFromCreateTopicRequestToEntity(createTopicRequest)).thenReturn(topic);

        // Настроим мок для сохранения темы
        when(topicRepository.save(topic)).thenReturn(topic);
        when(serverGlobalResponseHandler.success("Create topic success")).thenReturn("Create topic success");

        // Вызов метода
        String result = topicService.createTopic(createTopicRequest);

        // Проверка взаимодействий с моком
        verify(topicRepository).save(topic);
        verify(serverGlobalResponseHandler).success("Create topic success");
    }

    @Test
    void testViewAllTopic_Success() {
        Vote vote = Mockito.mock(Vote.class);
        topic.setVotes(List.of(vote));

        Topic topic2 = Mockito.mock(Topic.class);
        topic2.setTitle("Topic2");
        when(topic2.getTitle()).thenReturn("Topic2");
        Vote vote2 = Mockito.mock(Vote.class);

        topic2.setVotes(List.of(vote2));

        List<Topic> topics = Arrays.asList(topic, topic2);

        AllTopicResponse allTopicResponse = new AllTopicResponse(
                topics.stream()
                        .map(t -> new SmallTopicResponse(t.getTitle(), t.getVotes().size()))
                        .toList());

        // Настроим мок для получения всех тем
        when(topicRepository.findAll()).thenReturn(topics);
        when(serverGlobalResponseHandler.sendAllTopic(any(AllTopicResponse.class))).thenReturn("All topics retrieved");

        // Вызов метода
        String result = topicService.viewAllTopic();

        // Проверка результата
        assertEquals("All topics retrieved", result);

        // Проверка взаимодействий с моком
        verify(topicRepository).findAll();
        verify(serverGlobalResponseHandler).sendAllTopic(any(AllTopicResponse.class));
    }


    @Test
    void testViewTopic_TopicDoesNotExist() {
        ViewTopicRequest viewTopicRequest = new ViewTopicRequest();
        viewTopicRequest.setTopicTitle("nonExistingTopic");

        // Настроим мок для того, чтобы тема не существовала
        when(topicRepository.existsByTitle(viewTopicRequest.getTopicTitle())).thenReturn(false);

        // Вызов метода
        String result = topicService.viewTopic(viewTopicRequest);

        // Проверка взаимодействий с моком
        verify(topicRepository).existsByTitle(viewTopicRequest.getTopicTitle());
        verify(serverGlobalResponseHandler).sendBadRequest("Topic does not exist");
    }

    @Test
    void testViewTopic_Success() {
        ViewTopicRequest viewTopicRequest = Mockito.mock(ViewTopicRequest.class);
        viewTopicRequest.setTopicTitle("testTopic");
        when(viewTopicRequest.getTopicTitle()).thenReturn("testTopic");

        Topic topic = Mockito.mock(Topic.class);
        topic.setTitle("testTopic");
        when(topic.getTitle()).thenReturn("testTopic");
        List<Vote> votes = Arrays.asList(Mockito.mock(Vote.class), Mockito.mock(Vote.class));
        topic.setVotes(votes);

        TopicResponse topicResponse = new TopicResponse(topic.getTitle(), votes.stream().map(Vote::getTitle).collect(Collectors.toList()));

        // Настроим моки
        when(topicRepository.existsByTitle(viewTopicRequest.getTopicTitle())).thenReturn(true);
        when(topicRepository.findByTitle(viewTopicRequest.getTopicTitle())).thenReturn(topic);
        when(serverGlobalResponseHandler.sendTopic(any(TopicResponse.class))).thenReturn("Topic retrieved successfully");

        // Вызов метода
        String result = topicService.viewTopic(viewTopicRequest);

        // Проверка результата
        assertEquals("Topic retrieved successfully", result);

        // Проверка взаимодействий с моком
        verify(topicRepository).findByTitle(viewTopicRequest.getTopicTitle());
        verify(serverGlobalResponseHandler).sendTopic(any(TopicResponse.class));
    }
}