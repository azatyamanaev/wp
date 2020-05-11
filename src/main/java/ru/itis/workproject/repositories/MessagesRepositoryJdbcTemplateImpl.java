package ru.itis.workproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.workproject.models.Message;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class MessagesRepositoryJdbcTemplateImpl implements MessagesRepository {

    //language=SQL
    private static final String SQL_INSERT_MESSAGE = "insert into message (login, text) values (?, ?);";
    //language=SQL
    private static final String SQL_UPDATE_MESSAGE = "update message \n" +
            "set login = ?, text = ?" +
            "where id = ?;";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from message;";
    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from message where id = ?;";
    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from message where id = ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Message> messageRowMapper = (row, rowNumber) ->
            Message.builder()
                    .id(row.getLong("id"))
                    .login(row.getString("login"))
                    .text(row.getString("text"))
                    .build();

    @Override
    public void save(Message model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(dataSource -> {
            PreparedStatement statement = dataSource.prepareStatement(SQL_INSERT_MESSAGE);
            statement.setString(1, model.getLogin());
            statement.setString(2, model.getText());
            return statement;
        }, keyHolder);
        model.setId((Long) keyHolder.getKey());
    }

    @Override
    public void update(Message model) {
        jdbcTemplate.update(dataSource -> {
            PreparedStatement statement = dataSource.prepareStatement(SQL_UPDATE_MESSAGE);
            statement.setString(1, model.getLogin());
            statement.setString(2, model.getText());
            statement.setLong(3, model.getId());
            return statement;
        });
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(dataSource -> {
            PreparedStatement statement = dataSource.prepareStatement(SQL_DELETE_BY_ID);
            statement.setLong(1, id);
            return statement;
        });
    }

    @Override
    public Optional<Message> find(Long id) {
        try {
            Message message = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, messageRowMapper);
            return Optional.of(message);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, messageRowMapper);
    }
}
