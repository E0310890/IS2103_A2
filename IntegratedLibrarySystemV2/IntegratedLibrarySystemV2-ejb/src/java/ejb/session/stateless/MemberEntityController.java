package ejb.session.stateless;

import Entity.MemberEntity;
import dao.MemberEntityManager;
import javax.ejb.Stateless;

@Stateless
public class MemberEntityController implements MemberEntityControllerRemote, MemberEntityControllerLocal {

private final MemberEntityManager memberEntityManager;
    
    public MemberEntityController(){
        memberEntityManager = new MemberEntityManager();
    }
    
    @Override 
    public MemberEntity createNewMember(MemberEntity memberEntity){
        return memberEntityManager.createNewMember(memberEntity);
    }
}
