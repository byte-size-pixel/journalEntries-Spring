package com.bytesize.journal.controller;

import com.bytesize.journal.entity.User;
import com.bytesize.journal.entity.journalEntry;
import com.bytesize.journal.service.UserService;
import com.bytesize.journal.service.journalEntryService;
import com.bytesize.journal.serviceImpl.UserAuthServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class journalEntryController {

    @Autowired
    private journalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthServiceImpl userAuthService;

    @GetMapping("all")
    public ResponseEntity<List<journalEntry>> getAllJournalEntriesOfUser() {
        User user = userService.findByUserName(userAuthService.getUserName());
        List<journalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<journalEntry> createJournalEntry(@RequestBody journalEntry webEntry) {
        try {
            journalEntryService.saveJournalEntry(webEntry);
            return new ResponseEntity<>(webEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id) {
        boolean userHasJournalEntry = journalEntryService.checkUserHasJournalEntry(id);
        if(userHasJournalEntry) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        Optional<journalEntry> jEntry = journalEntryService.getById(id);
        return jEntry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<HttpStatus> deleteJournalEntryById(@PathVariable ObjectId id) {
        boolean removed = journalEntryService.deleteById(id);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody journalEntry webEntry) {
        Optional<journalEntry> existingJournalEntry = journalEntryService.getById(id);
        if (existingJournalEntry.isPresent()) {
            journalEntry old = existingJournalEntry.get();
            old.setTitle(webEntry.getTitle());
            old.setContent(webEntry.getContent());
            journalEntryService.saveJournalEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(webEntry,HttpStatus.NOT_FOUND);
    }
}
