package com.havodion.miss2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends ActionBarActivity {
    private ArrayList<TextView> blocks = new ArrayList<>();
    private ArrayList<String> oldBlocks = new ArrayList<>();
    private ArrayList<GradientDrawable> shapeList = new ArrayList<>();
    private Random randomGenerator = new Random();
    private TextView scoreBox;
    private TextView highScoreBox;
    private boolean beatScore;
    private boolean hasBack;
    private SharedPreferences sharedPreferences;
    private int moveCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout background = (RelativeLayout) findViewById(R.id.layoutBackgound);

        // Create Listeners for swipe events
        background.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeTop() {
                swipe('T');
            }

            public void onSwipeBottom() {
                swipe('B');
            }

            public void onSwipeRight() {
                swipe('R');
            }

            public void onSwipeLeft() {
                swipe('L');
            }
        });
        // TextView[][] blocksArray = new TextView[4][4];
        // Add all the blocks to the blocks array
        blocks.add((TextView) findViewById(R.id.Box1));
        blocks.add((TextView) findViewById(R.id.Box2));
        blocks.add((TextView) findViewById(R.id.Box3));
        blocks.add((TextView) findViewById(R.id.Box4));
        blocks.add((TextView) findViewById(R.id.Box5));
        blocks.add((TextView) findViewById(R.id.Box6));
        blocks.add((TextView) findViewById(R.id.Box7));
        blocks.add((TextView) findViewById(R.id.Box8));
        blocks.add((TextView) findViewById(R.id.Box9));
        blocks.add((TextView) findViewById(R.id.Box10));
        blocks.add((TextView) findViewById(R.id.Box11));
        blocks.add((TextView) findViewById(R.id.Box12));
        blocks.add((TextView) findViewById(R.id.Box13));
        blocks.add((TextView) findViewById(R.id.Box14));
        blocks.add((TextView) findViewById(R.id.Box15));
        blocks.add((TextView) findViewById(R.id.Box16));

        scoreBox = (TextView) findViewById(R.id.Score);
        highScoreBox = (TextView) findViewById(R.id.HighScore);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        AddListeners();

        setColors();
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = sharedPreferences.edit();

        ed.putString("HighScore", highScoreBox.getText().toString());
        ed.putString("Score", scoreBox.getText().toString());
        ed.putBoolean("BeatScore", beatScore);
        ed.putBoolean("hasBack", hasBack);
        ed.putInt("moveCount", moveCount);

        ed.putString("Box1", blocks.get(0).getText().toString());
        ed.putString("Box2", blocks.get(1).getText().toString());
        ed.putString("Box3", blocks.get(2).getText().toString());
        ed.putString("Box4", blocks.get(3).getText().toString());
        ed.putString("Box5", blocks.get(4).getText().toString());
        ed.putString("Box6", blocks.get(5).getText().toString());
        ed.putString("Box7", blocks.get(6).getText().toString());
        ed.putString("Box8", blocks.get(7).getText().toString());
        ed.putString("Box9", blocks.get(8).getText().toString());
        ed.putString("Box10", blocks.get(9).getText().toString());
        ed.putString("Box11", blocks.get(10).getText().toString());
        ed.putString("Box12", blocks.get(11).getText().toString());
        ed.putString("Box13", blocks.get(12).getText().toString());
        ed.putString("Box14", blocks.get(13).getText().toString());
        ed.putString("Box15", blocks.get(14).getText().toString());
        ed.putString("Box16", blocks.get(15).getText().toString());

        ed.putString("oldBox1", oldBlocks.get(0));
        ed.putString("oldBox2", oldBlocks.get(1));
        ed.putString("oldBox3", oldBlocks.get(2));
        ed.putString("oldBox4", oldBlocks.get(3));
        ed.putString("oldBox5", oldBlocks.get(4));
        ed.putString("oldBox6", oldBlocks.get(5));
        ed.putString("oldBox7", oldBlocks.get(6));
        ed.putString("oldBox8", oldBlocks.get(7));
        ed.putString("oldBox9", oldBlocks.get(8));
        ed.putString("oldBox10", oldBlocks.get(9));
        ed.putString("oldBox11", oldBlocks.get(10));
        ed.putString("oldBox12", oldBlocks.get(11));
        ed.putString("oldBox13", oldBlocks.get(12));
        ed.putString("oldBox14", oldBlocks.get(13));
        ed.putString("oldBox15", oldBlocks.get(14));
        ed.putString("oldBox16", oldBlocks.get(15));
        ed.putString("oldScore", scoreBox.getText().toString());
        ed.putString("oldHighScore", highScoreBox.getText().toString());

        ed.apply();
    }

    @Override
    public void onResume() {
        super.onResume();

        highScoreBox.setText(sharedPreferences.getString("HighScore", "0"));
        scoreBox.setText(sharedPreferences.getString("Score", "0"));
        beatScore = sharedPreferences.getBoolean("BeatScore", false);
        hasBack = sharedPreferences.getBoolean("hasBack", false);
        moveCount = sharedPreferences.getInt("moveCount", 0);

        blocks.get(0).setText(sharedPreferences.getString("Box1", ""));
        blocks.get(1).setText(sharedPreferences.getString("Box2", ""));
        blocks.get(2).setText(sharedPreferences.getString("Box3", ""));
        blocks.get(3).setText(sharedPreferences.getString("Box4", ""));
        blocks.get(4).setText(sharedPreferences.getString("Box5", ""));
        blocks.get(5).setText(sharedPreferences.getString("Box6", ""));
        blocks.get(6).setText(sharedPreferences.getString("Box7", ""));
        blocks.get(7).setText(sharedPreferences.getString("Box8", ""));
        blocks.get(8).setText(sharedPreferences.getString("Box9", ""));
        blocks.get(9).setText(sharedPreferences.getString("Box10", ""));
        blocks.get(10).setText(sharedPreferences.getString("Box11", ""));
        blocks.get(11).setText(sharedPreferences.getString("Box12", ""));
        blocks.get(12).setText(sharedPreferences.getString("Box13", ""));
        blocks.get(13).setText(sharedPreferences.getString("Box14", ""));
        blocks.get(14).setText(sharedPreferences.getString("Box15", ""));
        blocks.get(15).setText(sharedPreferences.getString("Box16", ""));

        oldBlocks.add(sharedPreferences.getString("oldBox1", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox2", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox3", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox4", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox5", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox6", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox7", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox8", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox9", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox10", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox11", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox12", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox13", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox14", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox15", ""));
        oldBlocks.add(sharedPreferences.getString("oldBox16", ""));
        oldBlocks.add(sharedPreferences.getString("oldScore", ""));
        oldBlocks.add(sharedPreferences.getString("oldHighScore", ""));

        int openBlocks = 0;
        for (int i = 0; i < 16; i++) {
            String s = blocks.get(i).getText() + "";
            if (s.equals("")) openBlocks++;
        }

        if (openBlocks == 16) {
            generateRandomBlock();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_undo).setVisible(hasBack);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with Love action_refresh was selected
            case R.id.action_love:
                final Context context = this;
                Intent intent = new Intent(context, Fullscreen.class);
                startActivity(intent);
                break;
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                new AlertDialog.Builder(this)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setTitle("Reset")
                        .setMessage("Are you sure you want to reset your game?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                newGame();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.action_undo:
                if (hasBack) {
                    for (int i = 0; i < 16; i++) {
                        blocks.get(i).setText(oldBlocks.get(i));
                    }
                    scoreBox.setText(oldBlocks.get(16));
                    highScoreBox.setText(oldBlocks.get(17));
                    hasBack = false;
                    moveCount = 2;
                    invalidateOptionsMenu();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void setColors() {
        // Set static colors that will be used in the program
        ArrayList<String> colorList = new ArrayList<>();
        colorList.add("#E6E6E6");
        colorList.add("#F5EBFF");
        colorList.add("#EBD6FF");
        colorList.add("#E0C2FF");
        colorList.add("#D6ADFF");
        colorList.add("#CC99FF");
        colorList.add("#C285FF");
        colorList.add("#B870FF");
        colorList.add("#AD5CFF");
        colorList.add("#A347FF");
        colorList.add("#9933FF");
        colorList.add("#8A2EE6");
        colorList.add("#7A29CC");
        colorList.add("#6B24B2");
        colorList.add("#5C1F99");
        colorList.add("#4C1A80");
        colorList.add("#3D1466");

        // Add the colors to shapes
        for (String s : colorList) {
            GradientDrawable shape = new GradientDrawable();
            shape.setColor(Color.parseColor(s));
            shape.setStroke(3, Color.parseColor("#00000000"));

            shapeList.add(shape);
        }
    }

    private void AddListeners() {
        blocks.get(0).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(0).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(1).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(1).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(2).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(2).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(3).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(3).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(4).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(4).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(5).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(5).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(6).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(6).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(7).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(7).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(8).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(8).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(9).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(9).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(10).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(10).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(11).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(11).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(12).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(12).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(13).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(13).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(14).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(14).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        blocks.get(15).addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                setColor(blocks.get(15).getId(), s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        scoreBox.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                int score = Integer.parseInt(scoreBox.getText().toString());
                int highScore = Integer.parseInt(highScoreBox.getText().toString());
                if (score > highScore) {
                    highScoreBox.setText(score + "");
                    if (score > 100 && !beatScore) {
                        Toast.makeText(MainActivity.this, "New HighScore", Toast.LENGTH_SHORT).show();
                        beatScore = true;
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void setColor(int boxId, String s) {
        TextView box = (TextView) findViewById(boxId);

        switch (s) {
            case "":
                box.setBackground(shapeList.get(0));
                break;
            case "2":
                box.setBackground(shapeList.get(1));
                break;
            case "4":
                box.setBackground(shapeList.get(2));
                break;
            case "8":
                box.setBackground(shapeList.get(3));
                break;
            case "16":
                box.setBackground(shapeList.get(4));
                break;
            case "32":
                box.setBackground(shapeList.get(5));
                break;
            case "64":
                box.setBackground(shapeList.get(6));
                break;
            case "128":
                box.setBackground(shapeList.get(7));
                break;
            case "256":
                box.setBackground(shapeList.get(8));
                break;
            case "512":
                box.setBackground(shapeList.get(9));
                break;
            case "1024":
                box.setBackground(shapeList.get(10));
                break;
            case "2048":
                box.setBackground(shapeList.get(11));
                break;
            case "4096":
                box.setBackground(shapeList.get(12));
                break;
            case "8192":
                box.setBackground(shapeList.get(13));
                break;
            case "16384":
                box.setBackground(shapeList.get(14));
                break;
            case "32768":
                box.setBackground(shapeList.get(15));
                break;
            case "65536":
                box.setBackground(shapeList.get(16));
                break;
        }
    }

    private void generateRandomBlock() {
        ArrayList<TextView> openBlocks = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            String s = blocks.get(i).getText() + "";
            if (s.equals("")) openBlocks.add(blocks.get(i));
        }
        if (openBlocks.size() != 0) {
            int seed = randomGenerator.nextInt(10);
            int NumberToSpawn;
            if (seed <= 8) {
                NumberToSpawn = 2;
            } else {
                NumberToSpawn = 4;
            }
            int BlockToSpawn = randomGenerator.nextInt(openBlocks.size());
            openBlocks.get(BlockToSpawn).setText("" + NumberToSpawn);
        }
    }

    private void addScore(int num) {
        int total = Integer.parseInt(scoreBox.getText().toString());
        total += num;
        scoreBox.setText(total + "");
    }

    private void swipe(char c) {
        int validMove = 0;
        if (c == 'T') {
            for (int i = 0; i <= 3; i++) {
                validMove += moveBlocks(i, i + 4, i + 8, i + 12, true);
            }
        }
        if (c == 'B') {
            for (int i = 12; i <= 15; i++) {
                validMove += moveBlocks(i, i - 4, i - 8, i - 12, true);
            }
        }
        if (c == 'L') {
            for (int i = 0; i <= 12; i = i + 4) {
                validMove += moveBlocks(i, i + 1, i + 2, i + 3, true);
            }
        }
        if (c == 'R') {
            for (int i = 3; i <= 15; i = i + 4) {
                validMove += moveBlocks(i, i - 1, i - 2, i - 3, true);
            }
        }

        // Valid move (Something can changed)
        if (validMove>0) {
            oldBlocks.clear();
            for (int i = 0; i < 16; i++) {
                oldBlocks.add(blocks.get(i).getText().toString());
            }
            oldBlocks.add(scoreBox.getText().toString());
            oldBlocks.add(highScoreBox.getText().toString());

            // Moves Blocks
            if (c == 'T') {
                for (int i = 0; i <= 3; i++) {
                    moveBlocks(i, i + 4, i + 8, i + 12, false);
                }
            }
            if (c == 'B') {
                for (int i = 12; i <= 15; i++) {
                    moveBlocks(i, i - 4, i - 8, i - 12, false);
                }
            }
            if (c == 'L') {
                for (int i = 0; i <= 12; i = i + 4) {
                    moveBlocks(i, i + 1, i + 2, i + 3, false);
                }
            }
            if (c == 'R') {
                for (int i = 3; i <= 15; i = i + 4) {
                    moveBlocks(i, i - 1, i - 2, i - 3, false);
                }
            }

            // Move was made (Something changed)
            moveCount--;

            // Check if the amount of valid moves was reached before the next undo is available.
            if (moveCount == 0) {
                hasBack = true;
            }
            generateRandomBlock();
        }
        // Invalid move (Nothing can changed)
        if (validMove==0){
            // Check if anything else can move.
            int generate = 0;
            for (int i = 0; i <= 3; i++) {
                generate += moveBlocks(i, i + 4, i + 8, i + 12, true);
            }
            for (int i = 12; i <= 15; i++) {
                generate += moveBlocks(i, i - 4, i - 8, i - 12, true);
            }
            for (int i = 0; i <= 12; i = i + 4) {
                generate += moveBlocks(i, i + 1, i + 2, i + 3, true);
            }
            for (int i = 3; i <= 15; i = i + 4) {
                generate += moveBlocks(i, i - 1, i - 2, i - 3, true);
            }
            // Nothing else can move.
            if (generate == 0) {
                hasBack = false;
                Toast toast = Toast.makeText(this, "Game OVER", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }

        invalidateOptionsMenu();
    }

    private int moveBlocks(int i, int ii, int iii, int iv, boolean test) {
        String b1 = blocks.get(i).getText().toString();
        String b2 = blocks.get(ii).getText().toString();
        String b3 = blocks.get(iii).getText().toString();
        String b4 = blocks.get(iv).getText().toString();

        boolean mergeI = false;
        boolean merge4 = false;
        boolean out = false;

        int scoreToBeAdded = 0;

        // Step One
        if (!b2.equals("")) {
            if (b1.equals("")) {
                b1 = b2;
                b2 = "";
            } else if (b1.equals(b2)) {
                b1 = (Integer.parseInt(b1) * 2) + "";
                scoreToBeAdded += Integer.parseInt(b1);
                b2 = "";
                mergeI = true;
            }
        }
        // Step Two
        if (!b3.equals("")) {
            if (b2.equals("")) {
                b2 = b3;
                b3 = "";
            } else if (b2.equals(b3)) {
                b2 = (Integer.parseInt(b2) * 2) + "";
                scoreToBeAdded += Integer.parseInt(b2);
                b3 = "";
                merge4 = true;
            }
            // Do step one again if step one was not a merge
            if (!mergeI && !b2.equals("")) {
                if (b1.equals("")) {
                    b1 = b2;
                    b2 = "";
                } else if (b1.equals(b2) && !merge4) {
                    b1 = (Integer.parseInt(b1) * 2) + "";
                    scoreToBeAdded += Integer.parseInt(b1);
                    b2 = "";
                    mergeI = true;
                }
            }
        }
        // Step Three
        if (!b4.equals("")) {
            if (b3.equals("")) {
                b3 = b4;
                b4 = "";
            } else if (b3.equals(b4)) {
                b3 = (Integer.parseInt(b3) * 2) + "";
                scoreToBeAdded += Integer.parseInt(b3);
                b4 = "";
                // SET MERGE FLAG
                // THIS BLOCK SHOULD NOW ONLY BE MOVE NOT MERGED
                // THE REST OF THIS STEP SHOULD BE SKIPPED
                out = true;
            }
            // Do step two again if step two was not a merge
            if (!merge4 && !b3.equals("")) {
                if (b2.equals("")) {
                    b2 = b3;
                    b3 = "";
                } else if (b2.equals(b3) && !out) {
                    b2 = (Integer.parseInt(b2) * 2) + "";
                    scoreToBeAdded += Integer.parseInt(b2);
                    b3 = "";
                    out = true;
                }
            }
            // Do Step one again if one was not a merge
            if (!mergeI && !b2.equals("")) {
                if (b1.equals("")) {
                    b1 = b2;
                    b2 = "";
                } else if (b1.equals(b2) && !out && !merge4) {
                    b1 = (Integer.parseInt(b1) * 2) + "";
                    scoreToBeAdded += Integer.parseInt(b1);
                    b2 = "";
                }
            }
        }

        // Check if any of the blocks changed.
        if (
                b1.equals(blocks.get(i).getText().toString()) &&
                        b2.equals(blocks.get(ii).getText().toString()) &&
                        b3.equals(blocks.get(iii).getText().toString()) &&
                        b4.equals(blocks.get(iv).getText().toString())
                ) {
            // Return 0 if nothing was done
            return 0;
        } else {
            if (test) {
                return 1;
            } else {
                addScore(scoreToBeAdded);
                blocks.get(i).setText(b1);
                blocks.get(ii).setText(b2);
                blocks.get(iii).setText(b3);
                blocks.get(iv).setText(b4);
                return 1;
            }
        }
    }

    private void newGame() {
        beatScore = false;
        moveCount = 1;
        hasBack = false;
        scoreBox.setText("0");

        for (int i = 0; i < 16; i++) {
            blocks.get(i).setText("");
        }

        generateRandomBlock();

        Toast toast = Toast.makeText(this, "Game Reset", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
