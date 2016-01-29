package com.example.maurafitzgerald.calorieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.content.res.Resources;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    public EditText numInput;
    public TextView calories;
    public TextView pushups;
    public TextView situps;
    public TextView squats;
    public TextView leglifts;
    public TextView planks;
    public TextView jumpjacks;
    public TextView pullups;
    public TextView cycling;
    public TextView walking;
    public TextView jogging;
    public TextView swimming;
    public TextView stairclimb;
    public int[] reps = {0,1,2,6};
    public int exercisePos;
    public int amount = 0;
    public int[] amountFor100 = {350,200,225,25,25,10,100,12,20,12,13,15};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        numInput = (EditText)findViewById(R.id.numInput);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setLogo(R.drawable.calories);
        getSupportActionBar().setTitle("  "+ "burnnd");

        //Spinner
        Spinner spinner = (Spinner)findViewById(R.id.exercises);
        Resources res = getResources();
        String[] entries = res.getStringArray(R.array.exercises);
        ArrayAdapter spinnerAdapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, entries);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exercisePos = position;
                if (checkInArray(position, reps)) {
                    numInput.setHint("# of Reps");
                } else {
                    numInput.setHint("# of Minutes");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        calories = (TextView)findViewById(R.id.calcCalories);
        pushups = (TextView)findViewById(R.id.pushup);
        situps = (TextView)findViewById(R.id.situp);
        squats = (TextView)findViewById(R.id.squat);
        leglifts = (TextView)findViewById(R.id.leglifts);
        planks = (TextView)findViewById(R.id.plank);
        jumpjacks = (TextView)findViewById(R.id.jumpjacks);
        pullups = (TextView)findViewById(R.id.pullup);
        cycling = (TextView)findViewById(R.id.cycle);
        walking = (TextView)findViewById(R.id.walk);
        jogging = (TextView)findViewById(R.id.jog);
        swimming = (TextView)findViewById(R.id.swim);
        stairclimb = (TextView)findViewById(R.id.stairclimbs);

        Button calcButton = (Button)findViewById(R.id.calculate);
        calcButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (numInput.getText().toString().equals(null) || numInput.getText().toString().equals("")) {
                } else {
                    amount = Integer.parseInt(numInput.getText().toString());
                    displayCalories();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static boolean checkInArray(int currentState, int[] myArray) {
        boolean found = false;
        for (int i = 0; !found && (i < myArray.length); i++) {
            found = (myArray[i] == currentState);
        }
       return found;
    }

    public void displayCalories() {
        double calsBurned = calculateExercise();
        float roundedCals = (float)Math.round(calsBurned*100)/100;
        calories.setText(String.valueOf(roundedCals));
        pushups.setText(String.valueOf(calculateReps(0, calsBurned)));
        situps.setText(String.valueOf(calculateReps(1, calsBurned)));
        squats.setText(String.valueOf(calculateReps(2, calsBurned)));
        leglifts.setText(String.valueOf(calculateReps(3,calsBurned) + "m"));
        planks.setText(String.valueOf(calculateReps(4,calsBurned))+"m");
        jumpjacks.setText(String.valueOf(calculateReps(5,calsBurned))+"m");
        pullups.setText(String.valueOf(calculateReps(6,calsBurned)));
        cycling.setText(String.valueOf(calculateReps(7,calsBurned))+"m");
        walking.setText(String.valueOf(calculateReps(8,calsBurned))+"m");
        jogging.setText(String.valueOf(calculateReps(9,calsBurned))+"m");
        swimming.setText(String.valueOf(calculateReps(10,calsBurned))+"m");
        stairclimb.setText(String.valueOf(calculateReps(11,calsBurned))+"m");
    }
    public double calculateExercise(){
        return (amount * 100.00)/amountFor100[exercisePos];
    }
    public float calculateReps(int exerciseNum, double calories){
        double reps =  (calories * amountFor100[exerciseNum])/100.00;
        return (float)Math.round(reps*100)/100;
    }
}
