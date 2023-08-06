package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student student = new Student(
                    "DP",
                    "Zheng",
                    "Zhengdp@edu",
                    21
            );

            Student ahmed = new Student(
                    "Ahmed",
                    "Ali",
                    "Ahmed.ali@edu",
                    21
            );
            List<Student> temp = new ArrayList<>();
            temp.add(student);
            temp.add(ahmed);
            System.out.println("Adding zhengdp and ahmed");
            repository.saveAll(temp);

            System.out.print("Number of Student: ");
            System.out.println(repository.count());

            repository.findById(2L).ifPresent(System.out::println);

            repository.findById(3L).ifPresent(System.out::println);
            System.out.println("Select all students");
            List<Student> all = repository.findAll();
            all.forEach(System.out::println);

            System.out.println("Delete Student By 1L");
            repository.deleteById(1L);

            System.out.print("Number of Student: ");
            System.out.println(repository.count());
        };
    }

}
