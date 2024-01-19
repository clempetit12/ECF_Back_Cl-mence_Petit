package daoImpl;

import Interfaces.Repository;
import entity.Department;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDao implements Repository<Student> {

    private SessionFactory sessionFactory;

    public StudentDao() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }
    @Override
    public boolean create(Student element) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(element);
            session.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Student student = getById(id);
            if (student != null) {
                session.delete(student);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();

        }
        return false;
    }

    @Override
    public Student getById(Long id) {
        Session session = null;
        try{
            session=sessionFactory.openSession();
            Student student = session.get(Student.class,id);
            return student;

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Student> getAll() {
        return null;
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
    public Long displayNumberStudentDepartment(Long id) {
        Session session = null;
        try{
            session=sessionFactory.openSession();
            Department department = session.get(Department.class,id);
            Query<Long> longQuery = session.createQuery(
                    "select count(s.idStudent) from Student s " +
                    "inner join Classroom c on c.iDclassroom = s.classroom.iDclassroom " +
                            "inner join Department d on d.idDepartment = c.department.idDepartment " +
                            "where d.idDepartment = :departmentId",

                    Long.class);
            longQuery.setParameter("departmentId",id);
            Long number = longQuery.uniqueResult();
            return number;

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }


    public boolean update(Student student) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
         session.merge(student);
         transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();

        }
        return false;
    }
}
