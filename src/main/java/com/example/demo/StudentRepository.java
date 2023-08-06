package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author zhengdp
 * @Description
 */
public interface StudentRepository extends JpaRepository<Student,Long> {

    /**
     * 通过按 命名规则命名方法，来自定义查询
     */
    Optional<Student> findStudentByEmail(String email);

    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName,Integer age);

    List<Student> findStudentsByFirstNameEqualsAndAgeGreaterThan(String firstName,Integer age);

    List<Student> findStudentsByFirstNameEqualsAndAgeGreaterThanEqual(String firstName,Integer age);

}
