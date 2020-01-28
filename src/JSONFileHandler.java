package src;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class JSONFileHandler {

    private JSONObject object;

    public JSONFileHandler(String fileName) {
        URL fileURL = this.getClass().getResource(fileName);
        String file = GetFile(fileURL);
        try {
            object = new JSONObject(file);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String GetFile(URL url) {
        Scanner scan = null;
        try {
            scan = new Scanner(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = new String();
        while (scan.hasNext())
            str += scan.nextLine();
        scan.close();
        return str;
    }

    public JSONObject getObject() {
        return object;
    }
}
