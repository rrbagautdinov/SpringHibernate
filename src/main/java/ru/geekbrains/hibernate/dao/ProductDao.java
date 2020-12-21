package ru.geekbrains.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.geekbrains.hibernate.PrepareDataApp;
import ru.geekbrains.hibernate.model.Product;

public class ProductDao {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        try {
            init();
            createProduct(1L, "Кристина", 10000000);
            readAndPrintProduct(1L);
            updateProduct(1L, "Шапалах", 1);
            deleteProduct(1L);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    public static void init() {
        PrepareDataApp.forcePrepareData();
        sessionFactory = new Configuration()
                .configure("configs/crud/hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public static void shutdown() {
        sessionFactory.close();
    }

    public static void createProduct(Long id, String name, int price) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Product product = new Product(id, name, price);
            session.save(product);
            session.getTransaction().commit();
            session.close();
        }
    }

    public static void readAndPrintProduct(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.getTransaction().commit();
            System.out.println(product.toString());
            session.close();
        }
    }

    public static void updateProduct(Long id, String name, int price) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            product.setName(name);
            product.setPrice(price);
            session.getTransaction().commit();
            session.close();
        }
    }

    public static void deleteProduct(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.delete(product);
            session.getTransaction().commit();
            session.close();
        }
    }
}
