package org.example.testswoyo.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.testswoyo.client.Client;
import org.example.testswoyo.client.service.ClientService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final Client client;
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();


    private String username;
    private Boolean loggedIn = false;
    Scanner scanner = new Scanner(System.in);
    String command = """
            Commands:
            - login -u=username
            - create topic -n=<topic>
            - view options parameters: [-t=<topic>, -t=<topic> -v=<vote>]
            - create vote -t=<topic>
            - vote -t=<topic> -v=<vote>
            - delete -t=topic -v=<vote>
            - exit
            """;
    String helloText = "Welcome to Swoyo client!\n" + command + "Please, login for using commands!";


    public void start(String host, int port) {
        try {
            client.start(host, port);
        } catch (Exception e) {
            System.out.println("Client error..");
        }

        System.out.println(helloText);

        while (true) {
            System.out.println("Enter command:");
            String input = scanner.nextLine();

            Optional<Pattern> patternCommand = CommandValidator.validateCommand(input);
            if (patternCommand.isPresent()) {
                Pattern pattern = patternCommand.get();

                if (pattern.equals(CommandValidator.LOGIN_PATTERN)) {
                    List<String> commandLogin = Arrays.stream(input.split("-u=")).toList();
                    if (loggedIn) {
                        System.out.println("You are already logged in by username: " + username);
                    } else {
                        String username = commandLogin.get(1);
                        String message = clientService.sendLoginRequest(username);
                        client.sendMessage(message);
                        loggedIn = true;
                        this.username = username;
                    }
                } else if (!loggedIn) {
                    System.out.println("You are not logged in, please log in for using commands!");
                }

                if (loggedIn) {

                    if (pattern.equals(CommandValidator.CREATE_TOPIC_PATTERN)) {
                        List<String> commandCreateTopic = Arrays.stream(input.split("-n=")).toList();
                        String topicName = commandCreateTopic.get(1);
                        String createTopicMessage = clientService.createTopic(username, topicName);
                        client.sendMessage(createTopicMessage);
                    }
                    if (pattern.equals(CommandValidator.CREATE_VOTE_PATTERN)) {
                        List<String> commandCreateVote = Arrays.stream(input.split("-t=")).toList();
                        createVote(commandCreateVote.get(1));
                    }
                    if (pattern.equals(CommandValidator.VIEW_PATTERN)) {
                        String viewMessage = clientService.viewAll(username);
                        client.sendMessage(viewMessage);
                    }
                    if (pattern.equals(CommandValidator.VIEW_TOPIC_PATTERN)) {
                        List<String> topicCommand = Arrays.stream(input.split("-t=")).toList();
                        String topic = topicCommand.get(1);
                        String message = clientService.viewTopic(username, topic);
                        client.sendMessage(message);
                    }
                    if (pattern.equals(CommandValidator.VIEW_VOTE_PATTERN)) {
                        List<String> getTopicVote = getTopicVote(input);

                        String message = clientService.viewVotesInTopic(
                                username, getTopicVote.get(0), getTopicVote.get(1));
                        client.sendMessage(message);
                    }
                    if (pattern.equals(CommandValidator.VOTE_PATTERN)) {
                        List<String> getTopicVote = getTopicVote(input);
                        String topic = getTopicVote.get(0);
                        String vote = getTopicVote.get(1);
                        String message = clientService.viewVotesInTopic(username, topic, vote);
                        client.sendMessage(message);
                        CountDownLatch latch = new CountDownLatch(1);
                        executorService.schedule(() -> {
                            System.out.println("Enter the answer:");

                            String answer = scanner.nextLine();
                            if (!answer.isEmpty()) {
                                String messageVoice = clientService.sendAnswer(username, topic, vote, answer);
                                client.sendMessage(messageVoice);
                            } else {
                                System.out.println("Answer cannot be empty. Please, try again.");
                            }

                            latch.countDown();
                        }, 1, TimeUnit.SECONDS);

                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            log.error("Error: ", e);
                        }
                    }
                    if (pattern.equals(CommandValidator.DELETE_PATTERN)) {
                        List<String> topicVote = getTopicVote(input);
                        String messageDelete = clientService.deleteVote(username, topicVote.get(0), topicVote.get(1));
                        client.sendMessage(messageDelete);

                    }
                    if (pattern.equals(CommandValidator.EXIT_PATTERN)) {
                        System.out.println("Exiting...");
                        client.closeConnection();
                        System.exit(0);
                    }
                }
            } else {
                System.out.println("Invalid command, please try again.");
            }
        }
    }


    public void createVote(String topicName) {
        System.out.println("Enter the title of the vote:");
        String voteTitle =
                scanner.nextLine();
        if (voteTitle.isEmpty()) {
            System.out.println("Title cannot be empty. Please, try again.");
            return;
        }
        System.out.println("Enter the description of the vote:");
        String voteDescription = scanner.nextLine();
        if (voteDescription.isEmpty()) {
            System.out.println("Description cannot be empty. Please, try again.");
            return;
        }

        System.out.println("Enter the number of answers:");
        String stringNumberOfAnswers = scanner.nextLine().trim();
        if (stringNumberOfAnswers.isEmpty() || !isNumber(stringNumberOfAnswers)) {
            System.out.println("Number of answers must be a number. Please, try again.");
            return;
        }
        Integer numberOfAnswers = Integer.parseInt(stringNumberOfAnswers);
        if (numberOfAnswers <= 0) {
            System.out.println("Number of answers must be greater than 0. Please, try again.");
            return;
        }

        System.out.println("Enter the answers:");

        List<String> answers = new ArrayList<>();
        for (int i = 0; i < numberOfAnswers; i++) {
            System.out.println("Answer " + (i + 1) + ": ");
            String answer = scanner.nextLine().trim();
            if (!answer.isEmpty()) {
                answers.add(answer);
            } else {
                System.out.println("Answer cannot be empty. Please, try again.");
                i--;
            }
        }

        String message = clientService.createVote(username, topicName,
                voteTitle, voteDescription, numberOfAnswers, answers);
        client.sendMessage(message);

        System.out.println("Vote created successfully.");
    }

    public List<String> getTopicVote(String input){
        List<String> commandDeleteTopic = Arrays.stream(input.split("-t=")).toList();
        String topic = commandDeleteTopic.get(1).split("-v=")[0].trim();
        List<String> commandDeleteVote = Arrays.stream(input.split("-v=")).toList();
        String vote = commandDeleteVote.get(1);
        System.out.println("topic " + topic + " Vote " + vote);
        return Arrays.asList(topic, vote);
    }

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}