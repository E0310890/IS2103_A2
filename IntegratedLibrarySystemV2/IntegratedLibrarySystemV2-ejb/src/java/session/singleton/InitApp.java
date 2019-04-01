package session.singleton;

import dao.LendEntityManager;
import dao.PaymentEntityManager;
import entity.BookEntity;
import entity.LendingEntity;
import entity.MemberEntity;
import entity.PaymentEntity;
import entity.StaffEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.stateless.local.MemberEntityControllerLocal;
import util.enumeration.Gender;

@Singleton
@LocalBean
@Startup
public class InitApp {

    @PersistenceContext
    private EntityManager em;
    private static LendEntityManager lem;
    private static PaymentEntityManager pem;
    private static MemberEntityControllerLocal mec;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    public void postConstruct() {
        try {
            if (em.find(StaffEntity.class, 1L) == null) {
                initializeData();
            }
        } catch (Exception ex) {
        }
        
        updateOverdue();
    }
    
    private void updateOverdue(){
        List<LendingEntity> lendList = lem.retrieveAll();
        List<PaymentEntity> paymentList = pem.retrieveAll();
        Date currDate = new Date();
        
        for(LendingEntity le : lendList){
            //if overdue // DEBUGGING SET CURRENT DATE, yyyy-MM-dd
            if(sdf.format(le.getDueDate()).compareTo("2019-04-01"/*sdf.format(currDate)*/) < 0){
                if(paymentList.stream()
                              .noneMatch(p -> p.getLendID().equals(le.getLendID()))){
                    mec.createFine(le);
                }
            };
        }
        
        for(PaymentEntity pe : paymentList){
            
        }
    }

    private void initializeData() {
        em.persist(new StaffEntity("Linda", "Chua", "manager", "password"));
        em.persist(new StaffEntity("Barbara", "Durham", "assistant", "password"));

        em.persist(new BookEntity("The Lord of the Rings", "S18018", "1954"));
        em.persist(new BookEntity("Le Petit Prince", "S64921", "1943"));
        em.persist(new BookEntity("Harry Potter and the Philosopher's Stone", "S38101", "1997"));
        em.persist(new BookEntity("The Hobbit", "S19527", "1937"));
        em.persist(new BookEntity("And Then There Were None", "S63288", "1939"));
        em.persist(new BookEntity("Dream of the Red Chamber", "S32187", "1791"));
        em.persist(new BookEntity("The Lion, the Witch and the Wardrobe", "S74569", "1950"));

        em.persist(new MemberEntity("S7483027A", "Tony", "Teo", Gender.MALE, 44, "87297373", "11 Tampines Ave 3", "123456"));
        em.persist(new MemberEntity("S8381028X", "Wendy", "Tan", Gender.FEMALE, 35, "97502837", "15 Computing Dr", "654321"));
    }

}
