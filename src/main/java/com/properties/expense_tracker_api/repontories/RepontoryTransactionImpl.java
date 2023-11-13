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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.properties.expense_tracker_api.Exception.EtBadRequestException;
import com.properties.expense_tracker_api.Exception.EtResourceNotFoundException;
import com.properties.expense_tracker_api.domain.Transaction;

@Repository
public class RepontoryTransactionImpl implements RepontoryTransaction{
    private static final String SQL_FIND_ALL = "SELECT * FROM et_transactions WHERE user_id = ? AND category_id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM ET_TRANSACTIONS WHERE user_id = ? AND category_id = ? AND transactions_id = ?";
    private static final String SQL_CREATE = "INSERT INTO ET_TRANSACTIONS (transactions_id, category_id, user_id, amount, note, transactions_date) VALUES(NEXTVAL('et_transactions_seq'), ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE et_transactions SET amount = ?, note = ?, transactions_date = ? WHERE user_id = ? AND category_id = ? AND transactions_id = ?";
    private static final String SQL_DELETE = "DELETE FROM ET_TRANSACTIONS WHERE user_id = ? AND category_id = ? AND transactions_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(Integer userId, Integer categoryId){
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId,categoryId}, transactionRowMapper);
    }

    @Override
    public Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException{
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId, transactionId}, transactionRowMapper);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Transaction not found");
        }
    }

    @Override
    public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException{
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE,Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, categoryId);
                ps.setInt(2, userId);
                ps.setDouble(3, amount);
                ps.setString(4, note);
                ps.setLong(5, transactionDate);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("transactions_id");
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer usedId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException{
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{transaction.getAmount(), transaction.getNote(), transaction.getTransactionDate(), usedId, categoryId, transactionId});
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void remove(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException{
        try {
            jdbcTemplate.update(SQL_DELETE, new Object[]{userId,categoryId,transactionId});
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    private RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) -> {
        return new Transaction(rs.getInt("transactions_id"),
                rs.getInt("category_id"),
                rs.getInt("user_id"),
                rs.getDouble("amount"),
                rs.getString("note"),
                rs.getLong("transactions_date"));
    });
}
