package session.stateless.local;

import javax.ejb.Local;

@Local
public interface InitFineSessionBeanLocal {
    public void setUpFine();
}
