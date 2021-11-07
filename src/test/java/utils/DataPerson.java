package utils;

import lombok.Value;

@Value
public class DataPerson {
    private final String login;
    private final String password;
    private final String status;
}