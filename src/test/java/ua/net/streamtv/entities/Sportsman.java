package ua.net.streamtv.entities;

import static org.apache.commons.lang3.RandomStringUtils.*;

import org.testng.annotations.DataProvider;

import java.util.*;

/**
 * Created by nskrypka on 8/28/2015.
 */
public class Sportsman {

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

    public Sportsman(String lastName, String dateOfBirth, String region, String fst, String style,
                     String year, String firstName, String middleName, String region1, String fst1, String age) {
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.region = region;
        this.fst = fst;
        this.style = style;
        this.year = year;
        this.firstName = firstName;
        this.middleName = middleName;
        this.region1 = region1;
        this.fst1 = fst1;
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getRegion() {
        return region;
    }

    public String getFst() {
        return fst;
    }

    public String getStyle() {
        return style;
    }

    public String getYear() {
        return year;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getAge() {
        return age;
    }

    public String getRegion1() {
        return region1;
    }

    public String getFst1() {
        return fst1;
    }

    public List<String> getInfo() {
        List<String> info = new ArrayList<>();
        info.add(lastName);
        info.add(dateOfBirth);
        info.add(region);
        info.add(fst);
        info.add(style);
        info.add(year);
        info.add(firstName);
        info.add(middleName);
        info.add(age);
        info.add(region1);
        info.add(fst1);
        return info;
    }

    @DataProvider(name = "randomSportsman")
    public static Object[][] createRandomSportsman() {
        Map<String, String> sportsmanMap = new HashMap<>();
        sportsmanMap.put("lname", randomAlphabetic(5));
        sportsmanMap.put("dob", "0" + randomNumeric(1) + "-0" + randomNumeric(1) + "-19" + randomNumeric(2));
        sportsmanMap.put("region1", String.valueOf(new Random().nextInt(26) + 1));
        sportsmanMap.put("fst1", String.valueOf(new Random().nextInt(6) + 1));
        sportsmanMap.put("style", String.valueOf(new Random().nextInt(2) + 1));
        sportsmanMap.put("expires", "2016");
        sportsmanMap.put("fname", randomAlphabetic(5));
        sportsmanMap.put("mname", randomAlphabetic(5));
        sportsmanMap.put("lictype", "1");
        return new Object[][] {
                new Object[] {sportsmanMap}
        };
    }
}
