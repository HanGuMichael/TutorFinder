package itp341.gu.han.finalproject.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.location.Location;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import itp341.gu.han.finalproject.app.model.Post;

public class DetailActivity extends AppCompatActivity {
    public static final String ADD_TYPE = "itp341.gu.han.finalproject.app.add_type";

    //private String location = "preset location";

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    private String type = "student";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null){
            type = intent.getExtras().getString(ADD_TYPE);
        }

        final Button buttonSubmit = findViewById(R.id.buttonSubmit);
        final EditText editName = findViewById(R.id.editName);
        final EditText editSubject = findViewById(R.id.editSubject);
        final EditText editContact = findViewById(R.id.editContact);
        final EditText editNote = findViewById(R.id.editNote);
        final EditText editTime = findViewById(R.id.editTime);
        final EditText editLocation = findViewById(R.id.editLocation);
        final Button buttonLocation = findViewById(R.id.buttonLocation);
        final TextView detailTitle = findViewById(R.id.detailTitle);

        detailTitle.setText(type + " info");

        ActivityCompat.requestPermissions(this, new
                String[]{Manifest.permission.ACCESS_FINE_LOCATION},22);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);





        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = editName.getText().toString();
                final String subject = editSubject.getText().toString();
                final String contact = editContact.getText().toString();
                final String note = editNote.getText().toString();
                final String time = editTime.getText().toString();
                final String location = editLocation.getText().toString();

                final Post newPost = new Post(name,subject,time,note,contact,location);

                db.collection(type).add(newPost);

                Intent intent = new Intent(DetailActivity.this, BrowseActivity.class);
                intent.putExtra(BrowseActivity.LIST_TYPE, type);
                startActivity(intent);
            }
        });

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location l;
                try {
                    l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    String loc= Double.toString(l.getLatitude()) + "," + Double.toString(l.getLongitude());
                    editLocation.setText(loc);
                } catch (SecurityException e) {
                    Log.d("permisson", "location"); // lets the user know there is a problem with the gps
                }
            }
        });

    }
}
