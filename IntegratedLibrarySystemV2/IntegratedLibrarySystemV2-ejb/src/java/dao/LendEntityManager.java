package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class LendEntityManager {
    
    //Code when need to use Entity manager outside of container
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("IntegratedLibrarySystemV2-ejbPU");
    private EntityManager entityManager = factory.createEntityManager();

    public LendEntityManager() {
    }
    
    
    
}
