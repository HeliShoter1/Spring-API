package com.properties.expense_tracker_api.repontories;

import com.properties.expense_tracker_api.Exception.EtAuthException;
import com.properties.expense_tracker_api.domain.User;

public interface RepontoryUser {
    Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;

    User findByEmailAndPassword(String email,String password) throws EtAuthException;

    Integer getCountEmail(String Email);

    User findById(Integer userId);
}
    