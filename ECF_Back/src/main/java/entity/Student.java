package entity;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student", nullable = false)
    private Long idStudent;

    @NotNull
    private String lastName;
    private String firstName;
    private Date dateOfBirth;
    private String email;

    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "id_classroom")
    private Classroom classroom;

    @OneToMany (mappedBy = "student", cascade =CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Grade> gradeList;

    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "id_schedule")
    private Schedule schedule;
    public Student() {

    }

    public Student(String lastName, String firstName, Date dateOfBirth, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.gradeList = new ArrayList<>();
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }


    public Long getIdStudent() {
        return idStudent;
    }

    public void addGrade(Grade grade) {
        if (gradeList == null) {
            gradeList = new ArrayList<>();
        }
        gradeList.add(grade);
        grade.setStudent(this);
    }

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", classroom=" + classroom +
                '}';
    }
}
