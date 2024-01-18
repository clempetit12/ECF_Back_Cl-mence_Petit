package dao;

import DaoImpl.Repository;
import entity.Grade;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class GradeDao implements Repository<Grade> {

    private SessionFactory sessionFactory;

    public GradeDao() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }
    @Override
    public boolean create(Grade element) {
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
        return false;
    }

    @Override
    public Grade getById(Long id) {
        Session session = null;
        try{
            session=sessionFactory.openSession();
            Grade grade = session.get(Grade.class,id);
            return grade;

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Grade> getAll() {
        return null;
    }

    @Override
    public void close() {

    }

    public List<Grade> getGradeStudent(Long id){
        Session session = null;
        Transaction transaction = null;
        try{
            session=sessionFactory.openSession();
            transaction = session.beginTransaction();
            List<Grade> gradeList = new ArrayList<>();
            Query<Grade> gradeQuery = session.createQuery("from Grade where student.idStudent = :id",Grade.class);
            gradeQuery.setParameter("id" , id);
            gradeList = gradeQuery.list();
            session.getTransaction().commit();
            return gradeList;

        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public Long getAverageGradeStudent(Long id){
        Session session = null;
        Transaction transaction = null;
        try{
            session=sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query<Long> gradeQuery = session.createQuery(
                    "select sum(g.grade * s.coefficient) / sum(s.coefficient) " +
                            "from Grade g " +
                            "inner join Subject s on g.subject.id = s.id " +
                            "where g.student.id = :id",
                    Long.class
            );

            gradeQuery.setParameter("id", id);
            Long average = gradeQuery.uniqueResult();
            session.getTransaction().commit();
            return average;

        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
