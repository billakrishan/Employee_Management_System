package employeemanagment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author KrishanBilla
 */
public class EmployeeManagment extends Application {
    
    private Label lblID = new Label("Employee ID");
    private TextField txtID = new TextField();
    private Label lblName = new Label("Employee Name");
    private TextField txtName = new TextField();
    private Label lblDep = new Label("Employee Department");
    private TextField txtDep = new TextField();
    private Label lblDesig = new Label("Employee Designation");
    private TextField txtDesig = new TextField();
    private Button btnFirst = new Button("First");
    private Button btnNext = new Button("Next");
    private Button btnPrevious = new Button("Previous");
    private Button btnLast = new Button("Last");
    private Button btnModify = new Button("Modify");
    private Button btnAdd = new Button("Add");
    private Button btnSaveA = new Button("Save");
    private Button btnSaveM = new Button("Save");
    private Button btnBack = new Button("Back");
    private Button btnDelete = new Button("Delete");
    private Button btnSearch = new Button("Search");
    
    private RadioButton rdName= new RadioButton("Employee Name");
    private RadioButton rdID= new RadioButton("Employee ID");
    private RadioButton rdDep= new RadioButton("Employee Department");
    private RadioButton rdDesig= new RadioButton("Employee Designation");
    ToggleGroup group = new ToggleGroup();
   private TextArea txtArea = new TextArea();
   private TextField txtField= new TextField();
    
    private ArrayList<String> dataList = new ArrayList();
    private int cIndex =0;

    @Override
    public void start(Stage primaryStage) {
        getFromFile();
        setFieldsEditable(false);
        
        VBox fPane = new VBox();
        fPane.getChildren().addAll(lblID, txtID, lblName, txtName, lblDep, txtDep, lblDesig, txtDesig);
        fPane.setSpacing(20);
        HBox sPane = new HBox();
        sPane.setSpacing(20);
        sPane.getChildren().addAll(btnFirst, btnNext, btnPrevious, btnLast);
        HBox sPane2 = new HBox();
        sPane2.setSpacing(20);
        sPane2.getChildren().addAll(btnModify, btnAdd,btnDelete,btnSearch);
        VBox sPanes = new VBox();
        sPanes.getChildren().addAll(sPane,sPane2);
        sPanes.setSpacing(20);
        BorderPane pane = new BorderPane();
        pane.setCenter(fPane);
        pane.setBottom(sPanes);
        pane.setPadding(new Insets(10, 10, 10, 10));
        displayRecord(cIndex);
        rdName.setToggleGroup(group);
        rdID.setToggleGroup(group);
        rdDep.setToggleGroup(group);
        rdDesig.setToggleGroup(group);
        
        
        rdID.setOnAction(e->{
        txtArea.clear();
        txtField.clear();
    });
        
        rdName.setOnAction(e->{
        txtArea.clear();
        txtField.clear();
    });
        rdDep.setOnAction(e->{
        txtArea.clear();
        txtField.clear();
    });
        rdDesig.setOnAction(e->{
        txtArea.clear();
        txtField.clear();
    });
        btnFirst.setOnMouseClicked(e -> {
            cIndex = 0;
            displayRecord(cIndex);

        });
        btnLast.setOnMouseClicked(e -> {
            cIndex = dataList.size() - 1;
            displayRecord(cIndex);
        });
        btnPrevious.setOnMouseClicked(e -> {
            cIndex--;
            if (cIndex >= 0) {
                displayRecord(cIndex);
            } else {
                cIndex=0;
                showError("This is the First Record\nNo Record Before This One!..");
            }
        });
        btnNext.setOnMouseClicked(e -> {
            cIndex++;
            if (cIndex < dataList.size()) {
                displayRecord(cIndex);
            } else {
                cIndex=dataList.size()-1;
                showError("This is the Last Record\nNo Record After This One!..");
            }
        });
        btnAdd.setOnAction(e -> {
            setFieldsEditable(true);
            clearFields();
            txtID.requestFocus();
            btnAdd.setDisable(true);
            btnNext.setDisable(true);
            btnFirst.setDisable(true);
            btnLast.setDisable(true);
            btnPrevious.setDisable(true);
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
            btnSearch.setDisable(true);
            fPane.getChildren().addAll(btnSaveA, btnBack);

        });
        btnBack.setOnAction(e -> {
            btnNext.setDisable(false);
            btnFirst.setDisable(false);
            btnLast.setDisable(false);
            btnPrevious.setDisable(false);
            btnAdd.setDisable(false);
            btnDelete.setDisable(false);
            btnModify.setDisable(false);
            btnSearch.setDisable(false);
            fPane.getChildren().removeAll(btnSaveA, btnBack,btnSaveM);
            
            clearFields();
            setFieldsEditable(false);
            displayRecord(cIndex);
            
        }
        );

        btnSaveA.setOnAction(e -> {
            addToList("a");
            
            
            
        });
        
        btnSaveM.setOnAction(e -> {
            addToList("m");
            btnNext.setDisable(false);
            btnFirst.setDisable(false);
            btnLast.setDisable(false);
            btnPrevious.setDisable(false);
            btnAdd.setDisable(false);
            btnDelete.setDisable(false);
            btnModify.setDisable(false);
            btnSearch.setDisable(false);
            fPane.getChildren().removeAll(btnSaveA, btnBack,btnSaveM);
            
            clearFields();
            setFieldsEditable(false);
            displayRecord(cIndex);
            
            
            
        });
        
        btnDelete.setOnAction(e->{
        Stage stage = new Stage();
        Button btnOK = new Button("OK");
        Button btnCancel= new Button ("Cancel");
        BorderPane dpane = new BorderPane();
        
        dpane.setCenter(new Label("Are you Sure you want to Delete Record? "));
        HBox pOptions = new HBox();
        pOptions.setSpacing(20);
        pOptions.setPadding(new Insets(10,10,10,40));
        pOptions.getChildren().addAll(btnOK,btnCancel);
 
        dpane.setBottom(pOptions);
    
        Scene scene = new Scene(dpane, 300, 100);
        stage.setScene(scene);
        stage.show();
        btnOK.setOnAction(f -> {
            stage.close();
             if(cIndex>=0 && cIndex <dataList.size()){
                dataList.remove(cIndex);
                clearFields();
                showError("Record Deleted");
                cIndex=0;
                displayRecord(cIndex);
            }
             });
         btnCancel.setOnMouseClicked(g->{
             stage.close();
         });
        
        
           
            
        });
        
        btnModify.setOnAction(e->{
        setFieldsEditable(true);
        txtID.requestFocus();
        btnAdd.setDisable(true);
            btnNext.setDisable(true);
            btnFirst.setDisable(true);
            btnLast.setDisable(true);
            btnPrevious.setDisable(true);
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
            btnSearch.setDisable(true);
            fPane.getChildren().addAll(btnSaveM, btnBack);
    });

           btnSearch.setOnAction(e->{
               Stage stage= new Stage();
               Label lblsearch= new Label("Search By:");
               VBox pSearch = new VBox();
               pSearch.setSpacing(20);
               pSearch.setPadding(new Insets(10,10,10,10));
               
               txtField.setPromptText("Enter Value");
               
               txtArea.setPromptText("Search Results");
               txtArea.setEditable(false);
               Button btnSearch = new Button("Search");
               rdName.setSelected(true);
               pSearch.getChildren().addAll(lblsearch,rdName,rdID,rdDep,rdDesig,txtField,txtArea,btnSearch);
               txtArea.setVisible(false);
               Scene scene= new Scene(pSearch,500,500);
               stage.setScene(scene);
               stage.setTitle("Search Box");
               stage.show();
               btnSearch.setOnAction(f->{
                   txtArea.clear();
                   if(txtField.getText().length()!=0){
                       if(rdID.isSelected()){
                       search(0,txtField.getText());
                       }
                       if(rdName.isSelected()){
                           search(1,txtField.getText());
                       }
                       if(rdDep.isSelected()){
                            search(2,txtField.getText());
                       }
                       if(rdDesig.isSelected()){
                            search(3,txtField.getText());
                       }
                   }else{
                       showError("Enter value");
                   }
                   
               });
           });
           
           btnFirst.setMinWidth(50);
           btnPrevious.setMinWidth(50);
           btnNext.setMinWidth(50);
           btnLast.setMinWidth(50);
           btnAdd.setMinWidth(50);
           btnSearch.setMinWidth(50);
           btnModify.setMinWidth(50);
           btnDelete.setMinWidth(50);
        Scene scene = new Scene(pane, 300, 500);
        
        primaryStage.setTitle("Employee Managment");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e->{
            addToFile();
            showError("Goodbye!...");
        });
        primaryStage.show();
    }

    public void getFromFile() {
        try {
            FileReader file = new FileReader("data.dat");
            BufferedReader buf = new BufferedReader(file);
            String line;
            line = buf.readLine();
            while (line != null) {
                dataList.add(line);
                line = buf.readLine();
            }
        } catch (IOException e) {
             showError(e.getMessage());
        }
    }

    public void displayRecord(int index) {
        String record = dataList.get(index);
        StringTokenizer token = new StringTokenizer(record, ",");
        txtID.setText(token.nextToken());
        txtName.setText(token.nextToken());
        txtDep.setText(token.nextToken());
        txtDesig.setText(token.nextToken());
    }

    public void showError(String message) {
        Stage stage = new Stage();
        Button bOK = new Button("OK");
        bOK.setMinWidth(50);
        BorderPane pane = new BorderPane();
        pane.setCenter(new Label(message));
        
        HBox pOk = new HBox();
        pOk.getChildren().add(bOK);
        pOk.setPadding(new Insets(5,5,5,70));
        pane.setBottom(pOk);
        Scene scene = new Scene(pane, 200, 100);
        stage.setScene(scene);
        stage.show();
        bOK.setOnAction(e -> {
            stage.close();
        });
    }
    
    public void clearFields(){
        txtID.clear();
            txtName.clear();
            txtDep.clear();
            txtDesig.clear();
    }
    public void setFieldsEditable(boolean edit) {
        txtID.setEditable(edit);
        txtName.setEditable(edit);
        txtDep.setEditable(edit);
        txtDesig.setEditable(edit);
    }
    
    public void addToList(String state){
        String temp=new String();
        boolean check = true;
        if (txtID.getText().length()!=0){
            if(checkID(txtID.getText())){
                temp+=txtID.getText()+",";
                check = check && true;
            }else{
                showError(txtID.getText()+" ID already exists");
                txtID.requestFocus();
                check = check && false;
            }
            
        }else{
            showError("Fill ID!....");
            check = check && false;
        }
        if (txtName.getText().length()!=0){
            temp+=txtName.getText()+",";
            check = check && true;
        }else{
            showError("Fill Name!....");
            check = check && false;
            
        }
        if (txtDep.getText().length()!=0){
            temp+=txtDep.getText()+",";
            check = check && true;
        }else{
            showError("Fill Department");
            check = check && false;
        }
        if (txtDesig.getText().length()!=0){
            temp+=txtDesig.getText();
            check = check && true;
        }else{
            showError("Fill Designation");
            check = check && false;
        }
       
        if(check){
            if(state.equals("m")){
                dataList.remove(cIndex);
                dataList.add(cIndex, temp);
                
            }else{
            dataList.add(temp);
            }
            showError("Record Saved!...");
            clearFields();
            txtID.requestFocus();
            
        }
        
    }
    
    public void addToFile(){
        try{
        FileWriter writer = new FileWriter("data.dat");
        BufferedWriter buf = new BufferedWriter(writer);
        for(int i=0;i<dataList.size()-1;i++){
            buf.write(dataList.get(i)+"\n");
        }
        buf.write(dataList.get(dataList.size()-1));
        buf.close();
        writer.close();
        }catch(IOException e){
            showError("File not found");
        }
        
    }
    
    public void search(int field,String value){
        txtArea.setVisible(true);
        int recordCount=0;
        String[] array= new String[4];
        for(int sub=0; sub<dataList.size();sub++){
            String record = dataList.get(sub);
            StringTokenizer token= new StringTokenizer(record,",");
            for(int i=0; i<4;i++){
                array[i]=token.nextToken();
            }
            
            if(array[field].equalsIgnoreCase(value)){
                
                recordCount++;
                txtArea.appendText("Record "+recordCount+"\n"+record+"\n");
            }
            
        
        }
        if(recordCount==0){
                txtArea.setText("No results found");
            }
    }
    
    public boolean checkID(String id){
        boolean state=true;
        String idS[]= new String[dataList.size()];
        for(int i=0; i<dataList.size();i++){
            String temp=dataList.get(i);
            StringTokenizer token = new StringTokenizer(temp,",");
            idS[i]=token.nextToken();
        }
        for(String x:idS){
            
            if(x.equals(id)){
                
                state = false;
            }
        }
        return state;
    }
    public static void main(String[] args) {
        launch(args);
    }

}
