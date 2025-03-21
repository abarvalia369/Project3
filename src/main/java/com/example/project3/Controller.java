package com.example.project3;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.ResourceBundle;
import java.net.URL;

public class Controller implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void open() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private DatePicker dobInput;

    //AccountTypes
    @FXML
    private RadioButton collegeChecking;
    @FXML
    private RadioButton checking;
    @FXML
    private RadioButton savings;
    @FXML
    private RadioButton moneyMarket;
    @FXML
    private RadioButton cd;

    //campus buttons
    @FXML
    private HBox collegeCheckingHBox;
    @FXML
    private RadioButton newBrunswick;
    @FXML
    private RadioButton newark;
    @FXML
    private RadioButton camden;
    @FXML
    private CheckBox loyalCustomer;

    //cd
    @FXML
    private HBox cdHBox;
    @FXML
    private ComboBox<String> termBox;
    @FXML
    private DatePicker cdDate;

    //initialDeposit
    @FXML
    private TextField initialDeposit;

    @FXML
    private ComboBox<String> branchBox;
    @FXML
    private Button open;
    @FXML
    private Button clearAllFields;

    //toggleGroups
    @FXML
    private ToggleGroup accountType;
    @FXML
    private ToggleGroup campuses;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        termBox.setItems(FXCollections.observableArrayList("3","6","9","12"));
        branchBox.setItems(FXCollections.observableArrayList("Edison","Bridgewater","Princeton","Piscataway", "Warren"));
        //campuses
        collegeCheckingHBox.disableProperty().bind(collegeChecking.selectedProperty().not());

        //newBrunswick.disableProperty().bind(collegeChecking.selectedProperty().not());
        //newark.disableProperty().bind(collegeChecking.selectedProperty().not());
        //camden.disableProperty().bind(collegeChecking.selectedProperty().not());

        loyalCustomer.disableProperty().bind(savings.selectedProperty().not().and(moneyMarket.selectedProperty().not()));

        cdHBox.disableProperty().bind(cd.selectedProperty().not());

        //when clicking off college checking campus selection will go back to false etc.
        accountType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            boolean isCollegeChecking = (newValue == collegeChecking);
            if( !isCollegeChecking ){
                campuses.selectToggle(null);
            }

            boolean isSavings = (newValue == savings);
            if( !isSavings ){
                loyalCustomer.setSelected(false);
            }

            boolean isCd = (newValue == cd);
            if( !isCd ){
                termBox.getSelectionModel().clearSelection();
                cdDate.setValue(null);
            }

        });

    }

    @FXML
    private void clearAll(){
        fname.clear();
        lname.clear();
        dobInput.setValue(null);
        collegeChecking.setSelected(false);
        checking.setSelected(false);
        savings.setSelected(false);
        moneyMarket.setSelected(false);
        cd.setSelected(false);
        termBox.getSelectionModel().clearSelection();
        cdDate.setValue(null);
        branchBox.getSelectionModel().clearSelection();
        initialDeposit.clear();
    }

}