package daoImpl;

import Interfaces.Repository;
import entity.Student;
import entity.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class TeacherDao implements Repository<Teacher> {
    private SessionFactory sessionFactory;

    public TeacherDao() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }


    @Override
    public boolean create(Teacher element) {
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
    public Teacher getById(Long id) {
        Session session = null;
        try{
            session=sessionFactory.openSession();
            Teacher teacher = session.get(Teacher.class,id);
            return teacher;

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Teacher> getAll() {
        return null;
    }

    @Override
    public void close() {
        sessionFactory.close();
    }

    public boolean update(Teacher teacher) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(teacher);
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
