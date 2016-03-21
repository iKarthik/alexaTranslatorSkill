package translator;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class TranslatorSpeechletRequestHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;

    static {
        supportedApplicationIds = new HashSet<String>();
        supportedApplicationIds.add("amzn1.echo-sdk-ams.app.55db24a6-4008-4309-8e90-613f0e1d875b");
    }

    public TranslatorSpeechletRequestHandler() {
        super(new TranslatorSpeechlet(), supportedApplicationIds);
    }
}
