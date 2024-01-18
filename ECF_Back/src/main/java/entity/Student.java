package entity;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student", nullable = false)
    private Long student;

    @NotNull
    private String lastName;
    private String firstName;
    private Date dateOfBirth;
    private String email;

    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "id_classroom")
    private Classroom classroom;

    @OneToMany (mappedBy = "student")
    private List<Grade> gradeList;

    public Student() {

    }


    public void setStudent(Long student) {
        this.student = student;
    }

    public Long getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return "Student{" +
                "student=" + student +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", classroom=" + classroom +
                '}';
    }
}
