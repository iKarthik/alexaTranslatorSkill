package translator.textToSpeech;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Karthik on 3/20/16.
 */
public class TextToSpeechConverterGoogleAPI {

    public static byte[] toSpeech(String phraseInLanguage, String phrase) throws Exception {
        InputStream in = null;
        ByteArrayOutputStream outputStream = null;
        byte[] byteArray = null;
        try {
            URL link = new URL("https://translate.google.com/translate_tts?ie=UTF-8&q=hello&tl=en&tk=995126.5923230&client=t");
            URLConnection connection = link.openConnection();
            connection.setRequestProperty("Referer", "http://translate.google.com/");
            connection.setRequestProperty("User-Agent", "stagefright/1.2 (Linux;Android 5.0)");
            InputStream stream = connection.getInputStream();
            in = new BufferedInputStream(stream);
            outputStream = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                outputStream.write(buf, 0, n);

            }
            byteArray = outputStream.toByteArray();
        }catch (Exception ex){
            throw ex;
        }finally {
            if(in != null) in.close();;
            if(outputStream != null) outputStream.close();
        }
        return byteArray;
    }

    private static String accountForSpaces(String phrase){
       return phrase.replace(" ", "+");
    }
}
