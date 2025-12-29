package com.transport.repositories.Employee;

import com.transport.entities.Employee;
import com.transport.utils.HibernateUtil;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class EmployeeRepository implements IEmployeeRepository {
    @Override
    public Employee save(Employee employee) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(employee);
            transaction.commit();
            return employee;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving employee", e);
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee employee = session.get(Employee.class, id);
            return Optional.ofNullable(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error finding employee by id: " + id, e);
        }
    }

    @Override
    public Optional<Employee> findByIdWithCompany(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee employee = session.createQuery(
                            "SELECT e FROM Employee e " +
                                    "LEFT JOIN FETCH e.company " +
                                    "WHERE e.id = :id",
                            Employee.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return Optional.ofNullable(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error finding employee with company by id: " + id, e);
        }
    }

    @Override
    public List<Employee> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all employees", e);
        }
    }

    @Override
    public List<Employee> findAllWithCompanyAndQualifications() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT e FROM Employee e " +
                                    "LEFT JOIN FETCH e.company " +
                                    "LEFT JOIN FETCH e.qualifications",
                            Employee.class)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all employees with company and qualifications", e);
        }
    }

    @Override
    public Optional<Employee> findByIdWithQualifications(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee employee = session.createQuery(
                            "SELECT e FROM Employee e " +
                                    "LEFT JOIN FETCH e.qualifications " +
                                    "WHERE e.id = :id",
                            Employee.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return Optional.ofNullable(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error finding employee with qualifications by id: " + id, e);
        }
    }

    @Override
    public Optional<Employee> findByIdWithCompanyAndQualifications(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee employee = session.createQuery(
                            "SELECT e FROM Employee e " +
                                    "LEFT JOIN FETCH e.company " +
                                    "LEFT JOIN FETCH e.qualifications " +
                                    "WHERE e.id = :id",
                            Employee.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return Optional.ofNullable(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error finding employee with company and qualifications by id: " + id, e);
        }
    }

    @Override
    public Employee update(Employee employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(employee);
            transaction.commit();
            return employee;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating employee", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                session.remove(employee);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting employee with id: " + id, e);
        }
    }


}
