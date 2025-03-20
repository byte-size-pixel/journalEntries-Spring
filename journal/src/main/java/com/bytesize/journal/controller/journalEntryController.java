package com.bytesize.journal.controller;

import com.bytesize.journal.entity.User;
import com.bytesize.journal.entity.journalEntry;
import com.bytesize.journal.service.UserService;
import com.bytesize.journal.service.journalEntryService;
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
    
    @GetMapping("{userName}")
    public ResponseEntity<List<journalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName); 
        List<journalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty())
        {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<journalEntry> createEntry(@RequestBody journalEntry webEntry, @PathVariable String userName) {
        try {
            journalEntryService.saveJournalEntry(webEntry, userName);
            return new ResponseEntity<>(webEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<journalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Optional<journalEntry> jEntry = journalEntryService.getJournalEntryById(myId);
        return jEntry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PutMapping("id/{id}")
//    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody journalEntry webEntry) {
//        journalEntry oldJournalEntry = journalEntryService.getById(id).orElse(null);
//        if(oldJournalEntry!=null)
//        {
//            oldJournalEntry.setTitle(webEntry.getTitle()!=null && !webEntry.getTitle().isEmpty() ? webEntry.getTitle() : oldJournalEntry.getTitle());
//            oldJournalEntry.setContent(webEntry.getContent()!=null && !webEntry.getContent().isEmpty() ? webEntry.getContent(): oldJournalEntry.getContent());
//            journalEntryService.saveJournalEntry(oldJournalEntry, user);
//            return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
