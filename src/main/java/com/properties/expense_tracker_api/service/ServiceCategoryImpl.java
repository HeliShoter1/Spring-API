package com.properties.expense_tracker_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.properties.expense_tracker_api.Exception.EtBadRequestException;
import com.properties.expense_tracker_api.Exception.EtResourceNotFoundException;
import com.properties.expense_tracker_api.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import com.properties.expense_tracker_api.repontories.RespontoryCategory;

@Service
@Transactional
public class ServiceCategoryImpl implements ServiceCategory{

    @Autowired
    RespontoryCategory respontoryCategory;
    
    @Override
    public List<Category> fetchAllCategories(Integer userId){
        return respontoryCategory.findAll(userId);
    }

    @Override
    public Category fetchCategoryById(Integer userId, Integer CategoryId) throws EtResourceNotFoundException{
        return respontoryCategory.findById(userId, CategoryId);
    }

    @Override
    public Category addCategory(Integer userId, String title, String description) throws EtBadRequestException{
        int categoryId = respontoryCategory.create(userId, title, description);
        return respontoryCategory.findById(userId, categoryId);
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException{
        respontoryCategory.update(userId, categoryId, category);
        return;
    }

    @Override
    public void removeCategory(Integer userId, Integer CategoryId) throws EtResourceNotFoundException{
        respontoryCategory.removeById(userId, CategoryId);
        return;
    }
}
