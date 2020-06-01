package com.example.connect4.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.connect4.Fragments.DetailRegFragment;
import com.example.connect4.Fragments.QueryFragment;
import com.example.connect4.R;

public class AccessBDActivity extends FragmentActivity implements QueryFragment.GameListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        QueryFragment fragmentList = (QueryFragment) getSupportFragmentManager().
                findFragmentById(R.id.ListFrag);
        fragmentList.setGameListener(this);
    }
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onGameSelected(int position) {
        DetailRegFragment fgdet = (DetailRegFragment) getSupportFragmentManager().findFragmentById(R.id.FrgDetalle);
        if (fgdet != null && fgdet.isInLayout()) {
            fgdet.viewDetails(position);
        }
        else {
            Intent intent = new Intent(this, DetailRegActivity.class);
            intent.putExtra(DetailRegActivity.CellSelected, position);
            startActivity(intent);
        }
    }
}
