package com.transport.repositories.Vehicle;

import com.transport.entities.Vehicle;
import com.transport.utils.HibernateUtil;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class VehicleRepository implements IVehicleRepository {
    @Override
    public Vehicle save(Vehicle vehicle) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(vehicle);
            transaction.commit();
            return vehicle;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving vehicle", e);
        }
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Vehicle vehicle = session.get(Vehicle.class, id);
            return Optional.ofNullable(vehicle);
        } catch (Exception e) {
            throw new RuntimeException("Error finding company by id: " + id, e);
        }
    }

    @Override
    public Optional<Vehicle> findByIdWithCompany(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Vehicle vehicle = session.createQuery(
                            "SELECT v FROM Vehicle v " +
                                    "LEFT JOIN FETCH v.company " +
                                    "WHERE v.id = :id",
                            Vehicle.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return Optional.ofNullable(vehicle);
        } catch (Exception e) {
            throw new RuntimeException("Error finding vehicle with company by id: " + id, e);
        }
    }

    @Override
    public List<Vehicle> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Vehicle", Vehicle.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all vehicles", e);
        }
    }

    @Override
    public List<Vehicle> findAllWithCompany() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT v FROM Vehicle v LEFT JOIN FETCH v.company",
                            Vehicle.class)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all vehicles with company", e);
        }
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(vehicle);
            transaction.commit();
            return vehicle;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating vehicle", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Vehicle vehicle = session.get(Vehicle.class, id);
            if (vehicle != null) {
                session.remove(vehicle);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting vehicle with id: " + id, e);
        }
    }

    @Override
    public boolean existsByLicensePlateAndIdNot(String licensePlate, Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(v) FROM Vehicle v WHERE v.licensePlate = :licensePlate AND v.id != :id",
                            Long.class)
                    .setParameter("licensePlate", licensePlate)
                    .setParameter("id", id)
                    .uniqueResult();

            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking if vehicle exists by license plate", e);
        }
    }

    @Override
    public List<Vehicle> findByCompanyId(Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT v FROM Vehicle v WHERE v.company.id = :companyId",
                            Vehicle.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching vehicles by company id: " + companyId, e);
        }
    }
}
