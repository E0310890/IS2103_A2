
package session.stateless;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fineAmount" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="lendID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fine", propOrder = {
    "fineAmount",
    "lendID"
})
public class Fine {

    protected double fineAmount;
    protected Long lendID;

    /**
     * Gets the value of the fineAmount property.
     * 
     */
    public double getFineAmount() {
        return fineAmount;
    }

    /**
     * Sets the value of the fineAmount property.
     * 
     */
    public void setFineAmount(double value) {
        this.fineAmount = value;
    }

    /**
     * Gets the value of the lendID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLendID() {
        return lendID;
    }

    /**
     * Sets the value of the lendID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLendID(Long value) {
        this.lendID = value;
    }

}
