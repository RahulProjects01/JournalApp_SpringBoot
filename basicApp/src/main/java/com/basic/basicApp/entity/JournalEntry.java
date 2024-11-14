package com.basic.basicApp.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document
@NoArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotNull(message = "Content cannot be null")
    @Size(min = 10, message = "Content must be at least 10 characters long")
    private String content;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDateTime date;
}
