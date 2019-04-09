
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

    private final static QName _FineNotFoundException_QNAME = new QName("http://stateless.session/", "FineNotFoundException");
    private final static QName _MemberNotFoundException_QNAME = new QName("http://stateless.session/", "MemberNotFoundException");
    private final static QName _PayFine_QNAME = new QName("http://stateless.session/", "payFine");
    private final static QName _PayFineResponse_QNAME = new QName("http://stateless.session/", "payFineResponse");
    private final static QName _ViewFine_QNAME = new QName("http://stateless.session/", "viewFine");
    private final static QName _ViewFineResponse_QNAME = new QName("http://stateless.session/", "viewFineResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: session.stateless
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FineNotFoundException }
     * 
     */
    public FineNotFoundException createFineNotFoundException() {
        return new FineNotFoundException();
    }

    /**
     * Create an instance of {@link MemberNotFoundException }
     * 
     */
    public MemberNotFoundException createMemberNotFoundException() {
        return new MemberNotFoundException();
    }

    /**
     * Create an instance of {@link PayFine }
     * 
     */
    public PayFine createPayFine() {
        return new PayFine();
    }

    /**
     * Create an instance of {@link PayFineResponse }
     * 
     */
    public PayFineResponse createPayFineResponse() {
        return new PayFineResponse();
    }

    /**
     * Create an instance of {@link ViewFine }
     * 
     */
    public ViewFine createViewFine() {
        return new ViewFine();
    }

    /**
     * Create an instance of {@link ViewFineResponse }
     * 
     */
    public ViewFineResponse createViewFineResponse() {
        return new ViewFineResponse();
    }

    /**
     * Create an instance of {@link Fine }
     * 
     */
    public Fine createFine() {
        return new Fine();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FineNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "FineNotFoundException")
    public JAXBElement<FineNotFoundException> createFineNotFoundException(FineNotFoundException value) {
        return new JAXBElement<FineNotFoundException>(_FineNotFoundException_QNAME, FineNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "MemberNotFoundException")
    public JAXBElement<MemberNotFoundException> createMemberNotFoundException(MemberNotFoundException value) {
        return new JAXBElement<MemberNotFoundException>(_MemberNotFoundException_QNAME, MemberNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PayFine }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "payFine")
    public JAXBElement<PayFine> createPayFine(PayFine value) {
        return new JAXBElement<PayFine>(_PayFine_QNAME, PayFine.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PayFineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "payFineResponse")
    public JAXBElement<PayFineResponse> createPayFineResponse(PayFineResponse value) {
        return new JAXBElement<PayFineResponse>(_PayFineResponse_QNAME, PayFineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewFine }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "viewFine")
    public JAXBElement<ViewFine> createViewFine(ViewFine value) {
        return new JAXBElement<ViewFine>(_ViewFine_QNAME, ViewFine.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewFineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "viewFineResponse")
    public JAXBElement<ViewFineResponse> createViewFineResponse(ViewFineResponse value) {
        return new JAXBElement<ViewFineResponse>(_ViewFineResponse_QNAME, ViewFineResponse.class, null, value);
    }

}
