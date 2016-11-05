package com.app.infideap.expandablerecyclerviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements
    PostFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main, PostFragment.newInstance(1))
                .commit();
    }

    @Override
    public void onListFragmentInteraction(Post item) {

    }
}
