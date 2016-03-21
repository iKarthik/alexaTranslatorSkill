import org.junit.Test;
import translator.textTranslator.GoogleTranslater;
import translator.speechAssets.LanguageToCode;

public class GoogleTranslaterTest {
    @Test
    public void testSimpleTranslation() throws Exception {
        String langCode = LanguageToCode.getGoogleLanguageCode("French");
        String translated = GoogleTranslater.translteTo(langCode, "i love music");
        System.out.println(translated);
    }
}