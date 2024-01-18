package dao;

import DaoImpl.Repository;
import entity.Grade;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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
        return null;
    }

    @Override
    public List<Grade> getAll() {
        return null;
    }

    @Override
    public void close() {

    }
}
