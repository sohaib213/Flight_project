package com.example.flight_project_1;

import com.example.flight_project_1.Base_classes.Files;
import com.example.flight_project_1.Base_classes.Flight;
import com.example.flight_project_1.Base_classes.Passenger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.EventObject;

public class UserProfile {
    public Button open_edit;
    public Button edit_username;
    public Button edit_password;
    public Button edit_contact;
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    private Label pass_username;
    @FXML
    private Label pass_phone;
    @FXML
    private Label pass_Id;
    @FXML
    private TextField user_edit;
    @FXML
    private PasswordField pass_edit;
    @FXML
    private TextField contact_edit;
    private Passenger user;
    private int sceneId;
    private int pass_index;
    Flight flight;
    Alert alert;
    ArrayList<Passenger> passengers=new ArrayList<>();

    public void assignFlight(Flight flight){
        this.flight = flight;
    }

    public void assignUser_sceneId(Passenger user, int sceneId){
        this.sceneId = sceneId;
        this.user = user;
        pass_username.setText("Username: "+user.getName());
        pass_phone.setText("Contact: "+user.getPhone());
        pass_Id.setText("Passenger ID: "+user.getPassenger_ID());

        //get passenger index in passenger.txt
        try {
            File file=new File("Passenger.txt");
            FileInputStream fis=new FileInputStream(file);
            ObjectInputStream ois=new ObjectInputStream(fis);
            if(file.length() > 0) {
                passengers = (ArrayList<Passenger>) ois.readObject();
                int size = passengers.size();
                for (int i = 0; i < size; i++) {
                    if (user.getName().toLowerCase().equals(passengers.get(i).getName().toLowerCase())) {
                        pass_index = i;
                        break;
                    }
                }
            }
        }
        catch (Exception exe) {
            System.out.println("Error when searching for a unique user"+exe);
        }
    }
    public void GoToEditProfile(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(Multi_used_methods.class.getResource("editProfileScene.fxml"));
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            ActionEvent e = null;
            UserProfile Up = loader.getController();
            //Up.editProfile(e);
            Up.assignFlight(flight);

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println("Can't Open editProfileScene.fxml");
        }
    }
    public void editUsername(){
        String username = user_edit.getText();
        if (username == null || username.trim().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username ,Contact Info Or Password Are Empty");
            alert.setContentText("Username ,Contact Info and Password are Required");
            alert.showAndWait();
        }
        else {
            boolean NameNotFound = true;
            try {
                File file=new File("Passenger.txt");
                FileInputStream fis=new FileInputStream(file);
                ObjectInputStream ois=new ObjectInputStream(fis);
                if(file.length() > 0) {
                    passengers = (ArrayList<Passenger>) ois.readObject();
                    int size = passengers.size();
                    for (int i = 0; i < size; i++) {
                        if (username.toLowerCase().equals(passengers.get(i).getName().toLowerCase())) {
                            NameNotFound = false;
                            break;
                        }
                    }
                }
            }
            catch (Exception EA) {
                System.out.println("Error when searching for a unique user"+EA);
            }
            if (!NameNotFound) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Username is used ");
                alert.setContentText("Username must be unique");
                alert.showAndWait();
            } else {
                try {
                    System.out.println(passengers.size());
                    Files.getPassengers().get(pass_index).setName(username);
                    user_edit.setText("Username edit done!");
                    System.out.println(passengers.size());
                }
                catch (Exception exception) {
                    System.out.println("Error in editing username: " + exception);
                }
            }
        }
    }
    public void editPassword(){
        String password = pass_edit.getText();
        if (password == null || password.trim().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username ,Contact Info Or Password Are Empty");
            alert.setContentText("Username ,Contact Info and Password are Required");
            alert.showAndWait();
        } else if (password.length() < 6) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password is less than 6 characters");
            alert.setContentText("Password is required more than 6 chars");
            alert.showAndWait();
        }
        else {
            try {
                System.out.println(passengers.size());
                Files.getPassengers().get(pass_index).setPassword(password);
                pass_edit.setText("Password edit done!");
                System.out.println(passengers.size());
            }
            catch (Exception exception) {
                System.out.println("Error in adding user: " + exception);
            }
        }
    }
    public void editContact(){
        String contact = contact_edit.getText();
        if (contact == null || contact.trim().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username ,Contact Info Or Password Are Empty");
            alert.setContentText("Username ,Contact Info and Password are Required");
            alert.showAndWait();
        }
        else {
            try {
                System.out.println(passengers.size());
                Files.getPassengers().get(pass_index).setPassword(contact);
                contact_edit.setText("Contact edit done!");
                System.out.println(passengers.size());
            }
            catch (Exception exception) {
                System.out.println("Error in adding user: " + exception);
            }
        }
    }
    public void backFromUserProfile(ActionEvent e) {
        // To Flight Search scene
        if(sceneId == 1)
            Multi_used_methods.openFlightSearch(e, user);
            // To Flight Show scene
        else if(sceneId == 2)
            Multi_used_methods.GoToFlightShow(e, flight, user);
            // To Flight Seat Selection scene
        else if (sceneId == 3)
            Multi_used_methods.GoToChooseSeat(e, flight, user);

    }
    public void backToUserProfil(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userProfileScene.fxml"));
            root = loader.load();
        } catch (IOException e) {
            System.out.println("Can't Open userSign.fxml");
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
