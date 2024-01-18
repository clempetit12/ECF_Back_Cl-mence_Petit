package entity;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_teacher", nullable = false)
    private Long idTeacher;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public Teacher(String lastName, String firstName, int age, boolean isPrincipalTeacher, boolean isHeadTeacher, Department department) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.isPrincipalTeacher = isPrincipalTeacher;
        this.isHeadTeacher = isHeadTeacher;
        this.department = department;
        this.classroomList = new ArrayList<>();
        this.subjectList = new ArrayList<>();
    }


    public void setIdTeacher(Long teacher) {
        this.idTeacher = idTeacher;
    }

    public Long getIdTeacher() {
        return idTeacher;
    }

    public void addClassroom(Classroom classroom) {
        if (classroomList == null) {
            classroomList = new ArrayList<>();
        }
        classroomList.add(classroom);
    }

    public void addSubject(Subject subject) {
        if (subjectList == null) {
            subjectList = new ArrayList<>();
        }
        subjectList.add(subject);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "idTeacher=" + idTeacher +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", isPrincipalTeacher=" + isPrincipalTeacher +
                ", isHeadTeacher=" + isHeadTeacher +
                ", department=" + department +
                '}';
    }
}
