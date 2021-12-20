package com.lcat.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowableParams {
    @Id
    @GeneratedValue
    private String id;
    private String username;
    private String manager;
}
