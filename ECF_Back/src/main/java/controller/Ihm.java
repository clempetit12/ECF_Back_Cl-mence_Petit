package controller;

import dao.*;
import entity.*;
import service.HighSchoolService;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Ihm() {
        classroomDao = new ClassroomDao();
        departmentDao = new DepartmentDao();
        gradeDao = new GradeDao();
        scheduleDao = new ScheduleDao();
        studentDao = new StudentDao();
        subjectDao = new SubjectDao();
        teacherDao = new TeacherDao();
        highSchoolService = new HighSchoolService(classroomDao, departmentDao, gradeDao, scheduleDao, studentDao, subjectDao, teacherDao);
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
                    createSchedule();
                    break;
                case 8:
                    displayClassrooms();
                    break;
                case 9:
                    displaySubjectStudent();
                    break;
                case 10:
                    displayGradeStudent();
                    break;
                case 11:
                    displayAverageGradeStudent();
                    break;
                case 12:
                    displayNumberStudentDepartment();
                    break;
                case 13:
                    deleteStudent();
                    break;
                case 14:
                    deleteClassroom();
                    break;
                case 15:
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
            List<Teacher> teacherList = new ArrayList<>();
            System.out.println("Combien de matières souhaitez vous créer ?");
            int nombre = scanner.nextInt();
            scanner.nextLine();
            String name;
            for (int i = 0; i < nombre; i++) {
                System.out.println("Saisissez le nom de la matière  : ");
                name = scanner.next();
                System.out.println("Saisissez la durée de la matière en minutes : ");
                long minutes = scanner.nextLong();
                Duration duration = Duration.ofMinutes(minutes);
                System.out.println("Saisissez le coefficient de la matière ");
                int coefficent = scanner.nextInt();
                System.out.println();
                do {
                    System.out.println("Saisissez l'identifiant de l'enseignant (saisissez 0 pour arrêter) : ");
                    teacherId = scanner.nextLong();
                    if (teacherId != 0) {
                        Teacher teacher = highSchoolService.getTeacher(teacherId);
                        teacherList.add(teacher);
                    }
                } while (teacherId != 0);
                Subject subject = new Subject(name,duration,coefficent,teacherList);
                if (highSchoolService.createSubject(subject)) {
                    System.out.println("La matière a bien été créé avec id " + subject.getIdSubject());
                }
                for (Teacher teacher : teacherList) {
                    teacher.addSubject(subject);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createGrade() {
        try {
            int gradeGiven;
            System.out.println("Combien de notes souhaitez vous ajouter ?");
            int nombre = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < nombre; i++) {
                System.out.println("Quel est l'id de l'étudiant dont vous voulez mettre une note ?");
                Long idStudent = scanner.nextLong();
                Student student = highSchoolService.getStudent(idStudent);
                System.out.println("Quel est l'id de la matière dont vous voulez mettre une note ?");
                Long idSubject = scanner.nextLong();
                Subject subject = highSchoolService.getSubject(idSubject);
                do {
                    System.out.println("Veuillez saisir la note (la note ne doit pas dépasser 20 et ne doit pas être négative");
                    gradeGiven = scanner.nextInt();
                    scanner.nextLine();
                } while (gradeGiven > 20 || gradeGiven < 0);
                System.out.println("Veuillez saisir un commentaire ");
                String comment = scanner.nextLine();
                Grade grade = new Grade(gradeGiven, comment, subject, student);
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
                System.out.println("Quel est le niveau de la classe ?");
                String levelClassroom = scanner.next();
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
                    System.out.println("Saisissez l'identifiant des professeurs à ajouter (saisissez 0 pour arrêter) : ");
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
                for (Student student : students) {
                    student.setClassroom(classroom);
                }
                for (Teacher teacher : teachers) {
                    teacher.addClassroom(classroom);
                }

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    private void createSchedule() {
    }

    private void displayClassrooms() {
    }

    private void displaySubjectStudent() {
    }

    private void displayGradeStudent() {
    }

    private void displayAverageGradeStudent() {
    }

    private void displayNumberStudentDepartment() {
    }

    private void deleteStudent() {
    }

    private void deleteClassroom() {
    }

    private void deleteDepartment() {
    }

    private void closeAll() {
    }

    private void printMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Créer un département");
        System.out.println("2. Créer un enseignant");
        System.out.println("3. Créer un étudiant");
        System.out.println("4. Créer une matière");
        System.out.println("5. Créer une note");
        System.out.println("6. Créer une classe");
        System.out.println("7. Créer un emploi du temps");
        System.out.println("8. Afficher la liste des classes (sans les élèves)");
        System.out.println("9. Afficher le nombre de matière d'un élève");
        System.out.println("10. Afficher la liste des notes d'un élève (avec les détails)");
        System.out.println("11. Afficher la moyenne d'un élève");
        System.out.println("12. Afficher le nombre d'élèves d'un département");
        System.out.println("13. Supprimer un élève (supprimera sa note mais pas sa classe)");
        System.out.println("14. Supprimer une classe (supprimera uniquement les élèves de cette classe)");
        System.out.println("15. Supprimer un département (supprimera toutes les classes et tous les professeurs)");
        System.out.println("0. Quitter");
        System.out.println("Saisissez votre choix :");

    }
}


