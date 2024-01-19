package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classroom", nullable = false)
    private Long iDclassroom;

    private String nameClassroom;
    private int levelClassroom;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_department")
    private Department department;

    @OneToMany (mappedBy = "classroom",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Student> studentList;

    @ManyToMany(mappedBy = "classroomList", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Teacher> teacherList;

    public Classroom(String nameClassroom, int levelClassroom, Department department, List<Student> studentList, List<Teacher> teacherList) {
        this.nameClassroom = nameClassroom;
        this.levelClassroom = levelClassroom;
        this.department = department;
        this.studentList = studentList;
        this.teacherList = teacherList;
    }

    public Classroom() {

    }

    public void setIdClassroom(Long classroom) {
        this.iDclassroom = iDclassroom;
    }

    public Long getIdClassroom() {
        return iDclassroom;
    }

    public void addStudent(Student student) {
        if (studentList == null) {
            studentList = new ArrayList<>();
        }
        studentList.add(student);
        student.setClassroom(this);
    }
    @Override
    public String toString() {
        return "Classroom{" +
                "classroom=" + iDclassroom +
                ", nameClassroom='" + nameClassroom + '\'' +
                ", levelClassroom=" + levelClassroom +
                '}';
    }
}
