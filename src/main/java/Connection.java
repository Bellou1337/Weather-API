import com.google.gson.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {

    private JsonObject jsonObject = null;

    public void Connect(URL url) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            if (httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK) {

                inputStream = httpURLConnection.getInputStream();
                File file = new File("info.json");
                fileOutputStream = new FileOutputStream(file);
                StringBuilder search = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    search.append(line);
                }

                JsonElement jsonElement = JsonParser.parseString(search.toString());
                jsonObject = jsonElement.getAsJsonObject();


                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String prettyJson = gson.toJson(gson.fromJson(search.toString(), Object.class));

                fileOutputStream.write(prettyJson.getBytes());
            }
        } catch (IOException e) {
            System.out.println("Connection error : " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();

                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                System.out.println("File close error :" + e.getMessage());
            }

        }

    }
    public JsonObject GetJsonObject(){
        return this.jsonObject;
    }
}
