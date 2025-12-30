package com.transport.repositories.Transport;

import com.transport.entities.Transport;
import com.transport.utils.HibernateUtil;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class TransportRepository implements ITransportRepository {
    @Override
    public Transport save(Transport transport) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(transport);
            transaction.commit();
            return transport;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving transport", e);
        }
    }

    @Override
    public Optional<Transport> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transport transport = session.get(Transport.class, id);
            return Optional.ofNullable(transport);
        } catch (Exception e) {
            throw new RuntimeException("Error finding transport by id: " + id, e);
        }
    }

    @Override
    public Optional<Transport> findByIdWithRelationships(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transport transport = session.createQuery(
                            "SELECT t FROM Transport t " +
                                    "LEFT JOIN FETCH t.company " +
                                    "LEFT JOIN FETCH t.client " +
                                    "LEFT JOIN FETCH t.vehicle " +
                                    "LEFT JOIN FETCH t.driver d " +
                                    "LEFT JOIN FETCH d.qualifications " +
                                    "WHERE t.id = :id",
                            Transport.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return Optional.ofNullable(transport);
        } catch (Exception e) {
            throw new RuntimeException("Error finding transport with relationships by id: " + id, e);
        }
    }

    @Override
    public List<Transport> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Transport", Transport.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all transports", e);
        }
    }

    @Override
    public List<Transport> findAllWithRelationships() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT t FROM Transport t " +
                                    "LEFT JOIN FETCH t.company " +
                                    "LEFT JOIN FETCH t.client " +
                                    "LEFT JOIN FETCH t.vehicle " +
                                    "LEFT JOIN FETCH t.driver d " +
                                    "LEFT JOIN FETCH d.qualifications",
                            Transport.class)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all transports with relationships", e);
        }
    }

    @Override
    public List<Transport> findAllByPaymentStatus(Boolean isPaid) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT t FROM Transport t " +
                                    "LEFT JOIN FETCH t.company " +
                                    "LEFT JOIN FETCH t.client " +
                                    "LEFT JOIN FETCH t.vehicle " +
                                    "LEFT JOIN FETCH t.driver d " +
                                    "LEFT JOIN FETCH d.qualifications " +
                                    "WHERE t.isPaid = :isPaid",
                            Transport.class)
                    .setParameter("isPaid", isPaid)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding transports by payment status", e);
        }
    }

    @Override
    public List<Transport> findAllByCompanyIdAndPaymentStatus(Long companyId, Boolean isPaid) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT t FROM Transport t " +
                                    "LEFT JOIN FETCH t.company " +
                                    "LEFT JOIN FETCH t.client " +
                                    "LEFT JOIN FETCH t.vehicle " +
                                    "LEFT JOIN FETCH t.driver d " +
                                    "LEFT JOIN FETCH d.qualifications " +
                                    "WHERE t.company.id = :companyId AND t.isPaid = :isPaid",
                            Transport.class)
                    .setParameter("companyId", companyId)
                    .setParameter("isPaid", isPaid)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding transports by company and payment status", e);
        }
    }

    @Override
    public boolean existsByVehicleId(Long vehicleId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(t) FROM Transport t WHERE t.vehicle.id = :vehicleId",
                            Long.class)
                    .setParameter("vehicleId", vehicleId)
                    .uniqueResult();

            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking if vehicle has transports", e);
        }
    }

    @Override
    public boolean existsByDriverId(Long driverId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(t) FROM Transport t WHERE t.driver.id = :driverId",
                            Long.class)
                    .setParameter("driverId", driverId)
                    .uniqueResult();

            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking if driver has transports", e);
        }
    }

    @Override
    public boolean existsByClientId(Long clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(t) FROM Transport t WHERE t.client.id = :clientId",
                            Long.class)
                    .setParameter("clientId", clientId)
                    .uniqueResult();

            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking if client has transports", e);
        }
    }

    @Override
    public Transport update(Transport transport) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(transport);
            transaction.commit();
            return transport;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating transport", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Transport transport = session.get(Transport.class, id);
            if (transport != null) {
                session.remove(transport);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting transport with id: " + id, e);
        }
    }

    @Override
    public List<Transport> findAllForExport() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT t FROM Transport t " +
                                    "LEFT JOIN FETCH t.company " +
                                    "LEFT JOIN FETCH t.client " +
                                    "LEFT JOIN FETCH t.vehicle " +
                                    "LEFT JOIN FETCH t.driver d " +
                                    "ORDER BY t.id ASC",
                            Transport.class)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all transports for export", e);
        }
    }
}
