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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @Author zhengdp
 * @Description
 */
@Data
@Entity(name = "StudentIdCard")
@Table(
        name = "student_id_card",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_id_card_number_unique",
                        columnNames = "card_number"
                )
        }
)
@AllArgsConstructor
@NoArgsConstructor
public class StudentIdCard {

    @Id
    @SequenceGenerator(
            name ="student_id_card_sequence", // 序列化生成器的唯一值
            sequenceName = "student_id_card_sequence",
            allocationSize = 1 // 指定每次递增的数
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_id_card_sequence"
    )
    @Column(
            name = "id",
            updatable = false, // 不允许更新
            nullable = false // 不允许为空
    )
    private Long id;

    @Column(
            name ="card_number",
            nullable = true,
            length = 15
    )
    private String cardNumber;

    // CascadeType.ALL: 传播所有行为：增删改查 --> card的保存也会传播到student,使得student也进行持久化操作
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER // 默认
    )
    @JoinColumn(
            name ="student_id", // 当前表 外键名
            referencedColumnName = "id", // 对应表的外键名
            foreignKey = @ForeignKey(
                    name = "student_id_fk" // 设置外键约束名称
            )
    )
    private Student student;

    public StudentIdCard(String cardNumber, Student student) {
        this.cardNumber = cardNumber;
        this.student = student;
    }

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", student=" + student.toString2() +
                '}';
    }

    public String toString2() {
        return "StudentIdCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
