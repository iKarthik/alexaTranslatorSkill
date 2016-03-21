package translator.textToSpeech;

import translator.creds.CredentialsManager;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Karthik on 3/20/16.
 */
public class TextToSpeechConverterVoiceRSS {

    public static byte[] toSpeech(String phraseInLanguage, String phrase) throws Exception {
        InputStream in = null;
        ByteArrayOutputStream outputStream = null;
        byte[] byteArray = null;
        try {
            URL link = new URL("https://api.voicerss.org/?key="+ CredentialsManager.getVoiceRssApiKey()+"&src="+
                    accountForSpaces(phrase)+"&hl="+phraseInLanguage+"&f=32khz_16bit_stereo");
            in = new BufferedInputStream(link.openStream());
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
