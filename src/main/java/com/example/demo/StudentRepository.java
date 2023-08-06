package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

    // jpql 语句，表名使用的是 @Entity中定义的Name值
    @Query(value = "SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> customQuery(String email);

    @Query(
            value = "SELECT * FROM student WHERE first_name = ?1 AND age >= ?2",
            nativeQuery = true
    )
    List<Student> customQuery2(String email,Integer age);

    /*
    * @Modifying注解修饰的方法，需要与@Transactional配合使用
    * 也就是说，该方法的执行需要在一个事务中执行
    * */
    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = ?1")
    Integer customDeleteById(Long id);

}
