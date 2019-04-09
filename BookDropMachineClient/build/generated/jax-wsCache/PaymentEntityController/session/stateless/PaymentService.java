
package session.stateless;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "PaymentService", targetNamespace = "http://stateless.session/", wsdlLocation = "http://localhost:8080/PaymentService/PaymentEntityController?wsdl")
public class PaymentService
    extends Service
{

    private final static URL PAYMENTSERVICE_WSDL_LOCATION;
    private final static WebServiceException PAYMENTSERVICE_EXCEPTION;
    private final static QName PAYMENTSERVICE_QNAME = new QName("http://stateless.session/", "PaymentService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/PaymentService/PaymentEntityController?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PAYMENTSERVICE_WSDL_LOCATION = url;
        PAYMENTSERVICE_EXCEPTION = e;
    }

    public PaymentService() {
        super(__getWsdlLocation(), PAYMENTSERVICE_QNAME);
    }

    public PaymentService(WebServiceFeature... features) {
        super(__getWsdlLocation(), PAYMENTSERVICE_QNAME, features);
    }

    public PaymentService(URL wsdlLocation) {
        super(wsdlLocation, PAYMENTSERVICE_QNAME);
    }

    public PaymentService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PAYMENTSERVICE_QNAME, features);
    }

    public PaymentService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PaymentService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns PaymentEntityController
     */
    @WebEndpoint(name = "PaymentEntityControllerPort")
    public PaymentEntityController getPaymentEntityControllerPort() {
        return super.getPort(new QName("http://stateless.session/", "PaymentEntityControllerPort"), PaymentEntityController.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PaymentEntityController
     */
    @WebEndpoint(name = "PaymentEntityControllerPort")
    public PaymentEntityController getPaymentEntityControllerPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://stateless.session/", "PaymentEntityControllerPort"), PaymentEntityController.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PAYMENTSERVICE_EXCEPTION!= null) {
            throw PAYMENTSERVICE_EXCEPTION;
        }
        return PAYMENTSERVICE_WSDL_LOCATION;
    }

}
