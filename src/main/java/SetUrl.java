import java.net.MalformedURLException;
import java.net.URL;

public class SetUrl {
    private String URL_STRING_TEMPLATE = "http://api.openweathermap.org/data/2.5/weather?q=%s&limit=5&appid=e5517ad9c128b2ddd8eb7bee2f81fd18";
    private String URL_STRING;

    public void SetURLString(String city) {
        this.URL_STRING = String.format(URL_STRING_TEMPLATE, city);
        //System.out.println("URL: " + URL_STRING);
    }

    public URL GetURL() {
        try {
            URL url = new URL(URL_STRING);
            return url;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
