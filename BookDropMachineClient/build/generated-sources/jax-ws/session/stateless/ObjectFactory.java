
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

    private final static QName _BookNotFoundException_QNAME = new QName("http://stateless.session/", "BookNotFoundException");
    private final static QName _BookNotLendException_QNAME = new QName("http://stateless.session/", "BookNotLendException");
    private final static QName _FineNotPaidException_QNAME = new QName("http://stateless.session/", "FineNotPaidException");
    private final static QName _LendBySelfException_QNAME = new QName("http://stateless.session/", "LendBySelfException");
    private final static QName _MemberNotFoundException_QNAME = new QName("http://stateless.session/", "MemberNotFoundException");
    private final static QName _ReserveBySelfException_QNAME = new QName("http://stateless.session/", "ReserveBySelfException");
    private final static QName _ReserveBook_QNAME = new QName("http://stateless.session/", "reserveBook");
    private final static QName _ReserveBookResponse_QNAME = new QName("http://stateless.session/", "reserveBookResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: session.stateless
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BookNotFoundException }
     * 
     */
    public BookNotFoundException createBookNotFoundException() {
        return new BookNotFoundException();
    }

    /**
     * Create an instance of {@link BookNotLendException }
     * 
     */
    public BookNotLendException createBookNotLendException() {
        return new BookNotLendException();
    }

    /**
     * Create an instance of {@link FineNotPaidException }
     * 
     */
    public FineNotPaidException createFineNotPaidException() {
        return new FineNotPaidException();
    }

    /**
     * Create an instance of {@link LendBySelfException }
     * 
     */
    public LendBySelfException createLendBySelfException() {
        return new LendBySelfException();
    }

    /**
     * Create an instance of {@link MemberNotFoundException }
     * 
     */
    public MemberNotFoundException createMemberNotFoundException() {
        return new MemberNotFoundException();
    }

    /**
     * Create an instance of {@link ReserveBySelfException }
     * 
     */
    public ReserveBySelfException createReserveBySelfException() {
        return new ReserveBySelfException();
    }

    /**
     * Create an instance of {@link ReserveBook }
     * 
     */
    public ReserveBook createReserveBook() {
        return new ReserveBook();
    }

    /**
     * Create an instance of {@link ReserveBookResponse }
     * 
     */
    public ReserveBookResponse createReserveBookResponse() {
        return new ReserveBookResponse();
    }

    /**
     * Create an instance of {@link Member }
     * 
     */
    public Member createMember() {
        return new Member();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "BookNotFoundException")
    public JAXBElement<BookNotFoundException> createBookNotFoundException(BookNotFoundException value) {
        return new JAXBElement<BookNotFoundException>(_BookNotFoundException_QNAME, BookNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookNotLendException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "BookNotLendException")
    public JAXBElement<BookNotLendException> createBookNotLendException(BookNotLendException value) {
        return new JAXBElement<BookNotLendException>(_BookNotLendException_QNAME, BookNotLendException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FineNotPaidException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "FineNotPaidException")
    public JAXBElement<FineNotPaidException> createFineNotPaidException(FineNotPaidException value) {
        return new JAXBElement<FineNotPaidException>(_FineNotPaidException_QNAME, FineNotPaidException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LendBySelfException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "LendBySelfException")
    public JAXBElement<LendBySelfException> createLendBySelfException(LendBySelfException value) {
        return new JAXBElement<LendBySelfException>(_LendBySelfException_QNAME, LendBySelfException.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveBySelfException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "ReserveBySelfException")
    public JAXBElement<ReserveBySelfException> createReserveBySelfException(ReserveBySelfException value) {
        return new JAXBElement<ReserveBySelfException>(_ReserveBySelfException_QNAME, ReserveBySelfException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "reserveBook")
    public JAXBElement<ReserveBook> createReserveBook(ReserveBook value) {
        return new JAXBElement<ReserveBook>(_ReserveBook_QNAME, ReserveBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "reserveBookResponse")
    public JAXBElement<ReserveBookResponse> createReserveBookResponse(ReserveBookResponse value) {
        return new JAXBElement<ReserveBookResponse>(_ReserveBookResponse_QNAME, ReserveBookResponse.class, null, value);
    }

}
