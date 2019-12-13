package com.my.simpleprofileui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CardView card1, one, two, three;
    ImageView Profilepic;
    TextView name, address, rides, freerides, credits, location1, date1, location2, date2, location3, date3, location4, date4, location5, date5, location6, date6, rate1, rate2, rate3, currencysymbol1, currencysymbol2, currencysymbol3, traveltime1, traveltime2, traveltime3;
    String JsonURL = "https://gist.githubusercontent.com/iranjith4/522d5b466560e91b8ebab54743f2d0fc/raw/7b108e4aaac287c6c3fdf93c3343dd1c62d24faf/radius-mobile-intern.json";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);

        //Profile
        Profilepic = findViewById(R.id.profilepic);
        name = findViewById(R.id.textView1);
        address = findViewById(R.id.textView2);

        //Stats
        rides = findViewById(R.id.rides);
        freerides = findViewById(R.id.freerides);
        credits = findViewById(R.id.credits);

        //Cards
        card1 = findViewById(R.id.card1);
        one = findViewById(R.id.one);

        //Trip Details

        location1 = findViewById(R.id.location1);
        date1 = findViewById(R.id.date1);
        location2 = findViewById(R.id.location2);
        date2 = findViewById(R.id.date2);
        location3 = findViewById(R.id.location3);
        date3 = findViewById(R.id.date3);
        location4 = findViewById(R.id.location4);
        date4 = findViewById(R.id.date4);
        location5 = findViewById(R.id.location5);
        date5 = findViewById(R.id.date5);
        location6 = findViewById(R.id.location6);
        date6 = findViewById(R.id.date6);
        rate1 = findViewById(R.id.rate1);
        rate2 = findViewById(R.id.rate2);
        rate3 = findViewById(R.id.rate3);

        currencysymbol1 = findViewById(R.id.currencysymbol1);
        currencysymbol2 = findViewById(R.id.currencysymbol2);
        currencysymbol3 = findViewById(R.id.currencysymbol3);
        traveltime1 = findViewById(R.id.traveltime1);
        traveltime2 = findViewById(R.id.traveltime2);
        traveltime3 = findViewById(R.id.traveltime3);



        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("data").getJSONObject("profile");
                            JSONObject obj1 = response.getJSONObject("data").getJSONObject("stats");
                            JSONObject obj2 = response.getJSONObject("data").getJSONObject("stats").getJSONObject("credits");
                            JSONObject obj3 = response.getJSONObject("data");

                            //Fetching JSON
                            String first = obj.getString("first_name");
                            String second = obj.getString("last_name");
                            String city = obj.getString("city");
                            String country = obj.getString("Country");
                            String noofrides = obj1.getString("rides");
                            String nooffreerides = obj1.getString("free_rides");
                            String currencysymbol = obj2.getString("currency_symbol");
                            String value = obj2.getString("value");
                            String profilepiclink = obj.getString("middle_name");

                            Glide.with(getApplicationContext()).load(profilepiclink).into(Profilepic);


                            //JSON Text to string
                            String customername = first +" "+ second;
                            String customeraddr = city +", "+ country;
                            String ridescount = noofrides;
                            String freeridescount = nooffreerides;
                            String creditsvalue = currencysymbol +" "+value;

                            //String to UI
                            name.setText(customername);
                            address.setText(customeraddr);
                            rides.setText(ridescount);
                            freerides.setText(freeridescount);
                            credits.setText(creditsvalue);


                            JSONArray trip_data = obj3.getJSONArray("trips");
                            int length = trip_data.length();
                            for(int i=0;i<length;i++)
                            {

                                if(i==0) {
                                    JSONObject jsonobj = trip_data.getJSONObject(0);
                                    String from = jsonobj.getString("from");
                                    String to = jsonobj.getString("to");
                                    String fromtimes = jsonobj.getString("from_time");
                                    String totimesz = jsonobj.getString("to_time");
                                    String totalduration = jsonobj.getString("trip_duration_in_mins");
                                    String costing = jsonobj.getJSONObject("cost").getString("value");
                                    String symbol = jsonobj.getJSONObject("cost").getString("currency_symbol");

                                    location1.setText(from);
                                    location2.setText(to);

                                    long toconvert = Long.parseLong(totimesz);
                                    long fromconvert = Long.parseLong(fromtimes);
                                    String s = getDate(fromconvert, "MMM/dd, hh:mm");
                                    String ss = getDate(toconvert, "MMM/dd, hh:mm");

                                    date1.setText(s);
                                    date2.setText(ss);

                                    long totalconvert = Long.parseLong(totalduration);
                                    String timeone = getDate(fromconvert, "h");
                                    String timetwo = getDate(fromconvert, "MM");
                                    String finaltime = timeone + "h " + timetwo + "min";
                                    traveltime1.setText(finaltime);
                                    rate1.setText(costing);
                                    currencysymbol1.setText(symbol);
                            }

                                if(i==1) {
                                    JSONObject jsonobj = trip_data.getJSONObject(1);

                                    String from = jsonobj.getString("from");
                                    String to = jsonobj.getString("to");
                                    String fromtimes = jsonobj.getString("from_time");
                                    String totimesz = jsonobj.getString("to_time");
                                    String totalduration = jsonobj.getString("trip_duration_in_mins");
                                    String costing = jsonobj.getJSONObject("cost").getString("value");
                                    String symbol = jsonobj.getJSONObject("cost").getString("currency_symbol");

                                    location3.setText(from);
                                    location4.setText(to);
                                    long toconvert = Long.parseLong(totimesz);
                                    long fromconvert = Long.parseLong(fromtimes);
                                    String s = getDate(fromconvert, "MMM/dd, hh:mm");
                                    String ss = getDate(toconvert, "MMM/dd, hh:mm");

                                    date3.setText(s);
                                    date4.setText(ss);
                                    long totalconvert = Long.parseLong(totalduration);
                                    String timeone = getDate(fromconvert, "h");
                                    String timetwo = getDate(fromconvert, "MM");
                                    String finaltime = timeone + "h " + timetwo + "min";
                                    traveltime2.setText(finaltime);
                                    rate2.setText(costing);
                                    currencysymbol2.setText(symbol);
                                }

                                if(i==2) {
                                    JSONObject jsonobj = trip_data.getJSONObject(2);
                                    String from = jsonobj.getString("from");
                                    String to = jsonobj.getString("to");
                                    String fromtimes = jsonobj.getString("from_time");
                                    String totimesz = jsonobj.getString("to_time");
                                    String totalduration = jsonobj.getString("trip_duration_in_mins");
                                    String costing = jsonobj.getJSONObject("cost").getString("value");
                                    String symbol = jsonobj.getJSONObject("cost").getString("currency_symbol");

                                    location5.setText(from);
                                    location6.setText(to);
                                    long toconvert = Long.parseLong(totimesz);
                                    long fromconvert = Long.parseLong(fromtimes);
                                    String s = getDate(fromconvert, "MMM/dd, hh:mm");
                                    String ss = getDate(toconvert, "MMM/dd, hh:mm");

                                    date5.setText(s);
                                    date6.setText(ss);
                                    long totalconvert = Long.parseLong(totalduration);
                                    String timeone = getDate(fromconvert, "h");
                                    String timetwo = getDate(fromconvert, "MM");
                                    String finaltime = timeone + "h " + timetwo + "min";
                                    traveltime3.setText(finaltime);
                                    rate3.setText(costing);
                                    currencysymbol3.setText(symbol);
                                }
                            }


                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
