package simplejson;

import com.commonfunction.ReadFileFIS;
import org.json.JSONObject;
import java.io.IOException;

public class SimpleJsonEx1 {


    public static void main(String[] args) throws IOException {
        String jsonStr = ReadFileFIS.readFile("src/test/resources/payload/book.json");
        JSONObject jsonObject = new JSONObject(jsonStr) ;
        JSONObject tempJsonObject =   jsonObject.getJSONObject("store").getJSONArray("book").getJSONObject(0);
        tempJsonObject.put("category","This is testing category");
        System.out.println(jsonObject.toString());
    }
}

