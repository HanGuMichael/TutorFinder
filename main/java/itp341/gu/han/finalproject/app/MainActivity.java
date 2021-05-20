package itp341.gu.han.finalproject.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Button buttonStudent;
    private Button buttonTutor;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStudent = findViewById(R.id.buttonStudent);
        buttonTutor = findViewById(R.id.buttonTutor);

        buttonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
                String type = "student";
                intent.putExtra(BrowseActivity.LIST_TYPE, type);
                startActivity(intent);
            }
        });

        buttonTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
                String type = "tutor";
                intent.putExtra(BrowseActivity.LIST_TYPE, type);
                startActivity(intent);
            }
        });
    }
}
