package session.stateless.local;

import entity.MemberEntity;
import util.exception.MemberNotFoundException;

public interface MemberEntityControllerLocal {
    
    public MemberEntity viewMember(String identityNumber) throws MemberNotFoundException;
    // public int numBookLended(MemberEntity memberE);
}