package translator.speechAssets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Karthik on 3/20/16.
 */
public class LanguageToCode {

    public static Map<String, String> googleLanguageCodeMap() {
        Map<String, String> map = new HashMap<>();
        map.put("afrikaans", "af");
        map.put("albanian", "sq");
        map.put("arabic", "ar");
        map.put("armenian", "hy");
        map.put("azerbaijani","az");
        map.put("basque", "eu");
        map.put("belarusian", "be");
        map.put("bengali", "bn");
        map.put("bosnian", "bs");
        map.put("bulgarian", "bg");
        map.put("catalan", "ca");
        map.put("cebuano", "ceb");
        map.put("chichewa", "ny");
        map.put("chinese", "zh-CN");
        map.put("croatian", "hr");
        map.put("czech", "cs");
        map.put("danish", "da");
        map.put("dutch", "nl");
        map.put("english", "en");
        map.put("british", "en");
        map.put("indian", "en");
        map.put("australian", "en");
        map.put("esperanto", "eo");
        map.put("estonian", "et");
        map.put("filipino", "tl");
        map.put("finnish", "fi");
        map.put("french", "fr");
        map.put("galician", "gl");
        map.put("georgian", "ka");
        map.put("german", "de");
        map.put("greek", "el");
        map.put("gujarati", "gu");
        map.put("haitian Creole", "ht");
        map.put("hausa", "ha");
        map.put("hebrew", "iw");
        map.put("hindi", "hi");
        map.put("hmong", "hmn");
        map.put("hungarian", "hu");
        map.put("icelandic", "is");
        map.put("igbo", "ig");
        map.put("indonesian", "id");
        map.put("irish", "ga");
        map.put("italian", "it");
        map.put("japanese", "ja");
        map.put("javanese", "jw");
        map.put("kannada", "kn");
        map.put("kazakh", "kk");
        map.put("khmer", "km");
        map.put("korean", "ko");
        map.put("lao", "lo");
        map.put("latin", "la");
        map.put("latvian", "lv");
        map.put("lithuanian", "lt");
        map.put("macedonian", "mk");
        map.put("malagasy", "mg");
        map.put("malay", "ms");
        map.put("malayalam", "ml");
        map.put("maltese", "mt");
        map.put("maori", "mi");
        map.put("marathi", "mr");
        map.put("mongolian", "mn");
        map.put("myanmar (Burmese)", "my");
        map.put("nepali", "ne");
        map.put("norwegian", "no");
        map.put("persian", "fa");
        map.put("polish", "pl");
        map.put("portuguese", "pt");
        map.put("punjabi", "ma");
        map.put("romanian", "ro");
        map.put("russian", "ru");
        map.put("serbian", "sr");
        map.put("sesotho", "st");
        map.put("sinhala", "si");
        map.put("slovak", "sk");
        map.put("slovenian", "sl");
        map.put("somali", "so");
        map.put("spanish", "es");
        map.put("sudanese", "su");
        map.put("swahili", "sw");
        map.put("swedish", "sv");
        map.put("tajik", "tg");
        map.put("tamil", "ta");
        map.put("telugu", "te");
        map.put("thai", "th");
        map.put("turkish", "tr");
        map.put("ukrainian", "uk");
        map.put("urdu", "ur");
        map.put("uzbek", "uz");
        map.put("vietnamese", "vi");
        map.put("welsh", "cy");
        map.put("yiddish", "yi");
        map.put("yoruba", "yo");
        map.put("zulu", "zu");
        return map;
    }

    public static Map<String, String> voiceRssLanguageCodeMap(){
        Map<String, String> map = new HashMap<>();
        map.put("Catalan","ca-es");
        map.put("Chinese","zh-cn");
        map.put("Danish","da-dk");
        map.put("Dutch","nl-nl");
        map.put("English","en-us");
        map.put("Finnish","fi-fi");
        map.put("French","fr-fr");
        map.put("German","de-de");
        map.put("Italian","it-it");
        map.put("Japanese","ja-jp");
        map.put("Korean","ko-kr");
        map.put("Norwegian","nb-no");
        map.put("Polish","pl-pl");
        map.put("Portuguese","pt-pt");
        map.put("Russian","ru-ru");
        map.put("Spanish","es-mx");
        map.put("Swedish","sv-se");
        return map;
    }

    private static Map<String, String> ivonaMap(){
        Map<String, String> map = new HashMap<>();
        map.put("english", "en-US");
        map.put("danish", "da-DK");
        map.put("german","de-DE");
        map.put("australian", "en-AU");
        map.put("british", "en-GB");
        map.put("welsh", "en-GB-WLS");
        map.put("indian", "en-IN");
        map.put("spanish", "es-ES");
        map.put("french", "fr-CA");
        map.put("icelandic", "is-IS");
        map.put("italian", "it-IT");
        map.put("norwegian","nb-NO");
        map.put("dutch", "nl-NL");
        map.put("polish", "pl-PL");
        map.put("portuguese", "pt-BR");
        map.put("romanian", "ro-RO");
        map.put("russian", "ru-RU");
        map.put("swedish", "sv-SE");
        map.put("turkish", "tr-TR");
        return map;
    }

    public static String getGoogleLanguageCode(String language){
        return googleLanguageCodeMap().get(language.toLowerCase());
    }

    public static String getVoicRssLanguageCode(String language){
        return voiceRssLanguageCodeMap().get(language);
    }

    public static String getIvonaLangCode(String language){
        return ivonaMap().get(language.toLowerCase());
    }

}
