package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberID;
    private String identityNumber;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer age;
    private String phone;
    private String address;
    private String securityCode;
    
    @OneToMany(mappedBy = "member")
    private List<Lend> lendList = new ArrayList<Lend> ();
   
    @OneToMany (mappedBy = "member")
    private List<Reservation> reservationList = new ArrayList<Reservation>();

    public Member() {
    }

    public Member(String identityNumber, String firstName, String lastName, String gender, Integer age,
            String phone, String address, String securityCode){
        this.identityNumber = identityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.securityCode = securityCode;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberID != null ? memberID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the memberID fields are not set
        if (!(object instanceof Member)) {
            return false;
        }
        Member other = (Member) object;
        if ((this.memberID == null && other.memberID != null) || (this.memberID != null && !this.memberID.equals(other.memberID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.MemberEntity[ id=" + memberID + " ]";
    }
    
    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public List<Lend> getLendList() {
        return lendList;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

}
