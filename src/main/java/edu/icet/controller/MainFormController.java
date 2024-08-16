package edu.icet.controller;

import com.jfoenix.controls.JFXTextField;
import edu.icet.db.DBConnection;
import edu.icet.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    private static List<String> idArrayList =new ArrayList<>();
    private static int counter=0;

    @FXML
    private ComboBox<String> cmbTitle;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colDob;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colNumber;

    @FXML
    private TableColumn colTitle;

    @FXML
    private DatePicker dateDob;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id= txtId.getText();
        String title= cmbTitle.getValue();
        String name =txtName.getText();
        String address=txtAddress.getText();
        String number =txtNumber.getText();
        LocalDate dob= dateDob.getValue();
        if(id !=null && null!=title && name!=null & address!= null && number != null && dob!=null ){
            Customer customer =new Customer(id,title,name,address,number,dob);
            List<Customer> customerList = DBConnection.getInstance().getConnection();
            customerList.add(customer);
            loadTable();
            clear();
        }else{
            System.out.println("Null txt fields here!");
        }


    }


    @FXML
    void btnReloadOnAction(ActionEvent event) {

        loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        tblCustomers.refresh();
        String id = txtSearch.getText();
        if(id!=null){

            List<Customer> customerList = DBConnection.getInstance().getConnection();
            customerList.forEach(element->{
                if(element.getId().equals(id)){
                    loadDetailsForm(element);

                }
            });

        }else{
            System.out.println("null");
            System.out.println(id);

            // update dialog menu 
        }

    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id= txtId.getText();

        List<Customer> customerList=DBConnection.getInstance().getConnection();
        for (Customer customer :customerList){
            if (customer.getId().equals(id)){
                removeData(customer);
                break;

            }
        }
        tblCustomers.refresh();
        loadTable();
        clear();
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id= txtId.getText();
        String title= cmbTitle.getValue();
        String name =txtName.getText();
        String address=txtAddress.getText();
        String number =txtNumber.getText();
        LocalDate dob= dateDob.getValue();

        List<Customer> customerList = DBConnection.getInstance().getConnection();
        customerList.forEach(obj->{
            if(obj.getId().equals(id)){
                obj.setName(name);
                obj.setTitle(title);
                obj.setAddress(address);
                obj.setNumber(number);
                obj.setDob(dob);

            }


        });
        tblCustomers.refresh();



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setText(generateId());
        idArrayList.add(txtId.getText());
        loadTable();
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("MR.");
        titles.add("Miss.");
        cmbTitle.setItems(titles);
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) ->{
            loadDetailsForm(newValue);

        });

    }
    private void clear(){

        txtId.setText(generateId());
        cmbTitle.setValue(null);
        txtName.setText(null);
        txtAddress.setText(null);
        txtNumber.setText(null);
        dateDob.setValue(null);
    }
    private void loadDetailsForm(Customer customer){
        if(customer!=null){
            txtId.setText(customer.getId());
            cmbTitle.setValue(customer.getTitle());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtNumber.setText(customer.getNumber());
            dateDob.setValue(customer.getDob());

        }

    }
    private void loadTable(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        List<Customer> customerList = DBConnection.getInstance().getConnection();

        customerList.forEach( obj->{
            customerObservableList.add(obj);
        });



        tblCustomers.setItems(customerObservableList);
    }
    private void removeData(Customer customer){
        List<Customer> customerList = DBConnection.getInstance().getConnection();
        customerList.remove(customer);
    }
    private static synchronized String generateId() {
        counter++;
        return String.format("C%03d", counter);
    }


    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }
}
