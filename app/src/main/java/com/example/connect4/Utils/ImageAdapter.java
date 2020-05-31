package com.example.connect4.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect4.Fragments.JuegoFragment;
import com.example.connect4.Logic.Player;
import com.example.connect4.R;
import com.example.connect4.Activities.ResultadosActivity;
import com.example.connect4.Logic.Game;

public class ImageAdapter extends BaseAdapter {

    private Activity mContext;
    private Game game_instance;
    private boolean timeNotNull;
    private TextView time;
    private ImageView turn;
    private int SIZE;
    private String alias;
    private JuegoFragment.GameListener listener;


    public ImageAdapter(Activity c, Game game_instance, String alias, int size,
                        boolean timeNotNull, ImageView turn, TextView time,
                        JuegoFragment.GameListener listener){

        this.listener = listener;
        mContext = c;
        this.game_instance = game_instance;
        this.SIZE = size;
        this.time = time;
        this.timeNotNull = timeNotNull;
        this.alias = alias;
        this.turn = turn;
    }

    private void updateTime() {
        if (timeNotNull) {
            time.setText(String.valueOf(game_instance.getTimePlayed() / Variables.SEGON));
            time.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            time.setText(String.valueOf((System.currentTimeMillis() / Variables.SEGON) - game_instance.getTimePlayed()));
            time.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        }
    }

    @Override
    public int getCount() {
        return SIZE *SIZE;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn;
        if (convertView == null) {
            btn = new Button(mContext);
        }
        else {
            btn = (Button) convertView;
        }

        btn.setBackgroundResource(setPiece(position));
        btn.setOnClickListener(new MyOnClickListener(position, mContext));
        btn.setId(position);
        return btn;
    }

    private int setPiece(int position) {
        if(game_instance.getPlayerPosition(position) == 1) {
            return R.drawable.celausuari;
        }
        else if(game_instance.getPlayerPosition(position) == 2) {
            return R.drawable.celaoponent;
        }
        return R.drawable.celabuida;
    }

    private void updateTextViews() {
        if (game_instance.getTurn() == Player.player1()){
            this.turn.setImageResource(R.drawable.celausuari);
        }
        else {
            this.turn.setImageResource(R.drawable.celaoponent);
        }
    }
    private void createNewActivity() {
        int timeLeft;
        if (timeNotNull) {
            if(game_instance.timeEnd){
                timeLeft =0;
            }else {
                timeLeft = (int)game_instance.getTimePlayed() / Variables.SEGON;
            }
        } else {
            timeLeft = (int) (System.currentTimeMillis() / Variables.SEGON - game_instance.getTimePlayed());
        }
        Intent intent = new Intent(mContext, ResultadosActivity.class);
        intent.putExtra(Variables.USER, alias);
        intent.putExtra(Variables.TIME, timeNotNull);
        intent.putExtra(Variables.TIME_LEFT, timeLeft);
        intent.putExtra(Variables.SIZE, SIZE);
        if(game_instance.getTurn().equals(Player.player1())) {
            intent.putExtra("turn", 1);
        }
        else {
            intent.putExtra("turn", 2);
        }
        mContext.startActivity(intent);
        mContext.finish();
        LogCreator.deleteLog();
    }

    private int randomCPU() {
        int randomInt = 100;
        while (game_instance.drop(randomInt%(game_instance.size)) != null) {
            randomInt = (int) (Math.random() * (SIZE * SIZE));
        }
        return randomInt;
    }


    private class MyOnClickListener implements View.OnClickListener {
        private int position;
        private Context context;

        MyOnClickListener(int position, Context context) {
            this.position = position;
            this.context = context;

        }

        public void onClick(View v) {
            if(game_instance.drop(position%(game_instance.size)) != null) {
                doTheMovement(position);
                if (isFinal()){
                    createNewActivity();
                }
                else {
                    position = randomCPU();
                    doTheMovement(position);
                    if (isFinal()) {
                        createNewActivity();
                    }
                }
            }
            else {
                Toast.makeText(context, "Invalid Movement. Try again", Toast.LENGTH_SHORT).show();
            }
        }
        private void doTheMovement(int position) {
            game_instance.drop(position%SIZE);
            game_instance.toggleTurn();
            update();
            listener.onGameItemSelected(position, game_instance);
        }
        private void update() {
            updateTextViews();
            updateTime();
            notifyDataSetChanged();
        }

        private boolean isFinal() {
            if (game_instance.checkForFinish()) {
                return true;
            } else {
                if (game_instance.timeEnd){
                    return true;
                }
                else {
                    return false;
                }
            }
        }
    }
}
