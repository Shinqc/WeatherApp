import com.example.weatherexamples.WeatherFetch;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class TestFetchWeather {

    @Test
    void testFetchWeather() {
        try {
            double latitude = 40.7128;
            double longitude = -74.0060;
            JSONObject weatherData = WeatherFetch.fetchWeather(latitude, longitude);
            assertNotNull(weatherData, "Weather data should not be null");
            assertEquals(weatherData.getDouble("lat"), latitude, 0.1);
            assertEquals(weatherData.getDouble("lon"), longitude, 0.1);
            // Add more assertions here based on the structure of the returned JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testConversion() {
        try {
            String city = "Boston";
            String state = "MA";
            JSONObject weatherData = WeatherFetch.convertCityStateToLatLon(city, state);
            assertNotNull(weatherData, "Weather data should not be null");
            double expectedLat = 42.3601;
            double expectedLon = -71.0589;
            assertEquals(weatherData.getDouble("lat"), expectedLat, 0.1);
            assertEquals(weatherData.getDouble("lon"), expectedLon, 0.1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFetchWeatherByCity() {
        try {
            String city = "New York";
            String state = "NY";
            JSONObject weatherData = WeatherFetch.fetchWeatherByCity(city, state);
            assertNotNull(weatherData, "Weather data should not be null");
            // Add more assertions here based on the structure of the returned JSON
            assertTrue(weatherData.has("current"));
            assertTrue(weatherData.has("hourly"));
            assertTrue(weatherData.has("daily"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expectedExceptions = IOException.class)
    void testInvalidCityState() throws IOException {
        // Test with an invalid city and state combination
        String city = "InvalidCity";
        String state = "InvalidState";
        WeatherFetch.convertCityStateToLatLon(city, state);
    }

    @Test
    void testFetchWeatherByInvalidCity() {
        try {
            String city = "InvalidCity";
            String state = "InvalidState";
            JSONObject weatherData = WeatherFetch.fetchWeatherByCity(city, state);
            // Weather data should be null for an invalid city
            assertEquals(weatherData.length(), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFetchWeatherByCityWithEmptyState() {
        try {
            String city = "New York";
            String state = "";
            JSONObject weatherData = WeatherFetch.fetchWeatherByCity(city, state);
            // Weather data should still be fetched even if state is empty
            assertNotNull(weatherData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}