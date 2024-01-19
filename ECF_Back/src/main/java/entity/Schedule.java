package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_schedule", nullable = false)
    private Long idSchedule;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<TimeTable> timetableList;

    @OneToMany (mappedBy = "schedule",fetch = FetchType.EAGER)
    private List<Student> studentList;

    public Schedule(List<TimeTable> timetableList, List<Student> studentList) {
        this.timetableList = timetableList;
        this.studentList = studentList;
    }

    public Schedule() {

    }

    public void setIdSchedule(Long idSchedule) {
        this.idSchedule = idSchedule;
    }

    public Long getIdSchedule() {
        return idSchedule;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "idSchedule=" + idSchedule +
                ", timetableList=" + timetableList +
                ", studentList=" + studentList +
                '}';
    }
}
