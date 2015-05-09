package cornelius.weatherapp2;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Cornelius on 5/9/2015.
 */
public class LocationIO implements Downloader.DownloadListener<JSONObject>
{
    JSONObject jsonObject;

    public JSONObject getLocation(String zipcode)
    {
        Downloader<JSONObject> downloadInfo = new Downloader<>(this);
        downloadInfo.execute("http://craiginsdev.com/zipcodes/findzip.php?zip=" + zipcode);
        return jsonObject;
    }

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

    @Override
    public void handleResult(JSONObject result)
    {
        jsonObject = result;
    }
}
