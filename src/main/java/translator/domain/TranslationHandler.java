package translator.domain;

import translator.speechAssets.LanguageToCode;
import translator.storage.S3Helper;
import translator.textToSpeech.TextToSpeechConverterIvona;
import translator.textToSpeech.TextToSpeechConverterVoiceRSS;
import translator.textTranslator.GoogleTranslater;

/**
 * Created by Karthik on 3/20/16.
 */
public class TranslationHandler {
    public static TranslatedText handleTranslation(String phraseToBeTranslated, String toLanguage) {
        String googleLangCode = LanguageToCode.getGoogleLanguageCode(toLanguage);
        System.out.println("Google lang code " + googleLangCode);
        String translated = translate(googleLangCode, phraseToBeTranslated);
        System.out.println("Translated text " + translated);
        byte[] audioByteArray = convertToSpeechUsingIvona(translated, toLanguage);
        String s3Location = saveToS3(audioByteArray, toLanguage);
        return createTT(toLanguage,phraseToBeTranslated, translated, s3Location);
    }

    private static TranslatedText createTT(String language, String sourceText, String translatedText, String s3Url){
        System.out.println("Creating TT");
        System.out.println("Translated Text " + translatedText);
        System.out.println("S3Location " + s3Url);
        TranslatedText text = new TranslatedText(language, sourceText);
        text.setTranslatedText(translatedText);
        text.setS3Location(s3Url);
        return text;
    }


    private static String translate(String googleLangCode, String phrase){
        try {
            return GoogleTranslater.translteTo(googleLangCode, phrase);
        } catch (Exception e) {
            return "error in translation "+e.getLocalizedMessage();
        }
    }

    private static byte[] convertToSpeechUsingVoiceRss(String translatedText, String language){
        String voiceRssLangCode = LanguageToCode.getVoicRssLanguageCode(language);
        try {
            return TextToSpeechConverterVoiceRSS.toSpeech(voiceRssLangCode, translatedText);
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] convertToSpeechUsingIvona(String translatedText, String language){
        String voiceRssLangCode = LanguageToCode.getIvonaLangCode(language);
        try {
            return TextToSpeechConverterIvona.toSpeech(voiceRssLangCode, translatedText);
        } catch (Exception e) {
            return null;
        }
    }


    private static String saveToS3(byte[] audioByteArray, String lang){
        System.out.println("Translated byte array length " + audioByteArray.length);
        String fileName = String.valueOf(System.currentTimeMillis())+".mp3";
        System.out.println("File name in S3 "+fileName);
        try {
            S3Helper.saveToS3(audioByteArray, "translateaudiofiles", fileName);
            return "https://s3.amazonaws.com/translateaudiofiles/"+fileName;
        } catch (Exception e) {
            System.err.println("Error is saving bytearray to S3 "+e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

}
