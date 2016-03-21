import org.junit.Test;
import translator.domain.TranslatedText;
import translator.domain.TranslationHandler;

/**
 * Created by Karthik on 3/20/16.
 */
public class TranslationHandlerTest {

    @Test
    public void testTranslationHandler() throws Exception {
        TranslatedText tt = TranslationHandler.handleTranslation("Food", "Danish");
        System.out.println(tt.getTranslatedText());
        System.out.println(tt.getS3Location());
    }
}
