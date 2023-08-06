package com.example.demo;

import com.github.javafaker.Faker;
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
    CommandLineRunner commandLineRunner1(StudentRepository repository) {
      return args -> {
          Faker faker = new Faker();
          for(int i =0;i<20;i++) {
              String firstName = faker.name().firstName();
              String lastName = faker.name().lastName();
              String email = String.format("%s.%s@163.com",firstName,lastName);
              Integer age = faker.number().numberBetween(17,55);
              repository.save(new Student(firstName,lastName,email,age));
          }
      };
    }

//    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student student = new Student(
                    "DP",
                    "Zheng",
                    "Zhengdp@edu",
                    21
            );

            Student student2 = new Student(
                    "DP",
                    "Li",
                    "LiDp@edu",
                    22
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
            temp.add(student2);
            System.out.println("Adding zhengdp and ahmed");
            repository.saveAll(temp);

            repository.findStudentByEmail("Ahmed.ali@edu").ifPresent(System.out::println);

            repository.findStudentsByFirstNameEqualsAndAgeEquals("DP",21)
                    .forEach(System.out::println);

            System.out.println("====================");

            repository.customQuery2("DP",21).forEach(System.out::println);

            System.out.println(repository.customDeleteById(1L));;
        };
    }

}
