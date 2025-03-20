package com.bytesize.journal.repository;

import com.bytesize.journal.entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalEntryRepo extends MongoRepository<journalEntry, ObjectId> {

}
