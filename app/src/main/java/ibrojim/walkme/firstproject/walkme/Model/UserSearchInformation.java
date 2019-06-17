package ibrojim.walkme.firstproject.walkme.Model;

import java.util.HashMap;

public class UserSearchInformation {

    public String name, surname, areya, gender, myself;
    public Integer age;
    public HashMap<String,String> tag;

    public UserSearchInformation() {
    }

    public UserSearchInformation(String name, String surname, String areya, String gender, Integer age, HashMap<String,String> tag, String myself) {
        this.name = name;
        this.surname = surname;
        this.areya = areya;
        this.gender = gender;
        this.age = age;
        this.tag=tag;
        this.myself=myself;
    }

    public String getMyself() {
        return myself;
    }

    public HashMap<String, String> getTags() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAreya() {
        return areya;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }
}
