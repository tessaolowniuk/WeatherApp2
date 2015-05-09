package cornelius.weatherapp2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity
{
    WeatherInfo weatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get latitude and longitude from zip code
        String zipcode = "60563";
        JSONObject location = new LocationIO().getLocation(zipcode);
        double latitude = 0;
        double longitude = 0;
        try
        {
            weatherInfo.location.latitude = location.getDouble("latitude");
            weatherInfo.location.longitude = location.getDouble("longitude");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        //Get weather data from latitude and longitude
        WeatherInfoIO.WeatherListener weatherListener = new WeatherInfoIO.WeatherListener()
        {
            @Override
            public void handleResult(WeatherInfo result)
            {
                weatherInfo = result;
            }
        };
        WeatherInfoIO.loadFromUrl("http://forecast.weather.gov/MapClick.php?lat=" + weatherInfo.location.latitude + "&lon=" + weatherInfo.location.longitude +"&unit=0&lg=english&FcstType=dwml", weatherListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
