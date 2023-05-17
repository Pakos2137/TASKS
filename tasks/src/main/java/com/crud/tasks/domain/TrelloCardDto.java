package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrelloCardDto {
    private String name;
    private String description;
    private String pos;
    private String listId;

}