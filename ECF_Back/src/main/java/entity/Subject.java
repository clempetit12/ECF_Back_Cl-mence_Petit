package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.List;

@Entity
@Getter
@Setter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_teacher", nullable = false)
    private Long idSubject;

    private String title;
    private Duration duration;
    private int coefficient;

    @OneToMany (mappedBy = "subject")
    private List<Grade> gradeList;

    @ManyToMany(mappedBy = "subjectList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> scheduleList;

    @ManyToMany(mappedBy = "subjectList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Teacher> teacherList;
    public void setIdSubject(Long idSubject) {
        this.idSubject = idSubject;
    }

    public Long getIdSubject() {
        return idSubject;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "idSubject=" + idSubject +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", coefficient=" + coefficient +
                '}';
    }


}