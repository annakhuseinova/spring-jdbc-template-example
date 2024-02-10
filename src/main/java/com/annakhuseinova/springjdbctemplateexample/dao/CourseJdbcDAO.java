package com.annakhuseinova.springjdbctemplateexample.dao;

import com.annakhuseinova.springjdbctemplateexample.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseJdbcDAO implements DAO<Course>{

    private final Logger log = LoggerFactory.getLogger(CourseJdbcDAO.class);
    /**
     * When we have spring-data-jdbc on our classpath, Spring will create one instance of JdbcTemplate in the context
     * We need to add @Component to the class that wants to inject that JdbcTemplate
     *
     * JdbcTemplate automatically completes a lot of tedious work like opening and closing connections, etc...
     * */
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Course> rowMapper = (resultSet, rowNumber)-> {
        Course course = new Course();
        course.setCourseId(resultSet.getInt("course_id"));
        course.setTitle(resultSet.getString("title"));
        course.setDescription(resultSet.getString("description"));
        course.setLink(resultSet.getString("link"));
        return course;
    };

    public CourseJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * RowMapper is a functional interface that defines how a specific row in db will be mapped to an object
     * in our app
     * */
    @Override
    public List<Course> list() {
        String sql = "SELECT course_id, title, description, link from course";
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * update method of JdbcTemplate is used for insert, delete and update operations
     * */
    @Override
    public void create(Course course) {
        String sql = "insert into course(title, description, link) values (?,?,?)";
        int rowsAffected = jdbcTemplate.update(sql, course.getTitle(), course.getDescription(), course.getLink());
        if (rowsAffected == 1){
            log.info("New course created: " + course.getTitle());
        }
    }

    @Override
    public Optional<Course> get(int id) {
        String sql = "SELECT course_id, title, description, link from course WHERE course_id = ?";
        Course course = null;
        try {
            course = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException exception){
            log.info("Course not found: " + id);
        }
        return Optional.ofNullable(course);
    }

    @Override
    public void update(Course course, int id) {
        String sql = "update course set title = ?, description = ?, link = ? where course_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, course.getTitle(), course.getDescription(), course.getLink());
        if (rowsAffected == 1){
            log.info("Course updated: " + course.getTitle());
        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from course where course_id = ?", id);
    }
}
