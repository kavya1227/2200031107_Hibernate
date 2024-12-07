package com.klef.jfsd.exam;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.klef.jfsd.exam.Customer;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ClientDemo {

    public static void main(String args[]) {
        ClientDemo operations = new ClientDemo();
        operations.addCustomer();
       // operations.restrictDemo();
         //operations.hcql();
    }

    public void addCustomer() {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        Transaction transaction = session.beginTransaction();

        // Adding sample Customer objects
        Customer c1 = new Customer();
        c1.setName("Vasavi");
        c1.setEmail("vasavi@gmail.com");
        c1.setAge(29);
        c1.setLocation("Vijayawada");

        Customer c2 = new Customer();
        c2.setName("Kavya");
        c2.setEmail("Kavya@gmail.com");
        c2.setAge(35);
        c2.setLocation("Chennai");

        session.persist(c1);
        session.persist(c2);
        transaction.commit();

        System.out.println("Customers Added Successfully");

        session.close();
        sf.close();
    }

    public void restrictDemo() {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery<Customer> cq=cb.createQuery(Customer.class);
    
        Root<Customer> root=cq.from(Customer.class);

        // Restriction example: greater than
        cq.select(root).where(cb.gt(root.get("age"), 30));

        List<Customer> customerList = session.createQuery(cq).getResultList();

        System.out.println("Customers with age greater than 30:");
        for (Customer c : customerList) {
            System.out.println(c.toString());
        }

        session.close();
        sf.close();
    }

    public void hcql() {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> root = cq.from(Customer.class);

        cq.select(root).where(cb.greaterThan(root.get("age"), 25));
        
        cq.orderBy(cb.asc(root.get("age")));

        List<Customer> customerList = session.createQuery(cq).getResultList();

        System.out.println("Customers with age greater than 25 (sorted by age):");
        for (Customer c : customerList) {
            System.out.println(c.toString());
        }

        session.close();
        sf.close();
    }
}
