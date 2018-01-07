package android.whereismycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.whereismycar.Domain.Report;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ListReportsActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference reportsDatabase;
    private DatabaseReference usersDatabase;

    private FirebaseAuth mAuth;
    private List<Report> reports = new ArrayList<>();
    private HashMap<String, Report> reportMap;

    private boolean userIsAdmin = false;

    ReportListAdapter adapter;

    private void checkAdminState(){
        usersDatabase = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("userIsAdmin");
        usersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ListReports", dataSnapshot.toString());
                userIsAdmin = dataSnapshot.getValue(Boolean.class);
                Log.d("ListReports", "" + userIsAdmin);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void validateReport(Report r){
        if(r.getStatus().equals("pending") && userIsAdmin){
            r.setStatus("approved");
            FirebaseDatabase.getInstance().getReference("/Reports").child(r.getID()).child("Status").setValue("approved");
            adapter.notifyDataSetChanged();
        }
    }

    private boolean userIsAdmin(){
        return userIsAdmin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reports);



        reportMap = new HashMap<>();
        mAuth = FirebaseAuth.getInstance();
        reportsDatabase = FirebaseDatabase.getInstance().getReference("/Reports");
        listView = (ListView)findViewById(R.id.reportsListView);

        adapter = new ReportListAdapter(this, reports); // de aici ia informatie bruta si baga in row in ListView
        listView.setAdapter(adapter);

        checkAdminState();

        SharedPreferences mySharedPreferences = getPreferences(MODE_PRIVATE);
        this.userIsAdmin = mySharedPreferences.getBoolean("userIsAdmin", false);

        reportsDatabase.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String reportID = dataSnapshot.getKey();
                Log.d("ListReports::childAdded", dataSnapshot.getValue().toString());
                Report report = dataSnapshot.getValue(Report.class);
                report.setID(reportID);




                if(userIsAdmin()){
                    Log.d("ListReports", "case1");
                    reports.add(report);
                    adapter.notifyDataSetChanged();

                }else if((Objects.equals(report.getStatus(), "approved") && !userIsAdmin) ||
                        (Objects.equals(report.getStatus(), "pending") && Objects.equals(report.getAuthor(), mAuth.getCurrentUser().getUid()))){

                    reports.add(report);
                    adapter.notifyDataSetChanged();

                    Log.d("ListReports", "case2");
                }else{
                    // Do not show

                    Log.d("ListReports", "case3");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                final String reportChangedID = dataSnapshot.getKey();
                final Report changedReport = dataSnapshot.getValue(Report.class);

                for(Report r : reports){
                    if(r.getID().equals(reportChangedID)){
                        r.setStreetName(changedReport.getStreetName());
                        r.setStreetNo(changedReport.getStreetNo());
                        r.setAuthor(changedReport.getAuthor());
                        r.setDate(changedReport.getDate());
                        r.setPickedCars(changedReport.getPickedCars());
                        r.setPicture(changedReport.getPicture());
                        r.setStatus(changedReport.getStatus());

                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                final String reportDeletedID = dataSnapshot.getKey();

                for(Report r : reports){
                    if(r.getID().equals(reportDeletedID)){
                        reports.remove(r);
                        adapter.notifyDataSetChanged();
                    }
                }

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
                Log.w("ListReports:::", "" + position);
                Report selectedReport = reports.get(position);
                validateReport(selectedReport);
                Log.w("ListReports:::", selectedReport.toString());

            }
        });


    }

    public void redirectUserToAddReport(View view) {
        Intent intent = new Intent(this, AddReportActivity.class);
        startActivity(intent);
    }
}
