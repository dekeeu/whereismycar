package android.whereismycar;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.whereismycar.Domain.Car;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private static final String[] carBrands = {"Seat", "Opel", "BMW", "Mercedes", "Ferrari"};
    private String selectedBrand = "";

    private String receivedCarID;
    private String receivedCarBrand;
    private String receivedCarModel;
    private String receivedCarColor;
    private String receivedCarLicensePlate;
    private int receivedCarUsageNo;

    private EditText carModel;
    private EditText carColor;
    private EditText carLicensePlate;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference db;


    public ArrayList<String> carNamesDataset = new ArrayList<>();
    public ArrayList<Integer> carUsageDataset = new ArrayList<>();

    public PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        carModel = (EditText)findViewById(R.id.modelTextEditCar);
        carColor = (EditText)findViewById(R.id.colorTextEditCar);
        carLicensePlate = (EditText)findViewById(R.id.licensePlateTextEditCar);

        pieChart = (PieChart)findViewById(R.id.pieChart);

        receivedCarID = getIntent().getStringExtra(ListCarsActivity.selectedCarID);
        receivedCarBrand = getIntent().getStringExtra(ListCarsActivity.selectedCarBrand);
        receivedCarModel = getIntent().getStringExtra(ListCarsActivity.selectedCarModel);
        receivedCarColor = getIntent().getStringExtra(ListCarsActivity.selectedCarColor);
        receivedCarLicensePlate = getIntent().getStringExtra(ListCarsActivity.selectedCarLicensePlate);
        receivedCarUsageNo = getIntent().getIntExtra(ListCarsActivity.selectedCarUsageNo, 0);

        carNamesDataset = getIntent().getStringArrayListExtra(ListCarsActivity.namesDataset);
        carUsageDataset = getIntent().getIntegerArrayListExtra(ListCarsActivity.usageDataset);

        populateSpinner();
        populateFields();
        populatePieChart();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void populatePieChart(){
        pieChart.setDescription("Cars usage statistics:");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.animateY(5000);

        ArrayList<Entry> entries = new ArrayList<>();
        int i=0;

        for(int usageNumber : carUsageDataset){
            entries.add(new Entry((float)usageNumber, i++));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(carNamesDataset, pieDataSet);
        pieChart.setData(data);
    }


    public void populateSpinner(){
        spinner = (Spinner)findViewById(R.id.spinnerEditCar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditCarActivity.this, android.R.layout.simple_spinner_item, carBrands);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void populateFields(){
        for(int i=0; i<carBrands.length; i++){
            if(carBrands[i].equals(receivedCarBrand)){
                spinner.setSelection(i);
                break;
            }
        }

        carModel.setText(receivedCarModel);
        carColor.setText(receivedCarColor);
        carLicensePlate.setText(receivedCarLicensePlate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedBrand = carBrands[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedBrand = "";
    }

    public void handleEditCar(View view) {
        Car c = new Car(receivedCarID,
                selectedBrand,
                carModel.getText().toString(),
                carColor.getText().toString(),
                carLicensePlate.getText().toString(),
                currentUser.getUid(),
                receivedCarUsageNo);

        Map<String, Object> postValues = c.toMap();

        FirebaseDatabase.getInstance().getReference("Cars").child(receivedCarID).updateChildren(postValues);
        finish();
    }

    public void handleDeleteCar(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure ?")
                .setMessage("Are you sure you want to delete : " + carLicensePlate.getText().toString())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("Cars").child(receivedCarID).removeValue();
                        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid()).child(receivedCarID).removeValue();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing
                    }
                })
                .show();
    }
}
