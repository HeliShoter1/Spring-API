package com.properties.expense_tracker_api.service;

import com.properties.expense_tracker_api.domain.User;
import com.properties.expense_tracker_api.Exception.EtAuthException;

public interface ServiceUser {
    User validateUser(String email,String password) throws EtAuthException;
    
    User registerUser(String firstName,String lastName,String email,String password) throws EtAuthException;
}
