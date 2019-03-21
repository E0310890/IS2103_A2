package ejb.session.stateless;

import Entity.MemberEntity;
import javax.ejb.Local;


@Local
public interface MemberEntityControllerLocal {
    
    MemberEntity createNewMember(MemberEntity memberEntity);
    
}
