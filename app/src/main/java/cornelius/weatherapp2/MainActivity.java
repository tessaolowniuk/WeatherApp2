package cornelius.weatherapp2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity implements Downloader.DownloadListener
{
    WeatherInfo weather;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get latitude and longitude from zip code
        String zipcode = "60563";
        Downloader<JSONObject> downloadInfo = new Downloader<>(this);
        downloadInfo.execute("http://craiginsdev.com/zipcodes/findzip.php?zip=" + zipcode);

        //Get weather data from latitude and longitude

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

    @Override
    public Object parseResponse(InputStream in)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            JSONObject jsonObject = new JSONObject(reader.readLine());
            return jsonObject;
        }
        catch(IOException ex)
        {
            Log.e("Error in parseResponse", ex.getMessage());
        }
        catch(JSONException ex)
        {
            Log.i("Error in parseResponse", ex.getMessage());
        }

        return null;
    }

    @Override
    public void handleResult(Object result)
    {
        try
        {
            JSONObject jsonObject = (JSONObject) result;

            weather.location.latitude = jsonObject.getDouble("latitude");
            weather.weatherInfo.location.longitude = jsonObject.getDouble("longitude");
        }
        catch(JSONException ex)
        {
            Log.i("Error in handleResult", ex.getMessage());
        }

    }
}
