package translator.domain;

/**
 * Created by Karthik on 3/20/16.
 */
public class TranslatedText {

    private String language;
    private String translatedText;
    private String sourceText;
    private String s3Location;

    public TranslatedText(String language, String sourceText) {
        this.language = language;
        this.sourceText = sourceText;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getS3Location() {
        return s3Location;
    }

    public void setS3Location(String s3Location) {
        this.s3Location = s3Location;
    }
}
