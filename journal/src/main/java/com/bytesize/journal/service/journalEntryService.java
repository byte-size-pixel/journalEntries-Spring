package com.bytesize.journal.service;

import com.bytesize.journal.entity.User;
import com.bytesize.journal.entity.journalEntry;
import com.bytesize.journal.repository.journalEntryRepo;
import com.bytesize.journal.serviceImpl.UserAuthServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class journalEntryService {

    @Autowired
    private journalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthServiceImpl userAuthService;

    @Transactional
    public void saveJournalEntry(journalEntry jEntry)
    {
        User user = userService.findByUserName(userAuthService.getUserName());
        jEntry.setDate(LocalDateTime.now());
        journalEntry savedEntry = journalEntryRepo.save(jEntry);
        user.getJournalEntries().add(savedEntry);
        userService.saveUser(user);
    }

    public List<journalEntry> getAll(){
        return journalEntryRepo.findAll();
    }

    public Optional<journalEntry> getById(ObjectId id)
    {
        return journalEntryRepo.findById(id);
    }

    public boolean deleteById(ObjectId id)
    {
        User user = userService.findByUserName(userAuthService.getUserName());
        boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if(removed){
            userService.saveUser(user);
            journalEntryRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean checkUserHasJournalEntry(ObjectId id){
        User user = userService.findByUserName(userAuthService.getUserName());
        List<journalEntry> userLinkedJournalEntries = new ArrayList<>();
        for (journalEntry x : user.getJournalEntries()) {
            if (x.getId().equals(id)) {
                userLinkedJournalEntries.add(x);
            }
        }
        return userLinkedJournalEntries.isEmpty();
    }

}
