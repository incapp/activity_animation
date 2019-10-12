package com.incapp.sharedelementanimation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.ClickListener {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setAllowReturnTransitionOverlap(true);
        }

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        List<StudentModel> list = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            list.add(new StudentModel("Student " + i, "Number " + i));
        }

        recyclerView.setAdapter(new MyAdapter(list, MainActivity.this));
    }

    @Override
    public void onClicked(StudentModel studentModel, ImageView imageView, TextView textViewName, TextView textViewNumber) {
        //Do anything with the student model here.
        //Get anything from the database or start a new activity.
        Toast.makeText(MainActivity.this, "Clicked on " + studentModel.getName(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, Main2Activity.class);

        //Start an activity with shared element animation.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(MainActivity.this,
                            Pair.<View, String>create(imageView, "imageViewAnim"),
                            Pair.<View, String>create(textViewName, "textViewNameAnim"),
                            Pair.<View, String>create(imageView, "textViewNumberAnim"));

            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
