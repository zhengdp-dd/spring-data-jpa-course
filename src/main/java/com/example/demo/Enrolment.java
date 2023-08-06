package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @Author zhengdp
 * @Description
 */
@Entity(name = "Enrolment")
@Table(name = "enrolment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrolment {

    @EmbeddedId // 联合主键，嵌入到该类
    private EnrolmentId id;

    @ManyToOne
    @MapsId("studentId") // 说明这里的这个Student只是EnrolmentId中StudentId的映射
    @JoinColumn(
            name = "student_id", // 外键名称（数据库实际名称）
            foreignKey = @ForeignKey(
                    name = "enrolment_student_id_fk"
            )
    )
    private Student student;

    @ManyToOne
    @MapsId("courseId") // 说明这里的这个Student只是EnrolmentId中StudentId的映射
    @JoinColumn(
            name = "course_id", // 外键名称（数据库实际名称）
            foreignKey = @ForeignKey(
                    name = "enrolment_course_id_fk"
            )
    )
    private Course course;

    @Column(
            name = "craete_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createAt;

    public Enrolment(Student student, Course course, LocalDateTime createAt) {
        this.student = student;
        this.course = course;
        this.createAt = createAt;
    }
}
