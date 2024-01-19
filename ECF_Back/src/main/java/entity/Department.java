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
public class Department {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_department", nullable = false)
    private Long idDepartment;

    private String nameDepartment;

@OneToMany (mappedBy = "department", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
private List<Teacher> teacherList;

    @OneToMany (mappedBy = "department",cascade = CascadeType.ALL)
    private List<Classroom> classroomList;

    public Department(String nameDepartment) {
        this.nameDepartment = nameDepartment;
        this.teacherList = new ArrayList<>();
        this.classroomList = new ArrayList<>();
    }

    public Department() {

    }


    public void setIdDepartment(Long idDepartment) {
        this.idDepartment = idDepartment;
    }

    public Long getIdDepartment() {
        return idDepartment;
    }


    public boolean hasHeadTeacher() {
        for (Teacher teacher : teacherList) {
            if (teacher.isHeadTeacher()) {
                return true;
            }
        }
        return false;
    }
    public void addTeacher(Teacher teacher) {
        if (teacherList == null) {
            teacherList = new ArrayList<>();
        }
        teacherList.add(teacher);
        teacher.setDepartment(this);
    }



    @Override
    public String toString() {
        return "Department{" +
                "idDepartment=" + idDepartment +
                ", nameDepartment=" + nameDepartment +
                ", classroomList=" + classroomList +
                '}';
    }
}
