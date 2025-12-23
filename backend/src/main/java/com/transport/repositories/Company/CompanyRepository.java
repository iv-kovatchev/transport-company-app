package com.transport.repositories.Company;

import com.transport.entities.Company;
import com.transport.utils.HibernateUtil;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CompanyRepository implements ICompanyRepository {
    @Override
    public Company save(Company company) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(company);
            transaction.commit();
            return company;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Error saving company", e);
        }
    }

    @Override
    public Optional<Company> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Company company = findByIdWithDetails(session, id);
            return Optional.ofNullable(company);
        } catch (Exception e) {
            throw new RuntimeException("Error finding company by id: " + id, e);
        }
    }

    @Override
    public List<Company> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return findAllWithDetails(session);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all companies", e);
        }
    }

    @Override
    public Company update(Company company) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(company);
            transaction.commit();
            return company;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Error updating company", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Company company = session.get(Company.class, id);

            if (company != null) {
                session.remove(company);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting company by id: " + id, e);
        }
    }

    @Override
    public boolean existsByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(c) FROM Company c WHERE c.name = :name", Long.class
            );
            query.setParameter("name", name);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking company name existence", e);
        }
    }

    @Override
    public boolean existsByRegistrationNumber(String registrationNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(c) FROM Company c WHERE c.registrationNumber = :regNum", Long.class
            );
            query.setParameter("regNum", registrationNumber);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking registration number existence", e);
        }
    }

    private Company findByIdWithDetails(Session session, Long id) {
        return session.createQuery(
                        "SELECT c FROM Company c " +
                                "LEFT JOIN FETCH c.clients " +
                                // "LEFT JOIN FETCH c.employees " +      // Добавяш по-късно
                                // "LEFT JOIN FETCH c.vehicles " +       // Добавяш по-късно
                                // "LEFT JOIN FETCH c.transports " +     // Добавяш по-късно
                                "WHERE c.id = :id",
                        Company.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    private List<Company> findAllWithDetails(Session session) {
        return session.createQuery(
                        "SELECT DISTINCT c FROM Company c " +
                                "LEFT JOIN FETCH c.clients",
                        // "LEFT JOIN FETCH c.employees " +
                        // "LEFT JOIN FETCH c.vehicles " +
                        // "LEFT JOIN FETCH c.transports",
                        Company.class)
                .getResultList();
    }
}
