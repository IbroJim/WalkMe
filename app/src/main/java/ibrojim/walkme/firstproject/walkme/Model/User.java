package ibrojim.walkme.firstproject.walkme.Model;

public class User {
    public String name, surname, areya, gender,myself;
    public Integer age;

    public User(){}

    public User(String name, String surname, String areya, Integer age,String gender,String myself) {
        this.name = name;
        this.surname = surname;
        this.areya = areya;
        this.age = age;
        this.gender=gender;
        this.myself=myself;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAreya(String areya) {
        this.areya = areya;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}