package hiber.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class App {

    public static void main(String[] args) {

        SessionFactory sessionFactory = null;

        try {
            sessionFactory = new Configuration()
                    .addAnnotatedClass(Student.class)
                    .buildSessionFactory();

            // =========================
            // INSERT
            // =========================

            try (Session session = sessionFactory.openSession()) {

                Transaction transaction = session.beginTransaction();

                try {
                    Student student = new Student(
                            "Jhon Doe",
                            "student@example.com",
                            "Bio"
                    );

                    session.persist(student);

                    transaction.commit();

                    System.out.println("Student inserted successfully.");
                    System.out.println("Generated ID: " + student.getId());

                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw e;
                }
            }


            // =========================
            // READ
            // =========================

            try (Session session = sessionFactory.openSession()) {

                Student student = session.get(Student.class, 1);

                System.out.println("Student retrieved:");
                System.out.println(student);
            }


            // =========================
            // UPDATE
            // =========================

            try (Session session = sessionFactory.openSession()) {

                Transaction transaction = session.beginTransaction();

                try {
                    Student student = session.get(Student.class, 1);

                    if (student != null) {
                        student.setCourse("Updated Course");
                    }

                    transaction.commit();

                    System.out.println("Student updated successfully.");

                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw e;
                }
            }


            // =========================
            // VERIFY UPDATE
            // =========================

            try (Session session = sessionFactory.openSession()) {

                Student student = session.get(Student.class, 1);

                System.out.println("Updated student:");
                System.out.println(student);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }
}