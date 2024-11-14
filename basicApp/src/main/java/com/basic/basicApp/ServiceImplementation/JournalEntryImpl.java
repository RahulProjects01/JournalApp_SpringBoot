package com.basic.basicApp.ServiceImplementation;

import com.basic.basicApp.entity.JournalEntry;
import com.basic.basicApp.entity.User;
import com.basic.basicApp.repository.JournalEntryRepository;
import com.basic.basicApp.service.JournalEntryService;
import com.basic.basicApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class JournalEntryImpl implements JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    UserService userService;

    @Transactional
    @Override
    public void saveNewUser(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);  // Add the saved journal entry to the user's list
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println("Error saving entry: " + e.getMessage());
            throw new RuntimeException("Failed to save journal entry", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Override
    public void deleteById(ObjectId id, String userName) {
        Optional<User> user = userService.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.get().getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.deleteById(user.get().getId());
    }
}
