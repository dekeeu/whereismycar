package android.whereismycar;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.whereismycar.Domain.Car;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class AddCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private static final String[] carBrands = {"Seat", "Opel", "BMW", "Mercedes", "Ferrari"};
    private String selectedBrand;

    private static final String REQUIRED = "REQUIRED";

    private EditText carModel;
    private EditText carColor;
    private EditText carLicensePlate;

    private String _carModel;
    private String _carColor;
    private String _carLicensePlate;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        this.carModel = (EditText)findViewById(R.id.carModelText);
        this.carColor = (EditText)findViewById(R.id.carColorText);
        this.carLicensePlate = (EditText)findViewById(R.id.licensePlateText);

        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance().getReference();

        populateSpinner();
    }

    public void populateSpinner(){
        spinner = (Spinner)findViewById(R.id.brandSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCarActivity.this, android.R.layout.simple_spinner_item, carBrands);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void getInputValues(){
        this._carModel = this.carModel.getText().toString();
        this._carColor = this.carColor.getText().toString();
        this._carLicensePlate = this.carLicensePlate.getText().toString();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedBrand = carBrands[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedBrand = "";
    }

    public void showAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(AddCarActivity.this).create();
        alertDialog.setTitle("Car added !");
        alertDialog.setMessage("The car was added !");

        alertDialog.show();
    }

    public void handleAddCar(View view) {
        getInputValues();

        if(!TextUtils.isEmpty(selectedBrand)){
            if(!TextUtils.isEmpty(_carModel)){
                if(!TextUtils.isEmpty(_carColor)){
                    if(!TextUtils.isEmpty(_carLicensePlate)){
                        String userID = mAuth.getCurrentUser().getUid();

                        String key = mDatabase.child("Cars").push().getKey();
                        Car car = new Car(key, selectedBrand, _carModel, _carColor, _carLicensePlate, userID, 0);
                        Map<String, Object> postValues = car.toMap();

                        mDatabase.child("Cars").child(key).setValue(postValues);
                        mDatabase.child("Users").child(userID).child("owned_cars").child(key).setValue(true);

                        Toast.makeText(this, _carLicensePlate + " was added !", Toast.LENGTH_LONG).show();
                        finish();

                    }else{
                        Toast.makeText(this, R.string.selectLicensePlateNo, Toast.LENGTH_SHORT).show();
                        carLicensePlate.setError(REQUIRED);
                    }
                }else{
                    Toast.makeText(this, R.string.selectCarColor, Toast.LENGTH_SHORT).show();
                    carColor.setError(REQUIRED);
                }
            }else{
                Toast.makeText(this, R.string.selectCarModel, Toast.LENGTH_SHORT).show();
                carModel.setError(REQUIRED);
            }
        }else{
            Toast.makeText(this, R.string.selectCarBrand, Toast.LENGTH_SHORT).show();

        }
    }
}
