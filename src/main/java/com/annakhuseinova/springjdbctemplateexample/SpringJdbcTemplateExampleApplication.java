package com.annakhuseinova.springjdbctemplateexample;

import com.annakhuseinova.springjdbctemplateexample.dao.DAO;
import com.annakhuseinova.springjdbctemplateexample.model.Course;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringJdbcTemplateExampleApplication {

	private static DAO<Course> dao;

	public SpringJdbcTemplateExampleApplication(DAO<Course> dao) {
		this.dao = dao;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcTemplateExampleApplication.class, args);

		System.out.println("\n All courses --------------- \n");
		List<Course> courses = dao.list();
		courses.forEach(System.out::println);
	}

}
