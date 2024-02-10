package com.annakhuseinova.springjdbctemplateexample.dao;

import com.annakhuseinova.springjdbctemplateexample.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseJdbcDAO implements DAO<Course>{

    private Logger log = LoggerFactory.getLogger(CourseJdbcDAO.class);
    /**
     * When we have spring-data-jdbc on our classpath, Spring will create one instance of JdbcTemplate in the context
     * We need to add @Component to the class that wants to inject that JdbcTemplate
     *
     * JdbcTemplate automatically completes a lot of tedious work like opening and closing connections, etc...
     * */
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Course> rowMapper = (resultSet, rowNumber)-> {
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

    @Override
    public void create(Course course) {

    }

    @Override
    public Optional<Course> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Course course, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
