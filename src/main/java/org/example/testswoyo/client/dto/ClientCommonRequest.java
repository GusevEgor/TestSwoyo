package org.example.testswoyo.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@AllArgsConstructor
@Setter
@Getter
public class ClientCommonRequest implements Serializable {
    private String command;
    private Object request;
}
