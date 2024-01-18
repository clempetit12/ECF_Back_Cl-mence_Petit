package service;

import dao.*;
import entity.*;

import java.util.List;

public class HighSchoolService {
    private ClassroomDao classroomDao;
    private DepartmentDao departmentDao;
    private GradeDao gradeDao;
    private ScheduleDao scheduleDao;
    private StudentDao studentDao;
    private SubjectDao subjectDao;
    private TeacherDao teacherDao;

    public HighSchoolService(ClassroomDao classroomDao,DepartmentDao departmentDao,GradeDao gradeDao,ScheduleDao scheduleDao, StudentDao studentDao, SubjectDao subjectDao, TeacherDao teacherDao) {
        this.classroomDao = classroomDao;
        this.departmentDao = departmentDao;
        this.gradeDao = gradeDao;
        this.scheduleDao = scheduleDao;
        this.studentDao = studentDao;
        this.subjectDao = subjectDao;
        this.teacherDao = teacherDao;
    }

    public  boolean createDepartment(Department department) {
        return departmentDao.create(department);
    }

    public  boolean createTeacher(Teacher teacher) {
        return teacherDao.create(teacher);
    }

    public  boolean createStudent(Student student) {
        return studentDao.create(student);
    }

    public  boolean createSubject(Subject subject) {
        return subjectDao.create(subject);
    }

    public  boolean createGrade(Grade grade) {
        return gradeDao.create(grade);
    }

    public  boolean createClassroom(Classroom classroom) {
        return classroomDao.create(classroom);
    }

    public  boolean createSchedule(Schedule schedule) {
        return scheduleDao.create(schedule);
    }

    public  Department getDepartmentById(Long idDepartment) {
        return departmentDao.getById(idDepartment);
    }

    public  Classroom getClassroom(Long idClassroom) {
        return classroomDao.getById(idClassroom);
    }

    public  Student getStudent(Long idStudent) {
        return studentDao.getById(idStudent);
    }

    public  Teacher getTeacher(Long idTeacher) {
        return teacherDao.getById(idTeacher);
    }

    public  Subject getSubject(Long idSubject) {
        return subjectDao.getById(idSubject);
    }

    public List<Classroom> getAllClassroom() {
        return classroomDao.getAll();
    }









}
