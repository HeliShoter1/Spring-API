package com.properties.expense_tracker_api.service;

import java.util.List;

import com.properties.expense_tracker_api.Exception.EtBadRequestException;
import com.properties.expense_tracker_api.Exception.EtResourceNotFoundException;
import com.properties.expense_tracker_api.domain.Category;

public interface ServiceCategory {
    List<Category> fetchAllCategories(Integer usserId);

    Category fetchCategoryById(Integer userId, Integer CategoryId) throws EtResourceNotFoundException;

    Category addCategory(Integer userId, String title, String description) throws EtBadRequestException;

    void updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException;

    void removeCategory(Integer userId, Integer CategoryId) throws EtResourceNotFoundException;
}
