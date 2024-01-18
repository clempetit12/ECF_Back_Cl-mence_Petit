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

    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "id_subject")
    private Subject subject;

    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "id_student")
    private Student student;




    public void setIdGrade(Long idGrade) {
        this.idGrade = idGrade;
    }

    public Long getIdGrade() {
        return idGrade;
    }
}
