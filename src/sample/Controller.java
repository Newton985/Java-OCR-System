package sample;

import com.sun.rowset.internal.Row;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * TODO: write you class description here
 *
 * @author newtonkarani98@gmail.com
 */

public class Controller implements Initializable {
    public Button selectImageBt;
    public Button scanImageBt;
    public TextArea detailsLabel;
    public Button saveButton;
    public Button cancelButton;
    public TextField nameTextField;
    public TextField genderTextField;
    public Button copyPOB;
    public Button copyGender;
    public Button copyName;
    public TextField idTextField;
    public Button copyID;
    public TextField contactTextField;
    public TextField birthTextField;
    public TableView <Person>detailsTable=new TableView<Person>();
    public TableColumn <Person,String> nameColumn=new TableColumn<Person,String>();
    public TableColumn <Person,String>contactColumn=new TableColumn<Person,String>();
    public TableColumn <Person,String> pobColumn=new TableColumn<Person,String>();
    public TableColumn <Person,String>genderColumn=new TableColumn<Person,String>();
    public TableColumn <Person,String>ageColumn=new TableColumn<Person,String>();
    public TableColumn <Person,String>idColumn=new TableColumn<Person,String>();
    public TableColumn <Person,String>dateRecordedColumn=new TableColumn<Person,String>();
    public Button refreshButton;
    public TextField searchTextView;
    public Button deleteButton;
    public AnchorPane imagePane;
    public ProgressIndicator progressIndicator;
    public Text progressText;
    public Button copyDate;
    public TextField dateTextField;

  private  ObservableList<Person> data=FXCollections.observableArrayList();
  private ObservableList<Person> filteredData=FXCollections.observableArrayList();
  Background defaultBackground;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        defaultBackground=imagePane.getBackground();


       // scan("");

        this.selectImageBt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser=new FileChooser();
                fileChooser.setTitle("Select The ID Image");
                fileChooser.getExtensionFilters().setAll(
                        new FileChooser.ExtensionFilter("Image files","*png","*jpeg","*jpg")
                );
                Node source=(Node)actionEvent.getSource();
                File file=fileChooser.showOpenDialog(source.getScene().getWindow());
                if (file!=null){
                       // Controller.this.detailsLabel.setText(file.getPath());
                        scan(file);

                }
            }
        });

        this.copyGender.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Controller.this.detailsLabel.getSelectedText().equals("")){
                    openDialog();
                }else {
                    Controller.this.genderTextField.setText(Controller.this.detailsLabel.getSelectedText());
                }
            }
        });


        this.copyName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Controller.this.detailsLabel.getSelectedText().equals("")){
                    openDialog();
                }else {
                    Controller.this.nameTextField.setText(Controller.this.detailsLabel.getSelectedText());
                }
            }
        });

        this.copyPOB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Controller.this.detailsLabel.getSelectedText().equals("")){
                    openDialog();
                }else {
                    Controller.this.birthTextField.setText(Controller.this.detailsLabel.getSelectedText());
                }
            }
        });

        this.copyID.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Controller.this.detailsLabel.getSelectedText().equals("")){
                    openDialog();
                }else {
                    Controller.this.idTextField.setText(Controller.this.detailsLabel.getSelectedText());
                }
            }
        });

        this.copyDate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Controller.this.detailsLabel.getSelectedText().equals("")){
                    openDialog();
                }else {
                    Controller.this.dateTextField.setText(Controller.this.detailsLabel.getSelectedText());
                }
            }
        });


        //Save Data To database
        this.saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Controller.this.nameTextField.getText().equals("")|| Controller.this.dateTextField.getText().equals("")
                    || Controller.this.contactTextField.getText().equals("")|| Controller.this.birthTextField.getText().equals("")
                || Controller.this.genderTextField.getText().equals("")){
                    openDialog();
                }else {
                    Date date=new Date();
                    DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                    String dateRecord=dateFormat.format(date);
                    Person person=new Person();
                    person.setDate(dateRecord);
                    person.setName(Controller.this.nameTextField.getText());
                    person.setPob(Controller.this.birthTextField.getText());
                    person.setGender(Controller.this.genderTextField.getText());
                    person.setAge(Controller.this.dateTextField.getText());
                    person.setContact(Controller.this.contactTextField.getText());
                    if (Controller.this.idTextField.getText().equals("")){
                        person.setId("N/A");
                    }else{
                    person.setId(Controller.this.idTextField.getText());}
                    new DatabaseController().insertData(person);
                   fillTable();
                }

                nameTextField.setText("");dateTextField.setText("");contactTextField.setText("");
                birthTextField.setText("");genderTextField.setText("");idTextField.setText("");
                detailsLabel.setText("no details");
                imagePane.setBackground(defaultBackground);
                progressIndicator.setVisible(true);
                progressText.setVisible(true);
            }
        });
        this.refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fillTable();
            }
        });


        this.deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SelectionModel<Person> selectionModel= Controller.this.detailsTable.getSelectionModel();
                Person person=selectionModel.getSelectedItem();
                new DatabaseController().deletePerson(person.getId());
                fillTable();
            }
        });



        // Details Tab
        fillTable();


        //print table contents
        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createExcel();

            }
        });




        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Person person=detailsTable.getSelectionModel().getSelectedItem();
                if (person==null){
                    JOptionPane.showMessageDialog(null,"Select Item From The table to delete","Info",JOptionPane.ERROR_MESSAGE);
                }else{
                String contact=person.contact.get();
                new DatabaseController().deletePerson(contact);
                fillTable(); }
            }
        });


        searchTable();
    }

    private void fillTable(){

        this.nameColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("name"));
        this.genderColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("gender"));
        this.ageColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("age"));
        this.idColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("id"));
        this.pobColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("pob"));
        this.contactColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("contact"));
        dateRecordedColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("date"));
        this.detailsTable.getColumns().setAll(this.nameColumn, this.genderColumn, this.ageColumn, this.idColumn, this.pobColumn, this.contactColumn,dateRecordedColumn);
        ///this.detailsTable.getItems().clear();
        this.data.clear();
        this.data =new DatabaseController().getData();
        this.detailsTable.setItems(this.data);

        searchTable();
    }

     private  void scan(File file){
         try {
             //set image
             Image image=new Image(file.toURI().toString());
             BackgroundSize backgroundSize=new BackgroundSize(100,100,true,true,true,false);
             BackgroundImage backgroundImage=new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                     BackgroundPosition.CENTER,backgroundSize);
             Background background=new Background(backgroundImage);
             this.imagePane.setBackground(background);

             this.progressIndicator.setVisible(false);
             this.progressText.setVisible(false);

             //Tesseract Scan
             Tesseract tesseract=new Tesseract();
             String dir=System.getProperty("user.dir")+"\\tessdata";
             tesseract.setDatapath(dir);
             String data=tesseract.doOCR(file);
             this.detailsLabel.setText(data);
         } catch (TesseractException e) {
             e.printStackTrace();
         }
     }

    private void openDialog(){
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("details.fxml"));
            Parent parent=fxmlLoader.load();
            DetailsController detailsController=fxmlLoader.<DetailsController>getController();
            Scene scene=new Scene(parent);
            Stage stage=new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    private  void createExcel(){

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet spreadsheet = workbook.createSheet("sample");

        HSSFRow row = spreadsheet.createRow(0);

        for (int j = 0; j < detailsTable.getColumns().size(); j++) {
            row.createCell((short) j).setCellValue(detailsTable.getColumns().get(j).getText());
        }

        for (int i = 0; i < detailsTable.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < detailsTable.getColumns().size(); j++) {
                if(detailsTable.getColumns().get(j).getCellData(i) != null) {
                    row.createCell((short) j).setCellValue(detailsTable.getColumns().get(j).getCellData(i).toString());
                }
                else {
                    row.createCell((short) j).setCellValue("");
                }
            }
        }

        FileOutputStream fileOut = null;
        try {
            File file=new File(System.getProperty("user.dir")+"\\reeganPassengers.xls");
            fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            Desktop.getDesktop().print(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void searchTable(){
        FilteredList<Person> filteredData = new FilteredList<>(data, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        searchTextView.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getFirstName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                    // Filter matches first name.

                } else if (String.valueOf(myObject.id.get()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }else if (String.valueOf(myObject.name.get()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }else if (String.valueOf(myObject.contact.get()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }else if (String.valueOf(myObject.date.get()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Person> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(detailsTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        detailsTable.setItems(sortedData);
    }

}
