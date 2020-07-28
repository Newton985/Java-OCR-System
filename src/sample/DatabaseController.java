package sample;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: write you class description here
 *
 * @author newton
 */

public class DatabaseController {

    private Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/Reegan?useUnicode=true&useJDBCCompliantTimeZoneShift=true&serverTimezone=UTC&useLegacyDatetimeCode=false","root","");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertData(Person person){
        try {
            String sql="INSERT INTO reegan (name, contact, id, pob,age,gender,date) VALUES(?,?,?,?,?,?,?)";
            Connection connection=getConnection();
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,person.name.get());
            statement.setString(2,person.contact.get());
            statement.setString(3,person.id.get());
            statement.setString(4,person.pob.get());
            statement.setString(5,person.age.get());
            statement.setString(6,person.gender.get());
            statement.setString(7,person.date.get());
            statement.execute(); connection.close();

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Record Successfuly Saved");
            alert.setTitle("Success");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Person getData(String contact){
        //get data for one person using id
        Person person=null;
        try {
            String sql="SELECT * FROM reegan WHERE contact="+contact;
            Connection connection=getConnection();
            PreparedStatement statement=connection.prepareStatement(sql);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                person=new Person();
                person.setAge(resultSet.getString("age"));
                person.setGender(resultSet.getString("gender"));
                person.setContact(resultSet.getString("contact"));
                person.setId(resultSet.getString("id"));
                person.setPob(resultSet.getString("pob"));
                person.setName(resultSet.getString("name"));
           ;
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public ObservableList<Person> getData(){
        ObservableList<Person> personList= FXCollections.observableArrayList();
        //get data for all people in database
        try {
            String sql="SELECT * FROM reegan ORDER BY id DESC";
            Connection connection=getConnection();
            PreparedStatement statement=connection.prepareStatement(sql);

            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
               Person person=new Person();
               person.setAge(resultSet.getString("age"));
               person.setGender(resultSet.getString("gender"));
               person.setContact(resultSet.getString("contact"));
               person.setId(resultSet.getString("id"));
               person.setPob(resultSet.getString("pob"));
               person.setName(resultSet.getString("name"));
               person.setDate(resultSet.getString("date"));
               personList.add(person);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    public void deletePerson(String id){
        Connection connection=getConnection();
        String sql="DELETE FROM reegan WHERE contact="+id;
        try {
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.execute();
            JOptionPane.showMessageDialog(null,"Deleted Successfully","info",JOptionPane.INFORMATION_MESSAGE);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
