package itp341.gu.han.finalproject.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import itp341.gu.han.finalproject.app.model.Post;

public class ViewActivity extends AppCompatActivity {

    public static final String VIEW_TYPE = "itp341.gu.han.finalproject.app.view_type";
    public static final String VIEW_POS = "itp341.gu.han.finalproject.app.view_pos";

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String type = "student";
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        final TextView textName = findViewById(R.id.textName);
        final TextView textContact = findViewById(R.id.textContact);
        final TextView textNote = findViewById(R.id.textNote);
        final TextView textTime = findViewById(R.id.textTime);
        final TextView textLocation = findViewById(R.id.textLocation);
        final TextView textSubject = findViewById(R.id.textSubject);
        final Button buttonMap = findViewById(R.id.buttonMap);

        final TextView detailTitle = findViewById(R.id.detailTitle);

        final Button buttonBack = findViewById(R.id.buttonBack);

        Intent intent = getIntent();
        if (intent != null){
            type = intent.getExtras().getString(VIEW_TYPE);
            pos = intent.getIntExtra(VIEW_POS,0);
        }

        detailTitle.setText(type + " info");

        db.collection(type)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        List<Post> ps = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot: value.getDocuments()){
                            Post p = documentSnapshot.toObject(Post.class);
                            ps.add(p);
                        }

                        Post currentPost = ps.get(pos);
                        textName.setText(currentPost.getName());
                        textContact.setText(currentPost.getContact());
                        textLocation.setText(currentPost.getLocation());
                        textNote.setText(currentPost.getNote());
                        textTime.setText(currentPost.getTime());
                        textSubject.setText(currentPost.getSubject());

                    }
                });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewActivity.this, BrowseActivity.class);
                intent.putExtra(BrowseActivity.LIST_TYPE, type);
                startActivity(intent);
            }
        });

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.co.in/maps?q=" + textLocation.getText();
                //String uri = String.format(Locale.ENGLISH, "geo:" + textLocation.getText() + "?q=" + textLocation.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

            }
        });
    }
}
