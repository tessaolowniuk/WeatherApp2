package cornelius.weatherapp2;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Gets latitude and longitude for a given zip code and sends that information to WeatherInfoIO()
 * Created by Cornelius on 5/9/2015.
 */
public class LocationIO implements Downloader.DownloadListener<JSONObject>
{
    /**
     * @param zipcode URL amended with zipcode to Downloader to get JSONObject
     */
    public void getLocation(String zipcode)
    {
        Downloader<JSONObject> downloadInfo = new Downloader<>(this);
        downloadInfo.execute("http://craiginsdev.com/zipcodes/findzip.php?zip=" + zipcode);
    }

    /**
     * @param in InputStream from the web request
     * @return jsonObject JSONObject parsed from InputStream
     */
    @Override
    public JSONObject parseResponse(InputStream in)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            JSONObject jsonObject = new JSONObject(reader.readLine());
            return jsonObject;
        }
        catch(IOException ex)
        {
            Log.e("LocationIO", ex.getMessage());
        }
        catch(JSONException ex)
        {
            Log.i("LocationIO", ex.getMessage());
        }

        return null;
    }

    /**
     * Latitude and longitude are passed to WeatherInfoIO
     *
     * @param result Object of type <code>T</code> created in the override parseResponse function
     */
    @Override
    public void handleResult(JSONObject result)
    {
        WeatherInfoIO.WeatherListener weatherListener = new WeatherInfoIO.WeatherListener()
        {
            @Override
            public void handleResult(WeatherInfo result)
            {
                WeatherInfo weatherInfo = result;
            }
        };

        try
        {
            WeatherInfoIO.loadFromUrl("http://forecast.weather.gov/MapClick.php?lat=" +
                    result.getDouble("latitude") + "&lon=" + result.getDouble("longitude")
                    +"&unit=0&lg=english&FcstType=dwml", weatherListener);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
