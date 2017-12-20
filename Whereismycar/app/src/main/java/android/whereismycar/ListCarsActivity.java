package android.whereismycar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.whereismycar.Domain.Car;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ListCarsActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference databaseReference;
    private DatabaseReference userDatabaseReference;
    private FirebaseListAdapter firebaseListAdapter;
    private CarListAdapter carListAdapter;

    private FirebaseAuth mAuth;
    private Map<String, Car> carMap;
    private List<Car> cars = new ArrayList<>();

    public static final String selectedCarID = "selectedCarID";
    public static final String selectedCarBrand = "selectedCarBrand";
    public static final String selectedCarModel = "selectedCarModel";
    public static final String selectedCarColor = "selectedCarColor";
    public static final String selectedCarLicensePlate = "selectedLicensePlate";
    public static final String selectedCarUsageNo = "selectedCarUsageNo";

    public static final String namesDataset = "carNamesDataset";
    public static final String usageDataset = "carUsageDataset";

    public ArrayList<String> carNamesDataset = new ArrayList<>();
    public ArrayList<Integer> carUsageDataset = new ArrayList<>();

    Logger l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cars);

        carMap = new HashMap<>();

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Cars");
        userDatabaseReference = FirebaseDatabase.getInstance().getReference("Users" + "/" + mAuth.getCurrentUser().getUid() + "/owner_cars");
        listView = (ListView)findViewById(R.id.carListView);

        final CarListAdapter adapter = new CarListAdapter(this, cars);
        listView.setAdapter(adapter);


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String carID = dataSnapshot.getKey();
                Car car = dataSnapshot.getValue(Car.class);

                if(car.getOwner().equals(mAuth.getCurrentUser().getUid())){
                    cars.add(car);
                    adapter.notifyDataSetChanged();

                    carNamesDataset.add(car.getBrand() + " " + car.getModel());
                    carUsageDataset.add(car.getUsageNumber());
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                final String carChangedID = dataSnapshot.getKey();
                final Car changedCar = dataSnapshot.getValue(Car.class);

                if(changedCar.getOwner().equals(mAuth.getCurrentUser().getUid())){
                    for(Car c : cars){
                        Log.w("ListCars", carChangedID);
                        Log.w("ListCars",c.toString());
                        if(c.getID().equals(carChangedID)){
                            c.setBrand(changedCar.getBrand());
                            c.setModel(changedCar.getModel());
                            c.setColor(changedCar.getColor());
                            c.setLicensePlate(changedCar.getLicensePlate());
                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                final String carChangedID = dataSnapshot.getKey();
                final Car changedCar = dataSnapshot.getValue(Car.class);

                if(changedCar.getOwner().equals(mAuth.getCurrentUser().getUid())){
                    for(Car c : cars){
                        if(c.getID().equals(carChangedID)){
                            cars.remove(c);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.w("ListCars:::", "" + position);

                Car selectedCar = cars.get(position);

                Intent intent = new Intent(ListCarsActivity.this, EditCarActivity.class);
                intent.putExtra(selectedCarID, selectedCar.getID());
                intent.putExtra(selectedCarBrand, selectedCar.getBrand());
                intent.putExtra(selectedCarModel, selectedCar.getModel());
                intent.putExtra(selectedCarColor, selectedCar.getColor());
                intent.putExtra(selectedCarLicensePlate, selectedCar.getLicensePlate());
                intent.putExtra(selectedCarUsageNo, selectedCar.getUsageNumber());


                Log.w("ListCars", carNamesDataset.toString());
                Log.w("ListCars", carUsageDataset.toString());
                intent.putExtra(namesDataset, carNamesDataset);
                intent.putExtra(usageDataset, carUsageDataset);


                startActivity(intent);
            }
        });

    }

}
