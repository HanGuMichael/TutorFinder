package itp341.gu.han.finalproject.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import itp341.gu.han.finalproject.app.BrowseActivity;
import itp341.gu.han.finalproject.app.model.Post;

public class BrowseActivity extends AppCompatActivity {
    public static final String LIST_TYPE = "itp341.gu.han.finalproject.app.list_type";

    private static final int REQUEST_CODE_VIEW_ACTIVITY = 100;


    public String type = "student";

    private ListView listView;
    private Button buttonHome;
    private Button buttonAdd;
    private TextView textTitle;

    private PostAdapter postAdapter;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Post> posts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);



        Intent intent = getIntent();
        if (intent != null){
            type = intent.getExtras().getString(LIST_TYPE);
        }
        //get data from database to list
        buttonAdd = findViewById(R.id.button_add);
        buttonHome = findViewById(R.id.button_home);

        textTitle = findViewById(R.id.textTitle);

        listView = findViewById(R.id.listView);

        textTitle.setText(type + " list");

        postAdapter = new PostAdapter(this, posts);
        listView.setAdapter(postAdapter);

        //Post p1 = new Post("michael", "Computer Science", "Friday", "note", "hangu@usc.edu", "shanghai");

        db.collection(type)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        List<Post> ps = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot: value.getDocuments()){
                            Post p = documentSnapshot.toObject(Post.class);
                            ps.add(p);
                        }

                        //arrayAdapter.clear();
                        //arrayAdapter.addAll(notes);
                        postAdapter.clear();
                        postAdapter.addAll(ps);
                        postAdapter.notifyDataSetChanged();
                    }
                });


        //Log.d("check", "setAdapter done");

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowseActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.ADD_TYPE,type);
                startActivity(intent);
            }
        });



        /*
        db.collection(type)
                .whereIn("completed", Arrays.asList(true, false))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        List<Note> notes = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot: value.getDocuments()){
                            Note note = documentSnapshot.toObject(Note.class);
                            notes.add(note);
                        }

                        arrayAdapter.clear();
                        arrayAdapter.addAll(notes);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
*/


    }

    private class PostAdapter extends ArrayAdapter<Post> {
        private List<Post> posts;

        public PostAdapter(Context context, List<Post> posts){
            super(context, R.layout.movie_row, posts);
            this.posts = posts;
            Log.d("check", "In PostAdapter Constructor");
            Log.d("count", Integer.toString(posts.size()));
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            // 1. We have a convertview we can work with
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_row, parent, false);
            }

            // 2. Get the data for given position
            final Post post = posts.get(position);

            Log.d("check", post.getName());

            // 3. Update our convert view with data from model
            final TextView textViewTitle = convertView.findViewById(R.id.textViewName);
            final TextView textViewSubtitle = convertView.findViewById(R.id.textViewSubject);
            final Button detailButton = convertView.findViewById(R.id.buttonDetails);
            detailButton.setTag(position);

            detailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BrowseActivity.this, ViewActivity.class);
                    //intent.putExtra(DetailActivity.MOVIE, movie);
                    intent.putExtra(ViewActivity.VIEW_POS,position);
                    intent.putExtra(ViewActivity.VIEW_TYPE,type);
                    startActivity(intent);
                }
            });



            textViewTitle.setText(post.getName());
            textViewSubtitle.setText(post.getSubject());


            // 4. Return the end result
            return convertView;
        }
    }
}


