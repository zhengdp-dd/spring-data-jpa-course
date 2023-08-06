package com.example.demo;

import com.github.javafaker.Faker;
import org.hibernate.Hibernate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
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

          // 初始化三本书
          student.addBook(new Book("Clean code", LocalDateTime.now().minusDays(4)));
          student.addBook(new Book("Think and Grow Rich", LocalDateTime.now()));
          student.addBook(new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

          student.setStudentIdCard(studentIdCard);

          // 设置 course
          student.enrolToCourse(new Course("Computer Science","IT"));
          student.enrolToCourse(new Course("Spring Data JPA","IT"));

          // 外键外侧实体 需要设置传播级别，才能将StudentCard级联保存
          studentRepository.save(student);

          studentRepository.findById(1L)
                  .ifPresent(s -> {
                      System.out.println("fetch book lazy ...");
                      List<Book> books = s.getBooks();
                      books.forEach(book -> {
                          System.out.println(
                                  s.getFirstName() + " borrowed " + book.getBookName()
                          );
                      });
                  });
      };
    }

    private static void oneToManyTest(StudentRepository studentRepository) {
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

        // 初始化三本书
        student.addBook(new Book("Clean code", LocalDateTime.now().minusDays(4)));
        student.addBook(new Book("Think and Grow Rich", LocalDateTime.now()));
        student.addBook(new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

        student.setStudentIdCard(studentIdCard);
        // 外键外侧实体 需要设置传播级别，才能将StudentCard级联保存
        studentRepository.save(student);

        studentRepository.findById(1L)
                .ifPresent(s -> {
                    System.out.println("fetch book lazy ...");
                    List<Book> books = s.getBooks();
                    books.forEach(book -> {
                        System.out.println(
                                s.getFirstName() + " borrowed " + book.getBookName()
                        );
                    });
                });
    }

    private static void oneToOneTest(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository) {
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
