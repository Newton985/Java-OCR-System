package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * TODO: write you class description here
 *
 * @author newtonkarani98@gmail.com
 */

public class Person {
    StringProperty name=new SimpleStringProperty();
    StringProperty age=new SimpleStringProperty();
    StringProperty gender=new SimpleStringProperty();
    StringProperty id=new SimpleStringProperty();
    StringProperty pob=new SimpleStringProperty();
    StringProperty contact=new SimpleStringProperty();
    StringProperty date=new SimpleStringProperty();

    public String getDate() { return date.get(); }
    public void setDate(String date) { this.date.set(date);}

    public String getName(){
        return this.name.get();
    }
    public void setName(String name) { this.name.set(name); }

    public  String getAge(){
        return this.age.get();
    }
    public void setAge(String age) { this.age.set(age); }

    public String getGender(){
        return this.gender.get();
    }
    public void setGender(String gender) { this.gender.set(gender); }

    public  String getId(){
        return this.id.get();
    }
    public void setId(String id) { this.id.set(id); }

    public String getPob(){
        return this.pob.get();
    }
    public void setPob(String pob) { this.pob.set(pob); }

    public  String getContact(){ return this.contact.get(); }
    public void setContact(String contact) { this.contact.set(contact); }


    private StringProperty firstName;
    public void setFirstName(String value) { firstNameProperty().set(value); }
    public String getFirstName() { return firstNameProperty().get(); }
    public StringProperty firstNameProperty() {
        if (this.firstName == null) this.firstName = new SimpleStringProperty(this, "firstName");
        return this.firstName;
    }

}
