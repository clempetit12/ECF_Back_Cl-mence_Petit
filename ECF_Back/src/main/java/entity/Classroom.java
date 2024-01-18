package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classroom", nullable = false)
    private Long classroom;

    private String nameClassroom;
    private int levelClassroom;

    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "id_department")
    private Department department;

    @OneToMany (mappedBy = "classroom")
    private List<Student> studentList;

    @ManyToMany(mappedBy = "classroomList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Teacher> teacherList;
    public void setClassroom(Long classroom) {
        this.classroom = classroom;
    }

    public Long getClassroom() {
        return classroom;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "classroom=" + classroom +
                ", nameClassroom='" + nameClassroom + '\'' +
                ", levelClassroom=" + levelClassroom +
                ", studentList=" + studentList +
                '}';
    }
}
