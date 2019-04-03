package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import model.Member;
import model.Staff;
import util.enumeration.Gender;

@Entity
public class MemberEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberID;

    @Column(nullable = false, length = 32, unique = true)
    private String identityNumber;
    @Column(nullable = false, length = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    private String lastName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    @Max(200)
    @Min(0)
    private Integer age;
    @Column(nullable = false, length = 32)
    private String phone;
    @Column(nullable = false, length = 128)
    private String address;
    @Column(nullable = false, length = 32)
    private String securityCode;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<PaymentEntity> payment;
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<LendingEntity> lending;
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ReservationEntity> reservation;

    public MemberEntity() {
    }

    public MemberEntity(String identityNumber, String firstName, String lastName, Gender gender, Integer age, String phone, String address, String securityCode) {
        this.identityNumber = identityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.securityCode = securityCode;
    }

    public MemberEntity(Member member) {
        this.memberID = member.getMemberID();
        this.identityNumber = member.getIdentityNumber();
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.gender = member.getGender();
        this.age = member.getAge();
        this.phone = member.getPhone();
        this.address = member.getAddress();
        this.securityCode = member.getSecurityCode();
    }

    public Member toMember() {
         return new Member(this.memberID, this.identityNumber, this.firstName, this.lastName, this.gender, this.age, this.phone, this.address, this.securityCode);
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
    }

    public String getFirstName() {
        return firstName;
    }

    public List<PaymentEntity> getPayment() {
        return payment;
    }
    
    public List<LendingEntity> getLending() {
        return lending;
    }

    public void setLending(List<LendingEntity> lending) {
        this.lending = lending;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }
    
    public void addPayment(PaymentEntity pe) {
       payment.add(pe);
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


}
