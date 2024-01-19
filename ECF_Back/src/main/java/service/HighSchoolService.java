package service;

import daoImpl.*;
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
    private TimeTableDao timeTableDao;

    public HighSchoolService(ClassroomDao classroomDao, DepartmentDao departmentDao, GradeDao gradeDao, ScheduleDao scheduleDao, StudentDao studentDao, SubjectDao subjectDao, TeacherDao teacherDao, TimeTableDao timeTableDao) {
        this.classroomDao = classroomDao;
        this.departmentDao = departmentDao;
        this.gradeDao = gradeDao;
        this.scheduleDao = scheduleDao;
        this.studentDao = studentDao;
        this.subjectDao = subjectDao;
        this.teacherDao = teacherDao;
        this.timeTableDao = timeTableDao;
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
    public  boolean createTimetable(TimeTable timeTable) {
        return timeTableDao.create(timeTable);
    }

    public  Department getDepartmentById(Long idDepartment) {
        return departmentDao.getById(idDepartment);
    }

    public  Classroom getClassroom(Long idClassroom) {
        return classroomDao.getById(idClassroom);
    }
    public  TimeTable getTimeTable(Long idTimetable) {
        return timeTableDao.getById(idTimetable);
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

    public List<Grade> getGradeStudent(Long id) {
        return  gradeDao.getGradeStudent(id);
    }

    public Long getAverageGrade(Long id) {
        return  gradeDao.getAverageGradeStudent(id);
    }

    public Long displayNumberStudentDepartment (Long id) {
        return studentDao.displayNumberStudentDepartment(id);
    }

    public boolean updateStudent(Student student) {
       if(studentDao.update(student)) {
           return true;
       };
       return false;
    }

    public boolean updateTeacher(Teacher teacher) {
        if(teacherDao.update(teacher)) {
            return true;
        };
        return false;
    }


    public List<String> displaStudentLevel (int level) {
        return classroomDao.displayNameStudentLevel(level);
    }

    public boolean deleteStudent(Long id)  {
        return  studentDao.delete(id);
    }

    public boolean deleteClassroom(Long id)  {
        return  classroomDao.delete(id);
    }

    public boolean deleteDepartment(Long id)  {
        return  departmentDao.delete(id);
    }
    public void close() {
        classroomDao.close();
        departmentDao.close();
        gradeDao.close();;
        scheduleDao.close();;
        studentDao.close();
        studentDao.close();
        subjectDao.close();
        teacherDao.close();
    }









}
