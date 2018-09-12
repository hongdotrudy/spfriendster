package com.sp.friendster.repository.impl;

import com.sp.friendster.domain.User;
import com.sp.friendster.repository.UserCustomRepository;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<User> findUsersToNotify(String email, String message) {
        Criteria finalCriteria = new Criteria();
        Criteria nonBlockCriteria = new Criteria("blocks").nin(email);
        Criteria isFriendCriteria = new Criteria("friends").in(email);
        Criteria isSubscriberCriteria = new Criteria("subscribe").in(email);

        List<String> mentionedEmails = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(message," ");
        while (stringTokenizer.hasMoreTokens()){
            String token = stringTokenizer.nextToken();
            if(EmailValidator.getInstance().isValid(email)){
                mentionedEmails.add(token);
            }
        }

        Criteria isMentionedCriteria = new Criteria("email").in(mentionedEmails);

        // Either is friend or subscriber or email mentioned in message
        Criteria orCriteria = new Criteria().orOperator(isFriendCriteria, isSubscriberCriteria, isMentionedCriteria);
        finalCriteria.andOperator(nonBlockCriteria, orCriteria);

        List<User> users = mongoTemplate.find(query(finalCriteria),User.class);
        return users;
    }
}
