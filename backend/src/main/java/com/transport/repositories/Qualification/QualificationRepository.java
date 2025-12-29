package com.transport.repositories.Qualification;

import com.transport.entities.Qualification;
import com.transport.enums.QualificationType;
import com.transport.utils.HibernateUtil;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class QualificationRepository implements IQualificationRepository {
    @Override
    public Qualification save(Qualification qualification) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(qualification);
            transaction.commit();
            return qualification;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving qualification", e);
        }
    }

    @Override
    public Optional<Qualification> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Qualification qualification = session.get(Qualification.class, id);
            return Optional.ofNullable(qualification);
        } catch (Exception e) {
            throw new RuntimeException("Error finding qualification by id: " + id, e);
        }
    }

    @Override
    public List<Qualification> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Qualification", Qualification.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all qualifications", e);
        }
    }

    @Override
    public List<Qualification> findByEmployeeId(Long employeeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT q FROM Qualification q WHERE q.employee.id = :employeeId",
                            Qualification.class)
                    .setParameter("employeeId", employeeId)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding qualifications by employee id: " + employeeId, e);
        }
    }

    @Override
    public Optional<Qualification> findByEmployeeIdAndType(Long employeeId, QualificationType type) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Qualification qualification = session.createQuery(
                            "SELECT q FROM Qualification q WHERE q.employee.id = :employeeId AND q.qualificationType = :type",
                            Qualification.class)
                    .setParameter("employeeId", employeeId)
                    .setParameter("type", type)
                    .uniqueResult();

            return Optional.ofNullable(qualification);
        } catch (Exception e) {
            throw new RuntimeException("Error finding qualification by employee and type", e);
        }
    }

    @Override
    public boolean existsByEmployeeIdAndType(Long employeeId, QualificationType type) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(q) FROM Qualification q WHERE q.employee.id = :employeeId AND q.qualificationType = :type",
                            Long.class)
                    .setParameter("employeeId", employeeId)
                    .setParameter("type", type)
                    .uniqueResult();

            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking if qualification exists", e);
        }
    }

    @Override
    public Qualification update(Qualification qualification) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(qualification);
            transaction.commit();
            return qualification;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating qualification", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Qualification qualification = session.get(Qualification.class, id);
            if (qualification != null) {
                session.remove(qualification);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting qualification with id: " + id, e);
        }
    }

    @Override
    public void deleteByEmployeeIdAndType(Long employeeId, QualificationType type) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            int deletedCount = session.createMutationQuery(
                            "DELETE FROM Qualification q WHERE q.employee.id = :employeeId AND q.qualificationType = :type")
                    .setParameter("employeeId", employeeId)
                    .setParameter("type", type)
                    .executeUpdate();

            transaction.commit();

            if (deletedCount == 0) {
                throw new IllegalArgumentException(
                        "Qualification not found for employee " + employeeId + " with type " + type);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting qualification by employee and type", e);
        }
    }
}
