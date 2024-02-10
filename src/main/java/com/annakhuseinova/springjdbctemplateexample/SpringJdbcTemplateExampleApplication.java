package com.annakhuseinova.springjdbctemplateexample;

import com.annakhuseinova.springjdbctemplateexample.dao.DAO;
import com.annakhuseinova.springjdbctemplateexample.model.Course;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringJdbcTemplateExampleApplication {

	private static DAO<Course> dao;

	public SpringJdbcTemplateExampleApplication(DAO<Course> dao) {
		this.dao = dao;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcTemplateExampleApplication.class, args);

		System.out.println("\n Create Course ----------------- \n");
		Course someNewCourse = new Course(6, "Course name", "Course Description", "Course link");
		dao.create(someNewCourse);

		System.out.println("\n One Course ------------------- \n");
		Optional<Course> course = dao.get(1);
		System.out.println(course.get());

		System.out.println("\n All courses --------------- \n");
		List<Course> courses = dao.list();
		courses.forEach(System.out::println);
	}

}
