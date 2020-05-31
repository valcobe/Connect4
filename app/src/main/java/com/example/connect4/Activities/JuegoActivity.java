package com.example.connect4.Activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.connect4.Fragments.JuegoFragment;
import com.example.connect4.Fragments.LogFragment;
import com.example.connect4.Logic.Game;
import com.example.connect4.R;
import com.example.connect4.Utils.LogCreator;

public class JuegoActivity extends FragmentActivity implements JuegoFragment.GameListener {

    private LogCreator logCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        JuegoFragment gameF = (JuegoFragment) getSupportFragmentManager().findFragmentById
                (R.id.fragmentGame);

        gameF.setGameListener(this);
        logCreator = LogCreator.getINSTANCE(this);
    }

    @Override
    public void onGameItemSelected(Integer position, Game gameInstance){
        LogFragment gameLogsFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.logFragment);

        if(gameLogsFragment != null && gameLogsFragment.isInLayout()){
            LogFragment gameFragmentDetail = (LogFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.logFragment);
            gameFragmentDetail.mostrarLogs(logCreator.logValues(gameInstance, position));
        }
    }
}
