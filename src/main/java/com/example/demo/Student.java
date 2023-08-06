package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

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
            orphanRemoval = true, // 级联删除，删除Student时会将其关联的StudentCard也一起删除
            cascade = {
                    CascadeType.PERSIST,CascadeType.REMOVE
            }
    )
    private StudentIdCard studentIdCard;

    @OneToMany(
            mappedBy = "student", // 对应实体类中的关联属性的属性名
            orphanRemoval = true,
            cascade = {
                    CascadeType.PERSIST,CascadeType.REMOVE
            },
            fetch = FetchType.EAGER // ManyToOne/Many 默认是Lazy,在查询时不会去查询book，只有当代码中对books对象进行操作，才会去查询Book
    )
    private List<Book> books = new ArrayList<>();

//    @ManyToMany(
//            cascade = {
//                    CascadeType.PERSIST,CascadeType.REMOVE
//            }
//    )
//    @JoinTable( // JoinTable会为我们创建链接表，但一般要自己创建，才可以指定一些细节
//            name = "enrolment", // 链接表名称
//            joinColumns = @JoinColumn( // 定义外键
//                    name = "student_id",
//                    foreignKey = @ForeignKey(name = "enrolment_student_id_fk")
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "course_id",
//                    foreignKey = @ForeignKey(name = "enrolment_course_id_fk")
//            )
//    )
    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            mappedBy = "student" //Enrolment类中对应的属性名
    )
    private List<Enrolment> enrolments = new ArrayList<>();

    public void addEnrolment(Enrolment enrolment) {
        if(!enrolments.contains(enrolment)) {
            enrolments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment) {
        enrolments.remove(enrolment);
    }

    public void addBook(Book book) {
        if(!books.contains(book)) {
            books.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book) {
        if(this.books.contains(book)) {
            this.books.remove(book);
            book.setStudent(null);
        }
    }


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
