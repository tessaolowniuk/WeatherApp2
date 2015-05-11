package cornelius.weatherapp2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity
{
    private String m_Text = "";
    Map weather = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        // Get latitude and longitude from zip code
        String zipcode = "60563";
        new LocationIO().getLocation(zipcode);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Displays about message
    private void about() {
        new AlertDialog.Builder(this)
                .setTitle("About")
                .setMessage("This is a weather app. Created by Cory and Tessa." +
                        "Data from www.weather.gov")
                .setNeutralButton("Ok", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void metric()
    {
        final TextView temp = (TextView)findViewById(R.id.temperature);
        final TextView dew = (TextView)findViewById(R.id.dew);
        final TextView pressure = (TextView)findViewById(R.id.pressure);
        final TextView gust = (TextView)findViewById(R.id.gusts);
        final TextView wind = (TextView)findViewById(R.id.windspeed);
        final TextView visibility = (TextView)findViewById(R.id.textView12);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        temp.setText(nf.format((Double.parseDouble(weather.get("temp").toString())-32)*5/9) + "º C");
        dew.setText(nf.format((Double.parseDouble(weather.get("dew").toString())-32)*5/9) + "º C");
        pressure.setText(nf.format(Double.parseDouble(weather.get("pressure").toString())*25.4)  + " mmHg");
        gust.setText(nf.format(Double.parseDouble(weather.get("gust").toString())*1.6093)  + " kph");
        wind.setText(weather.get("direction").toString() + " @ " + nf.format(Double.parseDouble(weather.get("wind").toString())*1.6093)  + " kph");
        visibility.setText(nf.format(Double.parseDouble(weather.get("visibility").toString())*1.6093)  + " km");
    }

    private void imperial()
    {
        final TextView temp = (TextView)findViewById(R.id.temperature);
        final TextView dew = (TextView)findViewById(R.id.dew);
        final TextView pressure = (TextView)findViewById(R.id.pressure);
        final TextView gust = (TextView)findViewById(R.id.gusts);
        final TextView wind = (TextView)findViewById(R.id.windspeed);
        final TextView visibility = (TextView)findViewById(R.id.textView12);

        temp.setText(weather.get("temp").toString() + "º F");
        dew.setText(weather.get("dew").toString() + "º F");
        pressure.setText(weather.get("pressure").toString()  + " inHg");
        gust.setText(weather.get("gust").toString()  + " mph");
        wind.setText(weather.get("direction").toString() + " @ " + weather.get("wind").toString()  + " mph");
        visibility.setText(weather.get("visibility").toString()  + " mi");
    }

    private void zipcode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("zipcode")
        .setMessage("Enter Zipcode of Desired Location");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.show();
    }

    private void recent_zipcodes() {

    }

    private void current_weather() {

    }

    private void forecast_weather() {

    }

    //Allows user to select what units they would like to be displayed
    private void units(){
        new AlertDialog.Builder(this)
                .setTitle("Units")
                .setMessage("Choose what units you would like your weather displayed in:")
                .setPositiveButton("Metric", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {
                        metric();
                    }
                })
                .setNegativeButton("Imperial", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which) {
                        imperial();
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {

            case R.id.zipcode:
                zipcode();
                return true;

            case R.id.recent_zipcode:
                recent_zipcodes();
                return true;

            case R.id.current_weather:
                current_weather();
                return true;

            case R.id.forecast:
                forecast_weather();
                return true;

            case R.id.units:
                units();
                return true;

            case R.id.about:
                about();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}