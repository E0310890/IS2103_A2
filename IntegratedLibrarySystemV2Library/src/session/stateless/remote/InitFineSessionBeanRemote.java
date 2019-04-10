package session.stateless.remote;

import javax.ejb.Remote;

@Remote
public interface InitFineSessionBeanRemote {
    public void setUpFine();
}
