package entity;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_teacher", nullable = false)
    private Long teacher;

    @NotNull
    private String lastName;
    private String firstName;
    //age > 18
    private int age;
    private boolean isPrincipalTeacher;
    private boolean isHeadTeacher;

    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "id_department")
    private Department department;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "teacher_classroom",
            joinColumns = @JoinColumn(name = "id_teacher"),
            inverseJoinColumns = @JoinColumn(name = "id_classroom")
    )
    private List<Classroom> classroomList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "teacher_subject",
            joinColumns = @JoinColumn(name = "id_teacher"),
            inverseJoinColumns = @JoinColumn(name = "id_subject")
    )
    private List<Subject> subjectList;
    public Teacher() {

    }


    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public Long getTeacher() {
        return teacher;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacher=" + teacher +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", isPrincipalTeacher=" + isPrincipalTeacher +
                ", isHeadTeacher=" + isHeadTeacher +
                ", department=" + department +
                '}';
    }
}
