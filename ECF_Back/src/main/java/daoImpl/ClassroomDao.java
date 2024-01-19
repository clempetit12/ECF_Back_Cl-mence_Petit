package daoImpl;

import Interfaces.Repository;
import entity.Classroom;
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

public class ClassroomDao implements Repository<Classroom> {

    private SessionFactory sessionFactory;

    public ClassroomDao() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }


    @Override
    public boolean create(Classroom element) {
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
            Classroom classroom = getById(id);
            if (classroom != null) {
                session.delete(classroom);
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
    public Classroom getById(Long id) {
    Session session = null;
    try{
        session=sessionFactory.openSession();
        Classroom classroom = session.get(Classroom.class,id);
        return classroom;

    }catch (Exception e) {
        e.printStackTrace();
    }finally {
        session.close();
    }
    return null;
    }

    @Override
    public List<Classroom> getAll() {
        Session session = null;
        try  {
             session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            List<Classroom> classroomList = session.createQuery("from Classroom", Classroom.class).list();
            transaction.commit();
            return classroomList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void close() {
        sessionFactory.close();
    }

    public List<String> displayNameStudentLevel(int level) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            List<String> nameList = new ArrayList<>();
            Query<String> queryName = session.createQuery("select s.lastName from Student s " +
                    "inner join Classroom c on c.iDclassroom = s.classroom.iDclassroom " +
                    "where c.levelClassroom = :level");
            queryName.setParameter("level", level);
            nameList = queryName.list();
            session.getTransaction().commit();
            return nameList;

        } catch (Exception e) {
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
