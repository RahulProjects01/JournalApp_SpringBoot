package com.basic.basicApp.controller;

import com.basic.basicApp.ServiceImplementation.JournalEntryImpl;
import com.basic.basicApp.entity.JournalEntry;
import com.basic.basicApp.entity.User;
import com.basic.basicApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    JournalEntryImpl journalEntryImplService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        if (user == null) {
            return new ResponseEntity<>("User not found with username: " + userName, HttpStatus.NOT_FOUND);
        }

        List<JournalEntry> all = user.getJournalEntries();
        if (all == null || all.isEmpty()) {
            return new ResponseEntity<>("No journal entries found for user: " + userName, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(all, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryImplService.saveNewUser(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);

        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("User is not authenticated.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String userName = authentication.getName();
        System.out.println("Authenticated User: " + userName);

        Optional<User> user = userService.findById(myId);
        if (user == null) {
            System.out.println("User not found in database.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println("User's Journal Entries: " + user.get().getJournalEntries());

        List<JournalEntry> journalEntry = user.get().getJournalEntries();
        if (!journalEntry.isEmpty()) {
            System.out.println("Journal entry found and belongs to user.");
            //return new ResponseEntity<>(journalEntry, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(journalEntry);
        }
        System.out.println("Requested journal entry not found for this user.");
        return new ResponseEntity<>("Requested resource could not be found.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryImplService.deleteById(myId, null);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntry(
            @PathVariable String myId,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName
    ) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(myId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid ID format", HttpStatus.BAD_REQUEST);
        }

        JournalEntry old = journalEntryImplService.findById(objectId).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
            journalEntryImplService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
