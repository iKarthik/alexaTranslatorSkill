package translator.textTranslator;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;
import translator.creds.CredentialsManager;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Karthik on 3/20/16.
 */
public class GoogleTranslater {

    public static String translteTo(String toLanguage, String sourcePhrase) throws Exception {
        try {
                 Translate translate = buildTranslate();
            return translate(translate, toLanguage, sourcePhrase);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error in performing translation";
        }
    }

    private static String translate(Translate translate, String toLanguage, String phrase) throws IOException {
        Translate.Translations.List list = buildTranslateList(translate, toLanguage, phrase);
        TranslationsListResponse response = list.execute();
        Optional<TranslationsResource> optional = response.getTranslations().stream().findFirst();
        if(optional.isPresent()) return optional.get().getTranslatedText();
        else return "Could not find translation";
    }

    private static Translate buildTranslate() throws GeneralSecurityException, IOException {
        return new Translate.Builder(GoogleNetHttpTransport.newTrustedTransport()
                , GsonFactory.getDefaultInstance(), null)
                .setApplicationName("Server Key 1")
                .build();
    }

    private static Translate.Translations.List buildTranslateList(Translate translate, String toLanguage, String phrase) throws IOException {
        Translate.Translations.List list = translate.new Translations().list(
                Arrays.asList(phrase),toLanguage);
        list.setKey(CredentialsManager.getGoogleApiKey());
        return list;
    }
}
