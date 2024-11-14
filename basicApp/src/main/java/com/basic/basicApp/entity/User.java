package com.basic.basicApp.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;

    @NotNull
    @Indexed(unique = true)
    private String userName;

    @NotNull
    private String password;

    @DBRef(lazy = false)
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles;
}
