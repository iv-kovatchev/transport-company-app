package com.transport.repositories.Client;

import com.transport.entities.Client;
import com.transport.utils.HibernateUtil;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class ClientRepository implements IClientRepository {
    @Override
    public Client save(Client client) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
            return client;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Error saving client", e);
        }
    }

    @Override
    public Optional<Client> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Client client = session.get(Client.class, id);
            return Optional.ofNullable(client);
        } catch (Exception e) {
            throw new RuntimeException("Error finding company by id: " + id, e);
        }
    }

    @Override
    public Optional<Client> findByIdWithCompany(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Client client = session.createQuery(
                            "SELECT c FROM Client c " +
                                    "LEFT JOIN FETCH c.company " +
                                    "WHERE c.id = :id",
                            Client.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return Optional.ofNullable(client);
        } catch (Exception e) {
            throw new RuntimeException("Error finding client with company by id: " + id, e);
        }
    }

    @Override
    public List<Client> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Client", Client.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all clients", e);
        }
    }

    @Override
    public List<Client> findAllWithCompany() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT c FROM Client c LEFT JOIN FETCH c.company",
                            Client.class)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all clients with company", e);
        }
    }

    @Override
    public Client update(Client client) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(client);
            transaction.commit();
            return client;
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Error updating client", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Client client = session.get(Client.class, id);
            if (client != null) {
                session.remove(client);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting client with id: " + id, e);
        }
    }

    @Override
    public boolean existsByNameAndCompany(String name, Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(c) FROM Client c WHERE c.name = :name AND c.company.id = :companyId",
                            Long.class)
                    .setParameter("name", name)
                    .setParameter("companyId", companyId)
                    .uniqueResult();

            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking if client exists by name and company", e);
        }
    }

    @Override
    public List<Client> findByCompanyId(Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT c FROM Client c WHERE c.company.id = :companyId",
                            Client.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching clients by company id: " + companyId, e);
        }
    }
}
