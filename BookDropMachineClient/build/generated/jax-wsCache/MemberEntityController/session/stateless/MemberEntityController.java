
package session.stateless;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebService(name = "MemberEntityController", targetNamespace = "http://stateless.session/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MemberEntityController {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns session.stateless.Member
     * @throws InvalidLoginCredentialException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "memberLogin", targetNamespace = "http://stateless.session/", className = "session.stateless.MemberLogin")
    @ResponseWrapper(localName = "memberLoginResponse", targetNamespace = "http://stateless.session/", className = "session.stateless.MemberLoginResponse")
    @Action(input = "http://stateless.session/MemberEntityController/memberLoginRequest", output = "http://stateless.session/MemberEntityController/memberLoginResponse", fault = {
        @FaultAction(className = InvalidLoginCredentialException_Exception.class, value = "http://stateless.session/MemberEntityController/memberLogin/Fault/InvalidLoginCredentialException")
    })
    public Member memberLogin(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1)
        throws InvalidLoginCredentialException_Exception
    ;

}
