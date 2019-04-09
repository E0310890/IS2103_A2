    package session.singleton;

import entity.BookEntity;
import entity.MemberEntity;
import entity.StaffEntity;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.Gender;

@Singleton
@LocalBean
@Startup
public class InitApp {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
        try {
            if (em.find(StaffEntity.class, 1L) == null) {
                initializeData();
            }
        } catch (Exception ex) {
        }
    }

    private void initializeData() {
        em.persist(new StaffEntity("Linda", "Chua", "manager", "password"));
        em.persist(new StaffEntity("Barbara", "Durham", "assistant", "password"));

        em.persist(new BookEntity("S18018", "The Lord of the Rings", "1954"));
        em.persist(new BookEntity("S64921", "Le Petit Prince", "1943"));
        em.persist(new BookEntity("S38101", "Harry Potter and the Philosopher's Stone", "1997"));
        em.persist(new BookEntity("S19527", "The Hobbit", "1937"));
        em.persist(new BookEntity("S63288", "And Then There Were None", "1939"));
        em.persist(new BookEntity("S32187", "Dream of the Red Chamber", "1791"));
        em.persist(new BookEntity("S74569", "The Lion, the Witch and the Wardrobe", "1950"));

        em.persist(new MemberEntity("S7483027A", "Tony", "Teo", Gender.MALE, 44, "87297373", "11 Tampines Ave 3", "123456"));
        em.persist(new MemberEntity("S8381028X", "Wendy", "Tan", Gender.FEMALE, 35, "97502837", "15 Computing Dr", "654321"));
    }
}