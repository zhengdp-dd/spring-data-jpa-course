package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository) {
      return args -> {
          Faker faker = new Faker();
          String firstName = faker.name().firstName();
          String lastName = faker.name().lastName();
          String email = String.format("%s.%s@163.com",firstName,lastName);
          Integer age = faker.number().numberBetween(17,55);
          Student student = new Student(firstName,lastName,email,age);

          StudentIdCard studentIdCard = new StudentIdCard(
                  "123456789",
                  student
          );
          // 调用StudentIdCard的保存方法，会将Student也保存进去
          studentIdCardRepository.save(studentIdCard);

          studentRepository.findById(1L).ifPresent(System.out::println);

          studentIdCardRepository.findById(1L)
                  .ifPresent(System.out::println);

          // 测试级联删除
          studentRepository.deleteById(1L);
//          studentIdCardRepository.deleteById(1L);
      };
    }

    private static void paging(StudentRepository repository) {
        PageRequest pageRequest = PageRequest.of(0,5,Sort.by("firstName").ascending());
        Page<Student> page = repository.findAll(pageRequest);
        System.out.println(page);
    }

    private static void sorting(StudentRepository repository) {
        // Sort
        Sort sort = Sort.by("firstName").ascending()
                .and(Sort.by(Sort.Direction.DESC,"age"));

        repository.findAll(sort).forEach(student -> {
            System.out.println(student.getFirstName() + " " + student.getAge());
        });
    }

    private void generateRandomStudents(StudentRepository repository) {
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
