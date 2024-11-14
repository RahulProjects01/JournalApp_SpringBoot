package com.basic.basicApp.repository;

import com.basic.basicApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntryRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);

    User deleteByUserName(String userName);
}
