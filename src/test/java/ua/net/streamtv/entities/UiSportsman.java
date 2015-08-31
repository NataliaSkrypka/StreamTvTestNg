package ua.net.streamtv.entities;

import static org.apache.commons.lang.RandomStringUtils.*;

import org.testng.annotations.DataProvider;

import java.util.Objects;
import java.util.Random;

/**
 * Created by nskrypka on 8/31/2015.
 */
public class UiSportsman {

    private String lastName;
    private String dateOfBirth;
    private String region;
    private String fst;
    private String style;
    private String year;
    private String firstName;
    private String middleName;
    private String region1;
    private String fst1;
    private String age;

    public UiSportsman() {

    }

    public UiSportsman(String lastName, String dateOfBirth, String region, String fst, String year, String style, String firstName, String region1, String middleName, String fst1, String age) {
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.region = region;
        this.fst = fst;
        this.year = year;
        this.style = style;
        this.firstName = firstName;
        this.region1 = region1;
        this.middleName = middleName;
        this.fst1 = fst1;
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFst() {
        return fst;
    }

    public void setFst(String fst) {
        this.fst = fst;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getRegion1() {
        return region1;
    }

    public void setRegion1(String region1) {
        this.region1 = region1;
    }

    public String getFst1() {
        return fst1;
    }

    public void setFst1(String fst1) {
        this.fst1 = fst1;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UiSportsman that = (UiSportsman) o;

        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (region != null ? !region.equals(that.region) : that.region != null) return false;
        if (fst != null ? !fst.equals(that.fst) : that.fst != null) return false;
        if (style != null ? !style.equals(that.style) : that.style != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (region1 != null ? !region1.equals(that.region1) : that.region1 != null) return false;
        if (fst1 != null ? !fst1.equals(that.fst1) : that.fst1 != null) return false;
        return !(age != null ? !age.equals(that.age) : that.age != null);

    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (fst != null ? fst.hashCode() : 0);
        result = 31 * result + (style != null ? style.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (region1 != null ? region1.hashCode() : 0);
        result = 31 * result + (fst1 != null ? fst1.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UiSportsman{" +
                "lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", region='" + region + '\'' +
                ", fst='" + fst + '\'' +
                ", style='" + style + '\'' +
                ", year='" + year + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", region1='" + region1 + '\'' +
                ", fst1='" + fst1 + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    @DataProvider(name = "randomUiSportsman")
    public Object[][] getRandomUiSportsman() {
        UiSportsman sportsman = new UiSportsman();
        sportsman.setLastName(randomAlphabetic(5));
        sportsman.setFirstName(randomAlphabetic(5));
        sportsman.setMiddleName(randomAlphabetic(5));
        sportsman.setDateOfBirth("0" + randomNumeric(1) + "-0" + randomNumeric(1) + "-19" + randomNumeric(2));
        sportsman.setRegion("Zakarpatska");
        sportsman.setFst("Ukraina");
        sportsman.setStyle("FW");
        sportsman.setYear("2016");
        sportsman.setRegion1("Poltavska");
        sportsman.setFst1("Kolos");
        sportsman.setAge("Junior");
        return new Object[][]{
                new Object [] {sportsman}
        };
    }
}
