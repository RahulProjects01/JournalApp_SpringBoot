package com.basic.basicApp.repository;

import com.basic.basicApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

    Optional<JournalEntry> findById(ObjectId id);

    void deleteById(ObjectId id);
}
