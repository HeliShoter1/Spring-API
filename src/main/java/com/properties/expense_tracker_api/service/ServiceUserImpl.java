package com.properties.expense_tracker_api.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.properties.expense_tracker_api.Exception.EtAuthException;
import com.properties.expense_tracker_api.domain.User;
import com.properties.expense_tracker_api.repontories.RepontoryUser;

@Service
@Transactional
public class ServiceUserImpl implements ServiceUser {

    @Autowired
    RepontoryUser repontoryUser;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if(email != null) email = email.toLowerCase();
        return repontoryUser.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid email format");
        Integer count = repontoryUser.getCountEmail(email);
        if(count > 0)
            throw new EtAuthException("Email already in use");
        Integer userId = repontoryUser.create(firstName, lastName, email, password);
        return repontoryUser.findById(userId);
    }
}
