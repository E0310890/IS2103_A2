package model;

import java.io.Serializable;
import util.enumeration.Gender;

public class Member implements Serializable{
    
    private Long memberID;
    private String identityNumber;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Integer age;
    private String phone;
    private String address;
    private String securityCode;

    public Member() {
    }

    public Member(String identityNumber, String firstName, String lastName, Gender gender, Integer age, String phone, String address, String securityCode) {
        this.identityNumber = identityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.securityCode = securityCode;
    }
    
    

    public Member(Long memberID, String identityNumber, String firstName, String lastName, Gender gender, Integer age, String phone, String address, String securityCode) {
        this.memberID = memberID;
        this.identityNumber = identityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.securityCode = securityCode;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

    @Override
    public String toString() {
        return "Member{" + "memberID=" + memberID + ", identityNumber=" + identityNumber + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender + ", age=" + age + ", phone=" + phone + ", address=" + address + ", securityCode=" + securityCode + '}';
    } 
}