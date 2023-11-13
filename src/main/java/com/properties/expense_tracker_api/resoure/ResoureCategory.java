package com.properties.expense_tracker_api.resoure;

import com.properties.expense_tracker_api.domain.Category;
import com.properties.expense_tracker_api.service.ServiceCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

@RestController
@RequestMapping("/api/categories")
public class ResoureCategory {

    @Autowired
    ServiceCategory serviceCategory;
    
    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request) {
        int userId = (Integer) request.getAttribute("userId");
        List<Category> categories = serviceCategory.fetchAllCategories(userId);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<Category> getCategoryById(HttpServletRequest request,@PathVariable Integer categoryId){
        int userId = (Integer) request.getAttribute("userId");
        Category category = serviceCategory.fetchCategoryById(userId, categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Category> addCategory(HttpServletRequest request,@RequestBody Map<String,Object> categoryMap){
        int userId = (Integer) request.getAttribute("userId");
        String title = (String) categoryMap.get("title");
        String description = (String) categoryMap.get("description");
        Category category = serviceCategory.addCategory(userId, title,description);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Map<String,Boolean>> updateCategory(HttpServletRequest request,@PathVariable Integer categoryId,@RequestBody Category category){
        int userId = (Integer) request.getAttribute("userId");
        serviceCategory.updateCategory(userId, categoryId, category);
        Map<String,Boolean> map = new LinkedHashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(HttpServletRequest request,@PathVariable("categoryId")Integer categoryId){
        int userId = (Integer) request.getAttribute("userId");
        serviceCategory.removeCategory(userId, categoryId);
        return new ResponseEntity<>("delete sucessfully",HttpStatus.OK);
    }
}
