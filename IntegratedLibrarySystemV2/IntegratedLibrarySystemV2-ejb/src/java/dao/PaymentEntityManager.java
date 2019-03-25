/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.MemberEntity;
import entity.PaymentEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 *
 * @author lester
 */
public class PaymentEntityManager {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegratedLibrarySystemV2-ejbPU");
    private final EntityManager em = emf.createEntityManager();

    public PaymentEntityManager() {
    }

    public void update(PaymentEntity pe) throws PersistenceException {
        try {
            
            if (pe.getPaymentID() != null) {
                em.joinTransaction();
                em.merge(pe);
            }
        } catch (PersistenceException ex) {
            throw ex;
        }
    }
}
