package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Department {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_department", nullable = false)
    private Long idDepartment;

    private NameDepartment nameDepartment;

@OneToMany (mappedBy = "department")
private List<Teacher> teacherList;

    @OneToMany (mappedBy = "department")
    private List<Classroom> classroomList;

    public void setIdDepartment(Long idDepartment) {
        this.idDepartment = idDepartment;
    }

    public Long getIdDepartment() {
        return idDepartment;
    }

    @Override
    public String toString() {
        return "Department{" +
                "idDepartment=" + idDepartment +
                ", nameDepartment=" + nameDepartment +
                ", teacherList=" + teacherList +
                ", classroomList=" + classroomList +
                '}';
    }
}
