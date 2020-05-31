package com.example.connect4.Fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.connect4.R;
import com.example.connect4.Logic.Game;
import com.example.connect4.Utils.ImageAdapter;
import com.example.connect4.Utils.Variables;

public class JuegoFragment extends Fragment {
    public GameListener listener;
    private int SIZE;
    private boolean time;
    private String player1;
    private int countDown = 40;
    private ImageView turn;
    private TextView timing;
    private Game game_instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPreferences();
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        } else {
            game_instance = new Game(SIZE, 4);
        }
        startGridView();
    }

    private void getPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        player1 = prefs.getString(getResources().getString(R.string.user_key), getResources().getString(R.string.user_default));
        time = prefs.getBoolean(getResources().getString(R.string.time_key), true);
        countDown = Integer.valueOf(prefs.getString(getResources().getString(R.string.selectTime_key), "60"));
        SIZE = Integer.valueOf(prefs.getString(getResources().getString(R.string.board_key), getResources().getString(R.string.board_Default_key)));
        timing = (TextView) getView().findViewById(R.id.timing);
        turn = (ImageView) getView().findViewById(R.id.turn);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        this.player1 = savedInstanceState.getString(getResources().getString(R.string.user_key));
        this.time = savedInstanceState.getBoolean(getResources().getString(R.string.time_key));
        this.countDown = savedInstanceState.getInt(getResources().getString(R.string.selectTime_key));
        this.SIZE = savedInstanceState.getInt(getResources().getString(R.string.board_key));
        this.game_instance = savedInstanceState.getParcelable(Variables.ConnectBoard);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Variables.ConnectBoard, (Parcelable) game_instance);
        outState.putString(getResources().getString(R.string.user_key), player1);
        outState.putInt(getResources().getString(R.string.board_key), SIZE);
        outState.putBoolean(getResources().getString(R.string.time_key), time);
        outState.putInt(getResources().getString(R.string.selectTime_key), countDown);
    }

    private void startGridView() {
        ImageAdapter imageAdapter = new ImageAdapter(getActivity(), game_instance, player1, SIZE, time, turn, timing, listener);
        GridView board = (GridView) getView().findViewById(R.id.board);
        board.setAdapter(imageAdapter);
        board.setNumColumns(SIZE);
    }

    public interface GameListener {
        void onGameItemSelected(Integer position, Game gameInstance);
    }

    public void setGameListener(GameListener listener) {
        this.listener = listener;
    }

    public void onAttach(Activity c) {
        super.onAttach(c);
        try {
            listener = (GameListener) c;
        } catch (ClassCastException e) {
            throw new ClassCastException(c.toString() + "No listener");
        }
    }

}
