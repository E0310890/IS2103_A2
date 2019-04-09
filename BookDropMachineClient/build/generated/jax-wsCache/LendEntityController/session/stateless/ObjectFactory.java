
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

    private final static QName _BookOverDueException_QNAME = new QName("http://stateless.session/", "BookOverDueException");
    private final static QName _ExtendLendBook_QNAME = new QName("http://stateless.session/", "ExtendLendBook");
    private final static QName _ExtendLendBookResponse_QNAME = new QName("http://stateless.session/", "ExtendLendBookResponse");
    private final static QName _FineNotPaidException_QNAME = new QName("http://stateless.session/", "FineNotPaidException");
    private final static QName _LendNotFoundException_QNAME = new QName("http://stateless.session/", "LendNotFoundException");
    private final static QName _MemberNotFoundException_QNAME = new QName("http://stateless.session/", "MemberNotFoundException");
    private final static QName _ReservedByOthersException_QNAME = new QName("http://stateless.session/", "ReservedByOthersException");
    private final static QName _ReturnLendBook_QNAME = new QName("http://stateless.session/", "ReturnLendBook");
    private final static QName _ReturnLendBookResponse_QNAME = new QName("http://stateless.session/", "ReturnLendBookResponse");
    private final static QName _ViewLendBooksWs_QNAME = new QName("http://stateless.session/", "ViewLendBooksWs");
    private final static QName _ViewLendBooksWsResponse_QNAME = new QName("http://stateless.session/", "ViewLendBooksWsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: session.stateless
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BookOverDueException }
     * 
     */
    public BookOverDueException createBookOverDueException() {
        return new BookOverDueException();
    }

    /**
     * Create an instance of {@link ExtendLendBook }
     * 
     */
    public ExtendLendBook createExtendLendBook() {
        return new ExtendLendBook();
    }

    /**
     * Create an instance of {@link ExtendLendBookResponse }
     * 
     */
    public ExtendLendBookResponse createExtendLendBookResponse() {
        return new ExtendLendBookResponse();
    }

    /**
     * Create an instance of {@link FineNotPaidException }
     * 
     */
    public FineNotPaidException createFineNotPaidException() {
        return new FineNotPaidException();
    }

    /**
     * Create an instance of {@link LendNotFoundException }
     * 
     */
    public LendNotFoundException createLendNotFoundException() {
        return new LendNotFoundException();
    }

    /**
     * Create an instance of {@link MemberNotFoundException }
     * 
     */
    public MemberNotFoundException createMemberNotFoundException() {
        return new MemberNotFoundException();
    }

    /**
     * Create an instance of {@link ReservedByOthersException }
     * 
     */
    public ReservedByOthersException createReservedByOthersException() {
        return new ReservedByOthersException();
    }

    /**
     * Create an instance of {@link ReturnLendBook }
     * 
     */
    public ReturnLendBook createReturnLendBook() {
        return new ReturnLendBook();
    }

    /**
     * Create an instance of {@link ReturnLendBookResponse }
     * 
     */
    public ReturnLendBookResponse createReturnLendBookResponse() {
        return new ReturnLendBookResponse();
    }

    /**
     * Create an instance of {@link ViewLendBooksWs }
     * 
     */
    public ViewLendBooksWs createViewLendBooksWs() {
        return new ViewLendBooksWs();
    }

    /**
     * Create an instance of {@link ViewLendBooksWsResponse }
     * 
     */
    public ViewLendBooksWsResponse createViewLendBooksWsResponse() {
        return new ViewLendBooksWsResponse();
    }

    /**
     * Create an instance of {@link Lendws }
     * 
     */
    public Lendws createLendws() {
        return new Lendws();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookOverDueException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "BookOverDueException")
    public JAXBElement<BookOverDueException> createBookOverDueException(BookOverDueException value) {
        return new JAXBElement<BookOverDueException>(_BookOverDueException_QNAME, BookOverDueException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExtendLendBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "ExtendLendBook")
    public JAXBElement<ExtendLendBook> createExtendLendBook(ExtendLendBook value) {
        return new JAXBElement<ExtendLendBook>(_ExtendLendBook_QNAME, ExtendLendBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExtendLendBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "ExtendLendBookResponse")
    public JAXBElement<ExtendLendBookResponse> createExtendLendBookResponse(ExtendLendBookResponse value) {
        return new JAXBElement<ExtendLendBookResponse>(_ExtendLendBookResponse_QNAME, ExtendLendBookResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link LendNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "LendNotFoundException")
    public JAXBElement<LendNotFoundException> createLendNotFoundException(LendNotFoundException value) {
        return new JAXBElement<LendNotFoundException>(_LendNotFoundException_QNAME, LendNotFoundException.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservedByOthersException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "ReservedByOthersException")
    public JAXBElement<ReservedByOthersException> createReservedByOthersException(ReservedByOthersException value) {
        return new JAXBElement<ReservedByOthersException>(_ReservedByOthersException_QNAME, ReservedByOthersException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnLendBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "ReturnLendBook")
    public JAXBElement<ReturnLendBook> createReturnLendBook(ReturnLendBook value) {
        return new JAXBElement<ReturnLendBook>(_ReturnLendBook_QNAME, ReturnLendBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnLendBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "ReturnLendBookResponse")
    public JAXBElement<ReturnLendBookResponse> createReturnLendBookResponse(ReturnLendBookResponse value) {
        return new JAXBElement<ReturnLendBookResponse>(_ReturnLendBookResponse_QNAME, ReturnLendBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewLendBooksWs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "ViewLendBooksWs")
    public JAXBElement<ViewLendBooksWs> createViewLendBooksWs(ViewLendBooksWs value) {
        return new JAXBElement<ViewLendBooksWs>(_ViewLendBooksWs_QNAME, ViewLendBooksWs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewLendBooksWsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stateless.session/", name = "ViewLendBooksWsResponse")
    public JAXBElement<ViewLendBooksWsResponse> createViewLendBooksWsResponse(ViewLendBooksWsResponse value) {
        return new JAXBElement<ViewLendBooksWsResponse>(_ViewLendBooksWsResponse_QNAME, ViewLendBooksWsResponse.class, null, value);
    }

}
