package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @Author zhengdp
 * @Description  @Column 列定义
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Student")
@Table(
        name = "student",
        uniqueConstraints = {
                // 如果email使用了 unique 属性，该注解是不会生效的
                @UniqueConstraint(
                        name = "student_email_unique",
                        columnNames = "email" //唯一约束的列
                )
        }
)
public class Student {

    // 设置Id值的序列化生成器
    @Id
    @SequenceGenerator(
            name ="student_sequence", // 序列化生成器的唯一值
            sequenceName = "student_sequence",
            allocationSize = 1 // 指定每次递增的数
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id",
            updatable = false, // 不允许更新
            nullable = false // 不允许为空
    )
    private Long id;
    @Column(
            name ="first_name",
            columnDefinition = "TEXT", //列定义
            nullable = false
    )
    private String firstName;
    @Column(
            name ="last_name",
            columnDefinition = "TEXT", //列定义
            nullable = false
    )
    private String lastName;
    @Column(
            name ="email",
            columnDefinition = "TEXT", //列定义
            nullable = false
//            unique = true //唯一索引（会创建一个唯一索引，但名称随机）
    )
    private String email;
    @Column(
            name ="age",
            nullable = false
    )
    private Integer age;

    @OneToOne(
            // 双向绑定
            mappedBy = "student", // 在关联的反向侧指定，用该注解来标注具有关联关系的字段
            // 只能控制关联的反向侧（StudentCard删除时，无论该属性为什么值，都会进行级联删除）
            orphanRemoval = true // 级联删除，删除Student时会将其关联的StudentCard也一起删除
    )
    private StudentIdCard studentIdCard;


    public Student(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", studentIdCard=" + studentIdCard.toString2() +
                '}';
    }

    public String toString2() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
