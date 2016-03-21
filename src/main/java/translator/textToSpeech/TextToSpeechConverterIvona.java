package translator.textToSpeech;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.ivona.services.tts.IvonaSpeechCloudClient;
import com.ivona.services.tts.model.CreateSpeechRequest;
import com.ivona.services.tts.model.CreateSpeechResult;
import com.ivona.services.tts.model.Input;
import com.ivona.services.tts.model.Voice;

import java.io.*;

/**
 * Created by Karthik on 3/20/16.
 */
public class TextToSpeechConverterIvona {
    private static IvonaSpeechCloudClient speechCloud;


    static {
        AWSCredentials creds = new BasicAWSCredentials("YOUR_ACCESS_KEY", "YOUR_SECRET_KEY");
        speechCloud = new IvonaSpeechCloudClient(creds);
        speechCloud.setEndpoint("https://tts.eu-west-1.ivonacloud.com");

    }

    public static byte[] toSpeech(String phraseInLanguage, String phrase) throws Exception {
        System.out.println("Using Ivona to translate text to speech");
        System.out.println("Ivona lang code "+phraseInLanguage);
        CreateSpeechRequest createSpeechRequest = new CreateSpeechRequest();
        Input input = new Input();
        Voice voice = new Voice();
        voice.setLanguage(phraseInLanguage);
        input.setData(accountForSpaces(phrase));
        createSpeechRequest.setInput(input);
        createSpeechRequest.setVoice(voice);
        InputStream in = null;
        ByteArrayOutputStream outputStream = null;

        try {

            CreateSpeechResult createSpeechResult = speechCloud.createSpeech(createSpeechRequest);
            in = createSpeechResult.getBody();
            outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[2 * 1024];
            int readBytes;

            while ((readBytes = in.read(buffer)) > 0) {
                // In the example we are only printing the bytes counter,
                // In real-life scenario we would operate on the buffer
                System.out.println(" received bytes: " + readBytes);
                outputStream.write(buffer, 0, readBytes);
            }
            byte[] outputByteArray = outputStream.toByteArray();
            System.out.println("Translated byte array "+outputByteArray);
            return outputByteArray;
        } catch (Exception ex) {
            System.err.println("Error translating text to speech by ivona " +ex.getLocalizedMessage());
            return null;
        } finally {
            if (in != null) in.close();
            if (outputStream != null) outputStream.close();
        }
    }

    private static String accountForSpaces(String phrase) {
        return phrase.replace(" ", "+");
    }
}
