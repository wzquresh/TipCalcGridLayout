package com.example.waqas.myapplication;
//The IDE asks you to press alt-Enter a few times. This adds to the imports.

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;


public class MainActivity extends ActionBarActivity {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;   //Amount entered by user
    private double customPercent = 0.18;  //Initial custom tip percentage
    private TextView amountDisplayTextView;  //Formatted bill amount
    private TextView percentCustomTextView;  //Custom tip percentage
    private TextView tip15TextView;  //Shows 15% tip
    private TextView total15TextView;  //Total with 15% tip
    private TextView tipCustomTextView;  //Shows custom tip
    private TextView totalCustomTextView;  //Total with custom tip

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*When the system calls onCreate it passes a Bundle argument
        containing the Activity's saved state*/
        super.onCreate(savedInstanceState);

        /*setContentView: Inflating the GUI.
        * This method loads the xml document,
        * parses it, and converts it into the app's GUI*/
        setContentView(R.layout.activity_main);

        /* R is an automatically generated class.
         It contains nested classes which represent
         each type of resource in the res folder.
         It is accessed by using R.className, such as R.id*/

        //Get the references to the Text Views
        amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayText);
        percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
        tip15TextView = (TextView) findViewById(R.id.tip15TextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);

        //Set Text to change GUI values
        amountDisplayTextView.setText(currencyFormat.format(billAmount));
        updateStandard();  //Defined below for 15% tip
        updateCustom();  //Defined below for custom tip

        //Text Watcher for amountEditText
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        //Listener for SeekBar
        SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    }  //end of onCreate method

    private void updateStandard()
    {
        double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;

        tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
        total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
    }  //end method updateStandard

    private void updateCustom()
    {
        percentCustomTextView.setText(percentFormat.format(customPercent));

        double customTip = billAmount * customPercent;
        double customTotal = billAmount + customTip;

        tipCustomTextView.setText(currencyFormat.format(customTip));
        totalCustomTextView.setText(currencyFormat.format(customTotal));
    }  //end method updateCustom

    private SeekBar.OnSeekBarChangeListener customSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            customPercent = progress / 100.0;
            updateCustom();
        } //end method onProgressChanged

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        } //end method onStartTrackingTouch

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        } //end method onStopTrackingTouch
    };  //end onSeekBarChangeListener

    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        } //end method beforeTextChanged

        //Called when the user enters a number, and whenever text is changed
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //converts amountEditText's text to a double
            try
            {
                billAmount = Double.parseDouble(s.toString()) / 100.0;
            } //end try
            catch (NumberFormatException e){
                billAmount = 0.0;  //default if exception occurs
            } //end catch
            //Displays the currency formatted bill amount
            amountDisplayTextView.setText(currencyFormat.format(billAmount));
            updateStandard();
            updateCustom();
        } //end method onTextChanged

        @Override
        public void afterTextChanged(Editable s) {

        } //end method afterTextChanged
    }; //end amountEditTextWatcher

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
}
