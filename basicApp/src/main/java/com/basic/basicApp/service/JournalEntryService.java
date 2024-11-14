package com.basic.basicApp.service;

import com.basic.basicApp.entity.JournalEntry;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface JournalEntryService {

    void saveNewUser(JournalEntry muEntry, String userName);

    List<JournalEntry> getAll();

    Optional<JournalEntry> findById(ObjectId id);

    void deleteById(ObjectId id, String userName);
}
