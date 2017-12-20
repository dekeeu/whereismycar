package android.whereismycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    protected void redirectUserToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    protected void redirectUserToProfile(){
        Intent intent = new Intent(this, MyProfileActivity.class);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = this.mAuth.getCurrentUser();
        if(firebaseUser != null){
            redirectUserToProfile();
        }else{
            redirectUserToLogin();
        }



        Log.d("onCreate","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("onStart","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("onResume","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("onPause","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("onStop","onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("onRestart","onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("onDestroy","onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
