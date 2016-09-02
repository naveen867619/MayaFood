package com.mayaf.mayajaalfb;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mayaf.mayajaalfb.foodlist.FoodListActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    static TextView DateEdit, DateEdiat1;

    static TextView odate, otime, mobile, name1;

    Spinner sp, sp2;
    String arg[] = {"Select Your Screen", "Screen 1", "Screen 2", "Screen 3", "Screen 4", "Screen 5", "Screen 6", "Screen 7",
            "Screen 8", "Screen 9", "Screen 10", "Screen 11", "Screen 12", "Screen 13", "Screen 14",
            "Screen 15", "Screen 16"};

    String arg2[] = {"Cash or Swipe", "Cash", "Swipe"};

    private EditText name, mobno, showtime, seatno;

    private Toolbar topToolBar;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        topToolBar = (Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(topToolBar);
        //topToolBar.setLogo(R.drawable.top);
        //topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));
        topToolBar.inflateMenu(R.menu.menu_main);
        topToolBar.setTitleTextColor(Color.WHITE);

        mobile = (EditText) findViewById(R.id.mobno);
        name1 = (EditText) findViewById(R.id.name);
        odate = (TextView) findViewById(R.id.date);
        odate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonDatePickerDialog(v);
            }
        });
        otime = (TextView) findViewById(R.id.time);
        otime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v);

            }
        });


        name = (EditText) findViewById(R.id.name);
        mobno = (EditText) findViewById(R.id.mobno);
        showtime = (EditText) findViewById(R.id.showtime);
        seatno = (EditText) findViewById(R.id.seatno);
        sp = (Spinner) findViewById(R.id.screenno);
        sp2 = (Spinner) findViewById(R.id.cash);

        topToolBar = (Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(topToolBar);
        //topToolBar.setLogo(R.drawable.top);
        //topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));
        topToolBar.inflateMenu(R.menu.menu_main);
        topToolBar.setTitleTextColor(Color.WHITE);

        bt = (Button) findViewById(R.id.next);
        bt.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {

                                      if (name1.getText().toString().trim().length() < 3 || name1.getText().toString().trim().equals("")) {
                                          alert("Invalid", "Please enter the valid name");

                                      } else if (mobile.getText().toString().trim().length() < 10) {
                                          alert("Invalid", "Enter valid mobile no");
                                      } else if (sp.getSelectedItem().toString().trim().equals("Select Your Screen")) {
                                          alert("Invalid", "Select your Screen no");
                                      } else if (seatno.getText().toString().trim().length() == 0) {
                                          alert("Invalid", "Enter your seat no");
                                      } else if (showtime.getText().toString().trim().length() == 0) {
                                          alert("Invalid", "Enter your show time");
                                      } else if (odate.getText().toString().trim().length() == 0) {
                                          alert("Invalid", "Select your order date");
                                      } else if (otime.getText().toString().trim().length() == 0) {
                                          alert("Invalid", "Enter your order time");
                                      } else if (sp2.getSelectedItem().toString().trim().equals("Cash or Swipe")) {
                                          alert("Invalid", "Select your Payment mode");
                                      } else {

                                  /*    String Name = "Hari";
                                      String Mobile = "1234567890";
                                      String Showtime = "10:50pm";
                                      String Seatno = "s1";
                                      String Orderdate = "1/1/16";
                                      String Ordertime = "12:00am";
                                      String Screenno = "Screen 1";
                                      String Cash = "cash";
*/

                                          Intent in = new Intent(MainActivity.this, FoodListActivity.class);
                                          in.putExtra("name", name.getText().toString());
                                          in.putExtra("mobno", mobno.getText().toString());
                                          in.putExtra("showtime", showtime.getText().toString());
                                          in.putExtra("seatno", seatno.getText().toString());
                                          in.putExtra("odate", odate.getText().toString());
                                          in.putExtra("otime", otime.getText().toString());
                                          in.putExtra("sp", sp.getSelectedItem().toString());
                                          in.putExtra("sp2", sp2.getSelectedItem().toString());
                                          startActivity(in);

                                      }
                                  }
                              }

        );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arg);
        sp.setAdapter(adapter);
        // adapter.setDropDownViewResource(R.layout.spinner_layoutd);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                                     {
                                         @Override
                                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                         }

                                         @Override
                                         public void onNothingSelected(AdapterView<?> parent) {

                                         }
                                     }

        );

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arg2);
        sp2.setAdapter(adapter2);
        // adapter.setDropDownViewResource(R.layout.spinner_layoutd);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                                      {
                                          @Override
                                          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                          }

                                          @Override
                                          public void onNothingSelected(AdapterView<?> parent) {

                                          }
                                      }

        );
    }

    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            odate.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    public void showTruitonTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            otime.setText(otime.getText() + " " + hourOfDay + ":" + minute);
        }

    }

    public void alert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
