
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.ivona.services.tts.IvonaSpeechCloudClient;
import com.ivona.services.tts.model.*;

/**
 * Class that generates sample synthesis and retrieves audio stream.
 */
public class SampleIvonaSpeechCloudCreateSpeech {

    private static IvonaSpeechCloudClient speechCloud;

    private static void init() {
        AWSCredentials creds = new BasicAWSCredentials("YOUR_ACCESS_KEY", "YOUR_SECRET_KEY");
        speechCloud = new IvonaSpeechCloudClient(creds);
        speechCloud.setEndpoint("https://tts.eu-west-1.ivonacloud.com");
    }

    public static void main(String[] args) throws Exception {
        init();
        tts();
    }

    private static void tts() throws Exception {
        String outputFileName = "speech.mp3";
        CreateSpeechRequest createSpeechRequest = new CreateSpeechRequest();
        Input input = new Input();
        Voice voice = new Voice();
//        voice.setName("Salli");
        voice.setLanguage("en-US");
        input.setData("This is a sample text to be synthesized.");

        createSpeechRequest.setInput(input);
        createSpeechRequest.setVoice(voice);
        InputStream in = null;
        FileOutputStream outputStream = null;

        try {

            CreateSpeechResult createSpeechResult = speechCloud.createSpeech(createSpeechRequest);

            System.out.println("\nSuccess sending request:");
            System.out.println(" content type:\t" + createSpeechResult.getContentType());
            System.out.println(" request id:\t" + createSpeechResult.getTtsRequestId());
            System.out.println(" request chars:\t" + createSpeechResult.getTtsRequestCharacters());
            System.out.println(" request units:\t" + createSpeechResult.getTtsRequestUnits());

            System.out.println("\nStarting to retrieve audio stream:");

            in = createSpeechResult.getBody();
            outputStream = new FileOutputStream(new File(outputFileName));

            byte[] buffer = new byte[2 * 1024];
            int readBytes;

            while ((readBytes = in.read(buffer)) > 0) {
                // In the example we are only printing the bytes counter,
                // In real-life scenario we would operate on the buffer
                System.out.println(" received bytes: " + readBytes);
                outputStream.write(buffer, 0, readBytes);
            }

            System.out.println("\nFile saved: " + outputFileName);

        } finally {
            if (in != null) {
                in.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private static void voices() throws Exception {
        ListVoicesRequest allVoicesRequest = new ListVoicesRequest();
        ListVoicesResult allVoicesResult = speechCloud.listVoices(allVoicesRequest);
        System.out.println("All voices: " + allVoicesResult);
        List<Voice> voices = allVoicesResult.getVoices();
        List<Voice> unique = new LinkedList<>();
        for (Voice voice : voices) {
            if(!contains(voice.getLanguage(), unique)) unique.add(voice);
        }

        for (Voice voice : unique) {
            System.out.println(voice.getName()+ " "+ voice.getLanguage());
        }
    }

    private static boolean contains(String lang, List<Voice> uniqueVoices){
        for (Voice uniqueVoice : uniqueVoices) {
            if(uniqueVoice.getLanguage().equals(lang)) return true;
        }
        return false;
    }
}


