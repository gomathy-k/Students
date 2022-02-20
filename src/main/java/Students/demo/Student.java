package Students.demo;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue(generator = "sequence-generator") //sets the starting ID value to auto-generate IDs
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "user_sequence"),
                    @Parameter(name = "initial_value", value = "220001"), //starting student ID
                    @Parameter(name = "increment_size", value = "1") //incremented by 1 for each new student
            }
    )

    private Long id;//auto-generated ID
    private String firstName;
    private String surname;
    private String dateOfBirth;
    private String sex;
    private String gender;
    private String phoneNumber;
    private String address;

    Student() {}
    Student(String firstName, String surname, String dateOfBirth, String sex, String gender,String phoneNumber, String address){
        this.firstName = firstName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId()) && Objects.equals(getFirstName(), student.getFirstName()) && Objects.equals(getSurname(), student.getSurname()) && Objects.equals(getDateOfBirth(), student.getDateOfBirth()) && Objects.equals(getSex(), student.getSex()) && Objects.equals(getGender(), student.getGender()) && Objects.equals(getPhoneNumber(), student.getPhoneNumber()) && Objects.equals(getAddress(), student.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getSurname(), getDateOfBirth(), getSex(), getGender(), getPhoneNumber(), getAddress());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", sex='" + sex + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
