/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author lester
 */
@Entity
public class LendingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lendID;
    @ManyToOne
    private BookEntity bookID; 
    @ManyToOne
    private MemberEntity memberID;
    private Date lendDate;

    public Long getLendID() {
        return lendID;
    }

    public void setLendID(Long lendID) {
        this.lendID = lendID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lendID != null ? lendID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the lendID fields are not set
        if (!(object instanceof LendingEntity)) {
            return false;
        }
        LendingEntity other = (LendingEntity) object;
        if ((this.lendID == null && other.lendID != null) || (this.lendID != null && !this.lendID.equals(other.lendID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.LendingEntity[ id=" + lendID + " ]";
    }

}
