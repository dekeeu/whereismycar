package android.whereismycar.Domain;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dekeeu on 17/12/2017.
 */

public class Car {

    private String ID;
    private String Brand;
    private String Model;
    private String Color;
    private String LicensePlateNumber;
    private String Owner;
    private int UsageNumber;


    public Car() {
    }

    public Car(String id, String brand, String model, String color, String licensePlateNumber, String owner, Integer usageNumber) {
        ID = id;
        Brand = brand;
        Model = model;
        Color = color;
        LicensePlateNumber = licensePlateNumber;
        Owner = owner;
        UsageNumber = usageNumber;
    }

    public int getUsageNumber() {
        return UsageNumber;
    }

    public void setUsageNumber(int usageNumber) {
        UsageNumber = usageNumber;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getLicensePlateNumber() {
        return LicensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        LicensePlateNumber = licensePlateNumber;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ID", this.ID);
        result.put("Brand", this.Brand);
        result.put("Model", this.Model);
        result.put("Color", this.Color);
        result.put("LicensePlateNumber", this.LicensePlateNumber);
        result.put("Owner", this.Owner);
        result.put("UsageNumber", this.UsageNumber);

        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "ID='" + ID + '\'' +
                ", Brand='" + Brand + '\'' +
                ", Model='" + Model + '\'' +
                ", Color='" + Color + '\'' +
                ", LicensePlateNumber='" + LicensePlateNumber + '\'' +
                ", Owner='" + Owner + '\'' +
                ", UsageNumber=" + UsageNumber +
                '}';
    }
}
