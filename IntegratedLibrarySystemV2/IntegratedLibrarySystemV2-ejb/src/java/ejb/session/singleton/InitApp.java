package ejb.session.singleton;

import Entity.BookEntity;
import Entity.MemberEntity;
import Entity.StaffEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.exception.StaffNotFoundException;
import ejb.session.stateless.StaffEntityControllerLocal;
import ejb.session.stateless.MemberEntityControllerLocal;
import ejb.session.stateless.BookEntityControllerLocal;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Singleton
@LocalBean
@Startup
public class InitApp {
    
    
    @PersistenceContext(unitName = "IntegratedLibrarySystemV2-ejbPU") 
    private EntityManager em;

    @EJB
    private StaffEntityControllerLocal staffEntityControllerLocal;
    @EJB
    private BookEntityControllerLocal bookEntityControllerLocal;
    @EJB
    private MemberEntityControllerLocal memberEntityControllerLocal;

    public InitApp(){
    }
    
    @PostConstruct
    public void postConstruct(){
        if(em.find(StaffEntity.class, 1l) == null)
        {
            initializeData();
        }
    }
    
    private void initializeData(){
        /* Staff Initializing */
        StaffEntity staff = new StaffEntity("Linda", "Chua", "manager","password");
        em.persist(staff);
        em.flush();     
        staff = new StaffEntity("Barbara", "Durham", "assistant", "password");        
        em.persist(staff);
        em.flush();        
       
        /* Book Initializing */
        BookEntity book = new BookEntity("The Lord of the Rings","S18018","1954");
        em.persist(book);
        em.flush();  
        book = new BookEntity("Le Petit Prince","S64921","1943");
        em.persist(book);
        em.flush();
        book = new BookEntity("Harry Potter and the Philosopher's Stone","S38101","1997");
        em.persist(book);
        em.flush();                
        book = new BookEntity("The Hobbit","S19527","1937");
        em.persist(book);
        em.flush(); 
        book = new BookEntity("And Then There Were None","S63288","1939");
        em.persist(book);
        em.flush();         
        book = new BookEntity("Dream of the Red Chamber","S32187","1791");
        em.persist(book);
        em.flush(); 
        book = new BookEntity("The Lion, the Witch and the Wardrobe","S74569","1950");
        em.persist(book);
        em.flush();         

        /* Member Initializing */
        MemberEntity member = new MemberEntity("S7483027A", "Tony", "Teo", "Male", 44, "87297373", "11 Tampines Ave 3", "123456");
        em.persist(member);
        em.flush();     
        member = new MemberEntity("S8381028X", "Wendy", "Tan", "Female", 35, "97502837", "15 Computing Dr", "654321");
        em.persist(member);
        em.flush();                   
    }    
}
