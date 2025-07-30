package dev.jamespagani.persistence;

import dev.jamespagani.config.DatabaseConfigManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public abstract class BaseDAO<T> {
    protected EntityManager getEntityManager() {
        EntityManagerFactory emf = DatabaseConfigManager.getEntityManagerFactory();
        return emf.createEntityManager();
    }

    public void save(T entity) {
        EntityManager em = getEntityManager();
        System.out.println(em.getProperties());
        try {
            System.out.println("Begin Transaction...");
            em.getTransaction().begin();
            System.out.println("Presisting...");
            em.persist(entity);
            System.out.println("Comitting...");
            em.getTransaction().commit();
            System.out.println("Done!");
        } catch (Exception e) {
            System.out.println("Rolling back and throwing errors...");
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
