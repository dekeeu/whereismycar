package android.whereismycar.Domain;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dekeeu on 07/01/2018.
 */

public class Report {
    private String ID;
    private String Picture;
    private String StreetName;
    private String StreetNo;
    private HashMap<String, HashMap<String, String>> PickedCars;
    //private String PickedCars;
    private String Author;
    private Long Date;
    private String Status;

    public Report() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public String getStreetNo() {
        return StreetNo;
    }

    public void setStreetNo(String streetNo) {
        StreetNo = streetNo;
    }

    /*

    public String getPickedCars() {
        return PickedCars;
    }

    public void setPickedCars(String pickedCars) {
        PickedCars = pickedCars;
    }

    */

    public HashMap<String, HashMap<String, String>> getPickedCars() {
        return PickedCars;
    }

    public void setPickedCars(HashMap<String, HashMap<String, String>> pickedCars) {
        PickedCars = pickedCars;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public Long getDate() {
        return Date;
    }

    public void setDate(Long date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ID", this.ID);
        result.put("Picture", this.Picture);
        result.put("StreetName", this.StreetName);
        result.put("StreetNo", this.StreetNo);
        result.put("PickedCars", this.PickedCars);
        result.put("Author", this.Author);
        result.put("Date", this.Date);
        result.put("Status", this.Status);

        return result;
    }
}
