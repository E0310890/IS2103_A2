
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
@WebService(name = "ReservationEntityController", targetNamespace = "http://stateless.session/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ReservationEntityController {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     * @throws ReserveBySelfException_Exception
     * @throws BookNotFoundException_Exception
     * @throws FineNotPaidException_Exception
     * @throws MemberNotFoundException_Exception
     * @throws LendBySelfException_Exception
     * @throws BookNotLendException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "reserveBook", targetNamespace = "http://stateless.session/", className = "session.stateless.ReserveBook")
    @ResponseWrapper(localName = "reserveBookResponse", targetNamespace = "http://stateless.session/", className = "session.stateless.ReserveBookResponse")
    @Action(input = "http://stateless.session/ReservationEntityController/reserveBookRequest", output = "http://stateless.session/ReservationEntityController/reserveBookResponse", fault = {
        @FaultAction(className = BookNotFoundException_Exception.class, value = "http://stateless.session/ReservationEntityController/reserveBook/Fault/BookNotFoundException"),
        @FaultAction(className = MemberNotFoundException_Exception.class, value = "http://stateless.session/ReservationEntityController/reserveBook/Fault/MemberNotFoundException"),
        @FaultAction(className = BookNotLendException_Exception.class, value = "http://stateless.session/ReservationEntityController/reserveBook/Fault/BookNotLendException"),
        @FaultAction(className = FineNotPaidException_Exception.class, value = "http://stateless.session/ReservationEntityController/reserveBook/Fault/FineNotPaidException"),
        @FaultAction(className = LendBySelfException_Exception.class, value = "http://stateless.session/ReservationEntityController/reserveBook/Fault/LendBySelfException"),
        @FaultAction(className = ReserveBySelfException_Exception.class, value = "http://stateless.session/ReservationEntityController/reserveBook/Fault/ReserveBySelfException")
    })
    public boolean reserveBook(
        @WebParam(name = "arg0", targetNamespace = "")
        Member arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Long arg1)
        throws BookNotFoundException_Exception, BookNotLendException_Exception, FineNotPaidException_Exception, LendBySelfException_Exception, MemberNotFoundException_Exception, ReserveBySelfException_Exception
    ;

}
