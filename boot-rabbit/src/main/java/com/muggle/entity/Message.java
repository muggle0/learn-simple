package com.muggle.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    private String title;
    private String content;
}
