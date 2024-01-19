package controller;

import daoImpl.*;
import entity.*;
import service.HighSchoolService;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.DayOfWeek;

public class Ihm {

    private Scanner scanner = new Scanner(System.in);
    private int choix;
    private static HighSchoolService highSchoolService;
    private static ClassroomDao classroomDao;
    private static DepartmentDao departmentDao;
    private static GradeDao gradeDao;
    private static ScheduleDao scheduleDao;
    private static StudentDao studentDao;
    private static SubjectDao subjectDao;
    private static TeacherDao teacherDao;
    private TimeTableDao timeTableDao;

    public Ihm() {
        classroomDao = new ClassroomDao();
        departmentDao = new DepartmentDao();
        gradeDao = new GradeDao();
        scheduleDao = new ScheduleDao();
        studentDao = new StudentDao();
        subjectDao = new SubjectDao();
        teacherDao = new TeacherDao();
        timeTableDao = new TimeTableDao();
        highSchoolService = new HighSchoolService(classroomDao, departmentDao, gradeDao, scheduleDao, studentDao, subjectDao, teacherDao,timeTableDao);
    }


    public void start() {
        do {
            printMenu();
            choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix) {
                case 1:
                    createDepartment();
                    break;
                case 2:
                    createTeacher();
                    break;
                case 3:
                    createStudent();
                    break;
                case 4:
                    createSubject();
                    break;
                case 5:
                    createGrade();
                    break;
                case 6:
                    createClassroom();
                    break;
                case 7:
                    createTimeTable();
                    break;
                case 8:
                    createSchedule();
                    break;
                case 9:
                    displayClassrooms();
                    break;
                case 10:
                    displaySubjectStudent();
                    break;
                case 11:
                    displayGradeStudent();
                    break;
                case 12:
                    displayAverageGradeStudent();
                    break;
                case 13:
                    displayNumberStudentDepartment();
                    break;
                case 14:
                    displayStudentLevel();
                    break;
                case 15:
                    deleteStudent();
                    break;
                case 16:
                    deleteClassroom();
                    break;
                case 17:
                    deleteDepartment();
                    break;
                case 0:
                    closeAll();
                    break;
                default:
                    System.out.println("choix invalide !");
            }

        } while (choix != 0);
    }

    private void displayStudentLevel() {
        System.out.println("==== Les niveaux ====");
        System.out.println("6 - 6ème");
        System.out.println("5 - 5ème");
        System.out.println("4 - 4ème");
        System.out.println("3 - 3ème");
        System.out.println("1 - 1ère");
        System.out.println("2 - 2nd");
        System.out.println("0 - Terminal");
        System.out.println("Veuillez préciser le niveau que vous souhaitez afficher ?");
        int level = scanner.nextInt();
        scanner.nextLine();
List<String> nameStudent = highSchoolService.displaStudentLevel(level);
for (String string : nameStudent) {
    System.out.println("Nom de l'étudiant en niveau " + level + " : " + string);
}
    }

    private void createDepartment() {
        try {
            System.out.println("Combien de départements souhaitez vous créer ?");
            int nombre = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < nombre; i++) {
                System.out.println("Quel nom souhaitez vous donner à votre département : ");
                String nameDepartment = scanner.next();
                Department department = new Department(nameDepartment);
                if (highSchoolService.createDepartment(department)) {
                    System.out.println("Vous avez bien créé le département " + department.getNameDepartment());
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTeacher() {
        try {
            String lastName;
            int age;
            System.out.println("Combien d'enseignants' souhaitez vous créer ?");
            int nombre = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < nombre; i++) {
                do {
                    System.out.println("Saisissez le nom de l'enseignant (doit contenir au moins 3 caractères)  : ");
                    lastName = scanner.next();
                } while (lastName.length() < 3 || lastName == null);
                System.out.println("Saisissez le prénom de l'enseignant  : ");
                String firstName = scanner.next();
                do {
                    System.out.println("Saisissez l'âge de l'enseignant  : ");
                    age = scanner.nextInt();
                    if (age < 18) {
                        System.out.println("Vous ne pouvez pas être enregistré dans la base vous n'êtes pas majeur");
                    }
                    scanner.nextLine();
                } while (age < 18);
                System.out.println("A quel département êtes vous rattaché ?");
                Long idDepartment = scanner.nextLong();
                Department department = highSchoolService.getDepartmentById(idDepartment);
                scanner.nextLine();
                System.out.println("Etes vous professeur principal : répondez par oui ou non)");
                String reponse = scanner.next();
                boolean isPrincipalTeacher;
                if (reponse.equals("oui")) {
                    isPrincipalTeacher = true;
                } else {
                    isPrincipalTeacher = false;
                }
                System.out.println("Etes vous responsable du département : répondez par oui ou non)");
                String reponse2 = scanner.next();
                boolean isHeadTeacher = false;

                if (reponse.equals("oui")) {


                    if (!department.hasHeadTeacher()) {
                        isHeadTeacher = true;
                    } else {
                        System.out.println("Il y a déjà un headteacher dans ce département.");
                        isHeadTeacher = false;
                    }
                }
                Teacher teacher = new Teacher(lastName, firstName, age, isPrincipalTeacher, isHeadTeacher, department);
                if (highSchoolService.createTeacher(teacher)) {
                    System.out.println("Le professeur a bien été créé avec id " + teacher.getIdTeacher());
                }
                department.addTeacher(teacher);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    private void createStudent() {
        try {
            String lastName;
            String email;
            int age;
            System.out.println("Combien d'étudiants souhaitez vous créer ?");
            int nombre = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < nombre; i++) {

                do {
                    System.out.println("Saisissez le nom de l'étudiant (doit contenir au moins 3 caractères)  : ");
                    lastName = scanner.next();
                } while (lastName.length() < 3 || lastName == null);
                System.out.println("Saisissez le prénom de l'étudiant  : ");
                String firstName = scanner.next();
                System.out.println("Saisissez la date de naissance de l'étudiant (ormat dd-MM-yyyy) ");
                String date_string = scanner.next();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = formatter.parse(date_string);
                Matcher matcher;
                do {
                    System.out.println("Saisisez l'adresse mail de l'étudiant (format @gmail.com ");
                    email = scanner.next();
                    String regex = ".*@gmail\\.com$";
                    Pattern pattern = Pattern.compile(regex);
                    matcher = pattern.matcher(email);
                    if (matcher.matches()) {
                        System.out.println("L'adresse e-mail est au format attendu.");
                    } else {
                        System.out.println("L'adresse e-mail n'est pas au format attendu.");
                    }
                } while (!matcher.matches());
                Student student = new Student(lastName, firstName, date, email);
                if (highSchoolService.createStudent(student)) {
                    System.out.println("L'étudiant a bien été créé avec id " + student.getIdStudent());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    private void createSubject() {
        try {
            Long teacherId;
            System.out.println("Combien de matières souhaitez vous créer ?");
            int nombre = scanner.nextInt();
            scanner.nextLine();
            String name;
            for (int i = 0; i < nombre; i++) {
                List<Teacher> teacherList = new ArrayList<>();
                System.out.println("Saisissez le nom de la matière  : ");
                name = scanner.next();
                System.out.println("Saisissez la durée de la matière en minutes : ");
                long minutes = scanner.nextLong();
                Duration duration = Duration.ofMinutes(minutes);
                System.out.println("Saisissez le coefficient de la matière ");
                int coefficent = scanner.nextInt();
                scanner.nextLine();
                System.out.println();
                do {
                    System.out.println("Saisissez l'identifiant de l'enseignant (saisissez 0 pour arrêter) : ");
                    teacherId = scanner.nextLong();
                    if (teacherId != 0) {
                        Teacher teacher = highSchoolService.getTeacher(teacherId);
                        teacherList.add(teacher);

                    }
                } while (teacherId != 0);
                Subject subject = new Subject(name, duration, coefficent, teacherList);
                if (highSchoolService.createSubject(subject)) {
                    System.out.println("La matière a bien été créé avec id " + subject.getIdSubject());
                }
                for (Teacher teacher : subject.getTeacherList()) {
                    teacher.addSubject(subject);
                    highSchoolService.updateTeacher(teacher);
                }


            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createGrade() {
        try {
            int gradeGiven;
            System.out.println("Combien de notes souhaitez-vous ajouter ?");
            int nombre = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < nombre; i++) {
                System.out.println("Quel est l'id de l'étudiant dont vous voulez mettre une note ?");
                Long idStudent = scanner.nextLong();
                Student student = highSchoolService.getStudent(idStudent);
                System.out.println("idStudent: " + idStudent);

                if (student != null) {
                    System.out.println("l'étudiant existe");
                }

                System.out.println("Quel est l'id de la matière dont vous voulez mettre une note ?");
                Long idSubject = scanner.nextLong();
                Subject subject = highSchoolService.getSubject(idSubject);
                System.out.println("idSubject: " + idSubject);

                do {
                    System.out.println("Veuillez saisir la note (la note ne doit pas dépasser 20 et ne doit pas être négative");
                    gradeGiven = scanner.nextInt();
                    scanner.nextLine();
                } while (gradeGiven > 20 || gradeGiven < 0);

                System.out.println("Veuillez saisir un commentaire ");
                String comment = scanner.nextLine();

                Grade grade = new Grade(gradeGiven, comment, subject, student);
                student.addGrade(grade);
                subject.addGrade(grade);

                if (highSchoolService.createGrade(grade)) {
                    System.out.println("Une note a bien été ajoutée avec id " + grade.getIdGrade());
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void createClassroom() {
        try {
            List<Student> students = new ArrayList<>();
            List<Teacher> teachers = new ArrayList<>();

            System.out.println("Combien de classe souhaitez vous créer ? ");
            int nombre = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < nombre; i++) {
                System.out.println("Quel est le nom de la classe ? ");
                String name = scanner.next();

                System.out.println("==== Les niveaux ====");
                System.out.println("6 - 6ème");
                System.out.println("5 - 5ème");
                System.out.println("4 - 4ème");
                System.out.println("3 - 3ème");
                System.out.println("1 - 1ère");
                System.out.println("2 - 2nd");
                System.out.println("0 - Terminal");
                System.out.println("Quel est le niveau de la classe ?");
                int levelClassroom = scanner.nextInt();
                scanner.nextLine();

                System.out.println("A quel département la classe est associée :");
                Long id = scanner.nextLong();
                Department department = highSchoolService.getDepartmentById(id);

                long studentId;
                do {
                    System.out.println("Saisissez l'identifiant de l'élève (saisissez 0 pour arrêter) : ");
                    studentId = scanner.nextLong();
                    if (studentId != 0) {
                        Student student = highSchoolService.getStudent(studentId);
                        students.add(student);
                    }
                } while (studentId != 0);

                long teacherId;
                do {
                    System.out.println("Saisissez l'identifiant du professeur à ajouter (saisissez 0 pour arrêter) : ");
                    teacherId = scanner.nextLong();
                    if (teacherId != 0) {
                        Teacher teacher = highSchoolService.getTeacher(teacherId);
                        teachers.add(teacher);
                    }
                } while (teacherId != 0);

                Classroom classroom = new Classroom(name, levelClassroom, department, students, teachers);
                if (highSchoolService.createClassroom(classroom)) {
                    System.out.println("Une classe a bien été créée avec identifiant " + classroom.getIdClassroom());
                }

                for (Student student : classroom.getStudentList()) {
                    student.setClassroom(classroom);
                    highSchoolService.updateStudent(student);
                }

                for (Teacher teacher : classroom.getTeacherList()) {
                    teacher.addClassroom(classroom);
                    highSchoolService.updateTeacher(teacher);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void createSchedule() {
        try{
            List<TimeTable> timetables = new ArrayList<>();
            long timetableId;
            do {
                System.out.println("Saisissez l'identifiant de la timetable à associer (saisissez 0 pour arrêter) : ");
                timetableId = scanner.nextLong();
                if (timetableId != 0) {
                    TimeTable timetable = highSchoolService.getTimeTable(timetableId);
                    timetables.add(timetable);
                }
            } while (timetableId != 0);

            List<Student> students = new ArrayList<>();
            long studentId;
            do {
                System.out.println("Saisissez l'identifiant de l'étudiant à associer (saisissez 0 pour arrêter) : ");
                studentId = scanner.nextLong();
                if (studentId != 0) {
                    Student student = highSchoolService.getStudent(studentId);
                    students.add(student);
                }
            } while (studentId != 0);

            Schedule schedule = new Schedule(timetables,students);
            if(highSchoolService.createSchedule(schedule)){
                System.out.println("Un nouvel emploi du temps a été créé ");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }
    private void createTimeTable() {

        try {
            System.out.println("Combien de plage horaire souhaitez vous créer ? :");
            int nombre = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < nombre; i++) {
                System.out.println("Précisez l'id de la matière : ");
                Long id = scanner.nextLong();
                Subject subject = highSchoolService.getSubject(id);
                System.out.println("Précisez le jour de la semaine: ");
                String dayInput = scanner.nextLine().toUpperCase();
                DayOfWeek day = DayOfWeek.valueOf(dayInput);
                System.out.println("Précisez l'heure au format HH:mm");
                String time = scanner.next();
                LocalTime timeConverted = LocalTime.parse(time);
                TimeTable timeTable = new TimeTable(day, timeConverted, subject);
                if (highSchoolService.createTimetable(timeTable)) {
                    System.out.println("Une table à bien été créée");


                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayClassrooms() {
        try {
            List<Classroom> classroomList = highSchoolService.getAllClassroom();
            for (Classroom classroom : classroomList) {
                System.out.println(classroom);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void displaySubjectStudent() {
    }

    private void displayGradeStudent() {
        try {
            System.out.println("Saisissez l'id de l'élève dont vous souhaitez voir les notes :");
            Long idStudent = scanner.nextLong();
            Student student = highSchoolService.getStudent(idStudent);
            if (student != null) {
                List<Grade> gradeList = highSchoolService.getGradeStudent(idStudent);
                for (Grade grade : gradeList) {
                    System.out.println(grade);
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void displayAverageGradeStudent() {
        try {
            System.out.println("Saisissez l'id de l'élève dont vous souhaitez voir les notes :");
            Long idStudent = scanner.nextLong();
            Student student = highSchoolService.getStudent(idStudent);
            if (student != null) {
                Long moyenne = highSchoolService.getAverageGrade(idStudent);
                System.out.println("La moyennne générale de l'étudiant est de " + moyenne);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayNumberStudentDepartment() {
        try {
            System.out.println("Veuillez indiquer id du departement : ");
            Long id = scanner.nextLong();
            ;
            Department department = highSchoolService.getDepartmentById(id);
            if (department != null) {
                Long nombre = highSchoolService.displayNumberStudentDepartment(id);
                System.out.println("Le nombre d'élève dans le département est de :" + nombre);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteStudent() {
        try {
            System.out.println("Veuillez préciser l'id de l'élève à supprimer : ");
            Long id = scanner.nextLong();
            Student student = highSchoolService.getStudent(id);
            if (student != null) {
                highSchoolService.deleteStudent(id);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteClassroom() {
        try {
            System.out.println("Veuillez indiquer l'id de la classe à supprimer :");
            Long id = scanner.nextLong();
            ;
            Classroom classroom = highSchoolService.getClassroom(id);
            if (classroom != null) {
                highSchoolService.deleteClassroom(id);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteDepartment() {
        try {
            System.out.println("Veuillez indiquer l'id du département à supprimer :");
            Long id = scanner.nextLong();;
            Department department = highSchoolService.getDepartmentById(id);
            if(department != null) {
                highSchoolService.deleteDepartment(id);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void closeAll() {
        highSchoolService.close();
        scanner.close();
    }

    private void printMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Créer un département");
        System.out.println("2. Créer un enseignant");
        System.out.println("3. Créer un étudiant");
        System.out.println("4. Créer une matière");
        System.out.println("5. Créer une note");
        System.out.println("6. Créer une classe");
        System.out.println("7. Créer des tables de matière");
        System.out.println("8. Créer un emploi du temps");
        System.out.println("9. Afficher la liste des classes (sans les élèves)");
        System.out.println("10. Afficher le nombre de matière d'un élève");
        System.out.println("11. Afficher la liste des notes d'un élève (avec les détails)");
        System.out.println("12. Afficher la moyenne d'un élève");
        System.out.println("13. Afficher le nombre d'élèves d'un département");
        System.out.println("14. Afficher tous les noms des eleves d'un niveau.");
        System.out.println("15. Supprimer un élève (supprimera sa note mais pas sa classe)");
        System.out.println("16. Supprimer une classe (supprimera uniquement les élèves de cette classe)");
        System.out.println("17. Supprimer un département (supprimera toutes les classes et tous les professeurs)");
        System.out.println("0. Quitter");
        System.out.println("Saisissez votre choix :");

    }
}


