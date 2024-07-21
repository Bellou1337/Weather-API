import com.google.gson.JsonObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {

    private JsonObject jsonObject = null;
    private SetUrl urlChange = new SetUrl();
    private Connection connect = new Connection();

    public void SetJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public GUI() {
        super("Weather App");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(450, 650);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setIconImage(new ImageIcon("src/image/weather logo.png").getImage());
        addGuiComponent();
    }

    private void addGuiComponent() {

        //textField
        JTextField searchTextField = new JTextField("Taganrog");
        searchTextField.setBounds(15, 15, 351, 45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);

        // search button
        JButton searchButton = new JButton(LoadImage("src/image/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setFocusable(false);
        searchButton.setContentAreaFilled(false);
        add(searchButton);

        //city name
        JLabel cityName = new JLabel("Taganrog");
        cityName.setFont(new Font("Dialog", Font.BOLD, 32));
        cityName.setBounds(45, 75, 351, 45);
        cityName.setHorizontalAlignment(SwingConstants.CENTER);
        add(cityName);


        // weather icon
        JLabel weatherImage = new JLabel(LoadImage("src/image/cloudy.png"));
        weatherImage.setBounds(0, 125, 450, 217);
        add(weatherImage);

        // temperature text
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        //weather description
        JLabel weatherDesc = new JLabel("Cloudy");
        weatherDesc.setBounds(0, 405, 450, 36);
        weatherDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherDesc);

        //humidity image
        JLabel humidityImage = new JLabel(LoadImage("src/image/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

        //humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100 %</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        //windspeed image
        JLabel windspeedImage = new JLabel(LoadImage("src/image/windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);

        //windspeed text
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15 m/s</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);

        //default view
        DefaultData(searchTextField, searchButton, cityName, temperatureText, weatherDesc, humidityText, windspeedText, weatherImage);

        //Click
        Click(searchTextField, searchButton, cityName, temperatureText, weatherDesc, humidityText, windspeedText, weatherImage);
    }

    private void DefaultData(JTextField searchTextField, JButton searchButton, JLabel cityNameLabel, JLabel temperatureText, JLabel weatherDesc, JLabel humidityText, JLabel windspeedText, JLabel weatherImage) {
        //default user view
        String getUserInfo = searchTextField.getText();
        urlChange.SetURLString(getUserInfo);
        connect.Connect(urlChange.GetURL());
        if (connect.GetJsonObject() == null) {
            GetErrorData(cityNameLabel, temperatureText, weatherDesc, humidityText, windspeedText, weatherImage);
        } else {
            SetJsonObject(connect.GetJsonObject());
            GetData(cityNameLabel, temperatureText, weatherDesc, humidityText, windspeedText, weatherImage);
        }
    }

    private void GetErrorData(JLabel cityNameLabel, JLabel temperatureText, JLabel weatherDesc, JLabel humidityText, JLabel windspeedText, JLabel weatherImage) {
        cityNameLabel.setText("city not found");
        temperatureText.setText("not found");
        weatherDesc.setText("not found");
        humidityText.setText("<html><b>Humidity</b> <br>not found</html>");
        windspeedText.setText("<html><b>Windspeed</b> <br>not found</html>");
        weatherImage.setIcon(null);
    }

    private void Click(JTextField searchTextField, JButton searchButton, JLabel cityNameLabel, JLabel temperatureText, JLabel weatherDesc, JLabel humidityText, JLabel windspeedText, JLabel weatherImage) {
        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultData(searchTextField, searchButton, cityNameLabel, temperatureText, weatherDesc, humidityText, windspeedText, weatherImage);
            }
        });
    }

    private void GetData(JLabel cityNameLabel, JLabel temperatureText, JLabel weatherDesc, JLabel humidityText, JLabel windspeedText, JLabel weatherImage) {

        String city = GetCity();
        String typeWeather = GetWeatherDesc();
        int temp = GetTemp();
        double windSpeed = GetWindSpeed();
        int humidity = GetHumidity();

        cityNameLabel.setText(city);
        weatherDesc.setText(typeWeather);
        temperatureText.setText(String.valueOf(temp) + ' ' + "С");
        windspeedText.setText(String.format("<html><b>Windspeed</b> %s m/s</html>", String.valueOf(windSpeed)));
        humidityText.setText(String.format("<html><b>Humidity</b> %s %%</html>", String.valueOf(humidity)));

        switch (typeWeather) {
            case "clear sky":
                weatherImage.setIcon(LoadImage("src/image/clear.png"));
                break;
            case "overcast clouds":
                weatherImage.setIcon(LoadImage("src/image/cloudy.png"));
                break;
            case "scattered clouds":
                weatherImage.setIcon(LoadImage("src/image/cloudy.png"));
                break;
            case "broken clouds":
                weatherImage.setIcon(LoadImage("src/image/cloudy.png"));
                break;
            case "rainy clouds":
                weatherImage.setIcon(LoadImage("src/image/rain.png"));
                break;
            case "light rain":
                weatherImage.setIcon(LoadImage("src/image/rain.png"));
                break;
            case "snowy clouds":
                weatherImage.setIcon(LoadImage("src/image/snow.png"));
                break;
            default:
                weatherImage.setIcon(LoadImage("src/image/clear.png"));
                break;
        }

        //System.out.println("Город: " + city);
        //System.out.println("Температура: " + temp + ' ' + "градусов");
        //System.out.println("Скорость ветра: " + windSpeed + ' ' + "м/c");
        //System.out.println("Влажность: " + humidity);
        //System.out.println("Тип погоды: " + typeWeather);

    }

    private ImageIcon LoadImage(String resourcePath) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Image not found");
        return null;
    }

    private String GetCity() {

        return (String) jsonObject.get("name").getAsString();
    }

    private int GetHumidity() {
        return (int) jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
    }

    private int GetTemp() {
        return (int) Math.round((double) jsonObject.getAsJsonObject("main").get("temp").getAsDouble() - 273.15);
    }

    private double GetWindSpeed() {
        return (double) jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
    }

    private String GetWeatherDesc() {
        return (String) jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
