package com.example.project3;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import javafx.event.ActionEvent;
import java.io.File;
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
    private Button test;

    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private DatePicker dob;

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

    //tab 2 (close/deposit/withdraw)
    @FXML
    private TextField acctnum;
    @FXML
    private TextField dorwAmount;
    @FXML
    private Button deposit;
    @FXML
    private Button withdraw;
    @FXML
    private DatePicker closingDate;
    @FXML
    private Button close;
    @FXML
    private Button closeAll;

    //tab 3 (account database)
    @FXML
    private Button loadAccountsFromFile;
    @FXML
    private Button printByHolder;
    @FXML
    private Button printByType;
    @FXML
    private Button printArchive;
    @FXML
    private Button loadActivitiesFromFile;
    @FXML
    private Button printByBranch;
    @FXML
    private Button printAccountStatements;
    @FXML
    private Label print;

    //textArea
    @FXML
    private TextArea textArea;

    /**
     * initialize
     *
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
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

    /**
     * Functionality for the clear all button
     */
    @FXML
    private void clearAll(){
        fname.clear();
        lname.clear();
        dob.setValue(null);
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

    /**
     * Function allows user to select one .txt file
     * @param event
     */
    @FXML
    void fileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);
        if( file != null ){
            System.out.println(file.getAbsolutePath());
        }

    }

}