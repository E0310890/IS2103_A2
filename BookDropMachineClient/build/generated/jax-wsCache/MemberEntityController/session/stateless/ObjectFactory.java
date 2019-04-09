
package session.stateless;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the session.stateless package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InvalidLoginCredentialException_QNAME = new QName("http://stateless.session/", "InvalidLoginCredentialException");
    private final static QName _MemberLogin_QNAME = new QName("http://stateless.session/", "memberLogin");
    private final static QName _MemberLoginResponse_QNAME = new QName("http://stateless.session/", "memberLoginResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: session.stateless
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InvalidLoginCredentialException }
     * 
     */
    public InvalidLoginCredentialException createInvalidLoginCredentialException() {
        return new InvalidLoginCredentialException();
    }

    /**
     * Create an instance of {@link MemberLogin }
     * 
     */
    public MemberLogin createMemberLogin() {
        return new MemberLogin();
    }

    /**
     * Create an instance of {@link MemberLoginResponse }
     * 
     */
    public MemberLoginResponse createMemberLoginResponse() {
        return new MemberLoginResponse();
    }

    /**
     * Create an instance of {@link Member }
     * 
     */
    public Member createMember() {
        return new Member();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidLoginCredentialException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "InvalidLoginCredentialException")
    public JAXBElement<InvalidLoginCredentialException> createInvalidLoginCredentialException(InvalidLoginCredentialException value) {
        return new JAXBElement<InvalidLoginCredentialException>(_InvalidLoginCredentialException_QNAME, InvalidLoginCredentialException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "memberLogin")
    public JAXBElement<MemberLogin> createMemberLogin(MemberLogin value) {
        return new JAXBElement<MemberLogin>(_MemberLogin_QNAME, MemberLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "memberLoginResponse")
    public JAXBElement<MemberLoginResponse> createMemberLoginResponse(MemberLoginResponse value) {
        return new JAXBElement<MemberLoginResponse>(_MemberLoginResponse_QNAME, MemberLoginResponse.class, null, value);
    }

}
