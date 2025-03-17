package org.example.testswoyo.client.controller;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;


@Component
public class CommandValidator {

    public static final Pattern LOGIN_PATTERN = Pattern.compile("^login\\s+-u=[a-zA-Z0-9_]+$");
    public static final Pattern CREATE_TOPIC_PATTERN = Pattern.compile("^create\\s+topic\\s+-n=[a-zA-Z0-9_]+$");
    public static final Pattern CREATE_VOTE_PATTERN = Pattern.compile("^create\\s+vote\\s+-t=[a-zA-Z0-9_]+$");
    public static final Pattern VIEW_TOPIC_PATTERN = Pattern.compile("^view\\s+-t=[a-zA-Z0-9_]+$");
    public static final Pattern VIEW_VOTE_PATTERN = Pattern.compile("^view\\s-t=[a-zA-Z0-9_]+\\s+-v=[a-zA-Z0-9_]+$");
    public static final Pattern VOTE_PATTERN = Pattern.compile("^vote\\s+-t=[a-zA-Z0-9_]+\\s+-v=[a-zA-Z0-9_]+$");
    public static final Pattern DELETE_PATTERN = Pattern.compile("^delete\\s+-t=[a-zA-Z0-9_]+\\s+-v=[a-zA-Z0-9_]+$");
    public static final Pattern VIEW_PATTERN = Pattern.compile("^view$");
    public static final Pattern EXIT_PATTERN = Pattern.compile("^exit$");


    public static Optional<Pattern> validateCommand(String command) {
        if (LOGIN_PATTERN.matcher(command).matches()) {
            return Optional.of(LOGIN_PATTERN);
        }
        else if (CREATE_TOPIC_PATTERN.matcher(command).matches()) {
            return Optional.of(CREATE_TOPIC_PATTERN);
        }
        else if (VIEW_PATTERN.matcher(command).matches()) {
            return Optional.of(VIEW_PATTERN);
        }
        else if (CREATE_VOTE_PATTERN.matcher(command).matches()) {
            return Optional.of(CREATE_VOTE_PATTERN);
        }
        else if (VIEW_TOPIC_PATTERN.matcher(command).matches()) {
            return Optional.of(VIEW_TOPIC_PATTERN);
        }
        else if (VIEW_VOTE_PATTERN.matcher(command).matches()) {
            return Optional.of(VIEW_VOTE_PATTERN);
        }
        else if (VOTE_PATTERN.matcher(command).matches()) {
            return Optional.of(VOTE_PATTERN);
        }
        else if (DELETE_PATTERN.matcher(command).matches()) {
            return Optional.of(DELETE_PATTERN);
        }
        else if (EXIT_PATTERN.matcher(command).matches()) {
            return Optional.of(EXIT_PATTERN);
        }
        else {
            return Optional.empty();
        }
    }
}