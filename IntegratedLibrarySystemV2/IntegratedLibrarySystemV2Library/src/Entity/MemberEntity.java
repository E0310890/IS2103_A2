package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class MemberEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberID;
    @Column(unique = true, nullable = false, length = 9)
    private String identityNumber;
    @Column(length = 64)
    private String firstName;
    @Column(length = 64, nullable = false)
    private String lastName;
    @Column(length = 32, nullable = false)
    private String gender;
    @Column(length = 128, nullable = false)
    private Integer age;
    @Column(length = 8, nullable = false)
    private String phone;
    @Column(length = 256, nullable = false)
    private String address;
    @Column(length = 32, nullable = false)
    private String securityCode;
    @OneToMany(mappedBy = "member")
    private List<LendEntity> lendList;
    @OneToMany (mappedBy = "member")
    private List<ReservationEntity> reservationList;

    public MemberEntity() {
        lendList = new ArrayList<LendEntity> ();
        reservationList = new ArrayList<ReservationEntity>();
    }

    public MemberEntity(String identityNumber, String firstName, String lastName, String gender, Integer age,
            String phone, String address, String securityCode){
        this();
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
        if (!(object instanceof MemberEntity)) {
            return false;
        }
        MemberEntity other = (MemberEntity) object;
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

    public List<LendEntity> getLendList() {
        return lendList;
    }

    public List<ReservationEntity> getReservationList() {
        return reservationList;
    }

}
