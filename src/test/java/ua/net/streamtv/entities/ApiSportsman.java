package ua.net.streamtv.entities;

import com.google.gson.annotations.SerializedName;
import org.testng.annotations.DataProvider;

import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * Created by nskrypka on 8/29/15.
 */
public class ApiSportsman {

    @SerializedName("lname")
    private String lastName;

    @SerializedName("dob")
    private String dateOfBirth;

    @SerializedName("region1")
    private String region;

    @SerializedName("fst1")
    private String fst;

    @SerializedName("style")
    private String style;

    @SerializedName("expires")
    private String year;

    @SerializedName("fname")
    private String firstName;

    @SerializedName("mname")
    private String middleName;

    @SerializedName("lictype")
    private String age;

    @SerializedName("id_wrestler")
    private String id;

    @SerializedName("card_state")
    private String card;

    public ApiSportsman() {

    }

    public ApiSportsman(String lastName, String dateOfBirth, String region, String fst, String style, String year, String firstName, String middleName, String age, String id, String card) {
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.region = region;
        this.fst = fst;
        this.style = style;
        this.year = year;
        this.firstName = firstName;
        this.middleName = middleName;
        this.age = age;
        this.id = id;
        this.card = card;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    @DataProvider(name = "randomSportsman")
    public static Object[][] createRandomSportsman() {
        ApiSportsman sportsman = new ApiSportsman();
        sportsman.setLastName(randomAlphabetic(5));
        sportsman.setDateOfBirth("0" + randomNumeric(1) + "-0" + randomNumeric(1) + "-19" + randomNumeric(2));
        sportsman.setRegion(String.valueOf(new Random().nextInt(25) + 2));
        sportsman.setFst(String.valueOf(new Random().nextInt(5) + 2));
        sportsman.setStyle(String.valueOf(new Random().nextInt(2) + 1));
        sportsman.setYear("2016");
        sportsman.setFirstName(randomAlphabetic(5));
        sportsman.setMiddleName(randomAlphabetic(5));
        sportsman.setAge("1");
        sportsman.setCard("1");
        return new Object[][]{
                new Object[]{sportsman}
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiSportsman that = (ApiSportsman) o;

        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (region != null ? !region.equals(that.region) : that.region != null) return false;
        if (fst != null ? !fst.equals(that.fst) : that.fst != null) return false;
        if (style != null ? !style.equals(that.style) : that.style != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (card != null ? !card.equals(that.card) : that.card != null) return false;
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
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (card != null ? card.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApiSportsman{" +
                "lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", region='" + region + '\'' +
                ", fst='" + fst + '\'' +
                ", style='" + style + '\'' +
                ", year='" + year + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", age='" + age + '\'' +
                ", id='" + id + '\'' +
                ", card_state='" + card + '\'' +
                '}';
    }
}
