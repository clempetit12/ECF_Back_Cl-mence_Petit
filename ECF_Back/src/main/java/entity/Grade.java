package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grade", nullable = false)
    private Long idGrade;

    private int grade ;
    private String comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_subject")
    private Subject subject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student")
    private Student student;

    public Grade(int grade, String comment, Subject subject, Student student) {
        this.grade = grade;
        this.comment = comment;
        this.subject = subject;
        this.student = student;


    }

    public Grade() {

    }

    public void setIdGrade(Long idGrade) {
        this.idGrade = idGrade;
    }

    public Long getIdGrade() {
        return idGrade;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "idGrade=" + idGrade +
                ", grade=" + grade +
                ", comment='" + comment + '\'' +
                ", subject=" + subject +
                '}';
    }
}
