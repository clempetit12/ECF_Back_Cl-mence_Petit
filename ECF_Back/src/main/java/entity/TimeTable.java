package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_timetable", nullable = false)
    private Long idTimetable;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

   private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "id_subject")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "id_schedule")
    private Schedule schedule;
    public TimeTable(DayOfWeek dayOfWeek, LocalTime time, Subject subject) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.subject = subject;
    }

    public TimeTable() {

    }

    public void setIdTimetable(Long idTimetable) {
        this.idTimetable = idTimetable;
    }

    public Long getIdTimetable() {
        return idTimetable;
    }


}
