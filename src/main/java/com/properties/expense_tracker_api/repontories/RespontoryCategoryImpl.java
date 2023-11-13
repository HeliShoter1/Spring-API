package com.properties.expense_tracker_api.repontories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.properties.expense_tracker_api.Exception.EtBadRequestException;
import com.properties.expense_tracker_api.Exception.EtResourceNotFoundException;
import com.properties.expense_tracker_api.domain.Category;

@Repository
public class RespontoryCategoryImpl implements RespontoryCategory{
    private static final String SQL_FIND_ALL = "SELECT C.categories_id, C.user_id, C.title, C.description, " +
    "COALESCE(SUM(T.amount), 0) TOTAL_EXPENSE " +
    "FROM et_transactions T RIGHT OUTER JOIN et_categories C ON C.categories_id = T.category_id " +
    "WHERE C.user_id = ? GROUP BY C.categories_id";
    private static final String SQL_FIND_BY_ID ="SELECT C.categories_id, C.user_id, C.title, C.description, " +
    "COALESCE(SUM(T.amount), 0) TOTAL_EXPENSE " +
    "FROM et_transactions T RIGHT OUTER JOIN et_categories C ON C.categories_id = T.category_id " +
    "WHERE C.user_id = ? AND C.categories_id = ? GROUP BY C.categories_id";
    private static final String SQL_CREATE = "INSERT INTO et_categories (categories_id, user_id, title, description) VALUES(NEXTVAL('et_categories_seq'), ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE et_categories SET title = ?, description = ? " +
            "WHERE user_id = ? AND categories_id = ?";
    private static final String SQL_DELETE_CATEGORY = "DELETE FROM et_categories WHERE user_id = ? AND categories_id = ?";
    private static final String SQL_DELETE_ALL_TRANSACTIONS = "DELETE FROM et_transactions WHERE category_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public List<Category> findAll(Integer userId) throws EtResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId}, categoryRowMapper);
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException{
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId}, categoryRowMapper);
        }catch (Exception e) {
            throw new EtResourceNotFoundException("Category not found");
        }
    }

    @Override
    public Integer create(Integer userId, String title, String description) throws EtBadRequestException{
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, description);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("categories_id");
        }catch (Exception e) {
            throw new EtBadRequestException("Invalid request1");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException{
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{category.getTitle(),category.getDescription(),userId,categoryId});
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId) throws EtBadRequestException{
        this.removeAllCatTransaction(categoryId);
        jdbcTemplate.update(SQL_DELETE_CATEGORY, new Object[]{userId,categoryId});
    }

    public void removeAllCatTransaction(Integer categoryId){
        jdbcTemplate.update(SQL_DELETE_ALL_TRANSACTIONS, new Object[]{categoryId});
    }
    private RowMapper<Category> categoryRowMapper = ((rs, rowNum) -> {
        return new Category(rs.getInt("categories_id"),
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getString("description"),
                0.0);
    });
}
