package itp341.gu.han.finalproject.app.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class PostSingleton {
    private static PostSingleton singleton;

    private List<Post> students;
    private List<Post> tutors;

    private Context context;
    private PostSingleton(Context context){
        this.context = context;

        students = new ArrayList<>();
        tutors = new ArrayList<>();

    }

    public static PostSingleton get(Context context){
        if (singleton == null){
            singleton = new PostSingleton(context);
        }

        return singleton;
    }

    public List<Post> getStudents() {
        return students;
    }

    public List<Post> getTutors() {
        return tutors;
    }

    public void addStudent(Post m){
        students.add(m);
    }
    public void removeStudent(int index){
        students.remove(index);
    }

    public void addTutor(Post m){
        tutors.add(m);
    }
    public void removeTutor(int index){
        tutors.remove(index);
    }
}
