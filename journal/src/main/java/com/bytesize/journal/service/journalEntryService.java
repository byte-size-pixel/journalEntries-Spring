package com.bytesize.journal.service;

import com.bytesize.journal.entity.User;
import com.bytesize.journal.entity.journalEntry;
import com.bytesize.journal.repository.journalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class journalEntryService {

    @Autowired
    private journalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveJournalEntry(journalEntry jEntry, String userName)
    {
        User user = userService.findByUserName(userName);
        jEntry.setDate(LocalDateTime.now());
        journalEntry savedEntry = journalEntryRepo.save(jEntry);
        user.getJournalEntries().add(savedEntry);
    }

    public List<journalEntry> getAll(){
        return journalEntryRepo.findAll();
    }

    public Optional<journalEntry> getById(ObjectId id)
    {
        return journalEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id)
    {
        journalEntryRepo.deleteById(id);
    }

}
