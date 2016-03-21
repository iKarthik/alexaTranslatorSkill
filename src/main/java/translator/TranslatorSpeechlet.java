/**
 Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

 Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

 http://aws.amazon.com/apache2.0/

 or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package translator;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.SsmlOutputSpeech;

import translator.domain.TranslatedText;
import translator.domain.TranslationHandler;
import translator.storage.S3Helper;

import java.io.IOException;
import java.util.Map;

public class TranslatorSpeechlet implements Speechlet {
    public static final String LANGUAGE_SLOT = "Language";
    public static final String PHRASE_SLOT = "Phrase";
    private static final String SESSION_LANGUAGE = "SessionLanguage";
    private static final String DEFAULT_TRANSLATION_LANG = "Russian";
    private static final String LAST_CARD = "LastCard";

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("NewLanguageIntent".equals(intentName)) {
            System.out.println("NewLanguageIntent is called");
            return languageChanged(intent, session);
        }  else if("RepeatIntent".equals(intentName)){
            System.out.println("RepeatIntent is called");
            return repeatIntent(session);
        }else {
            System.out.println("TranslateIntent is called");
            return translateThis(intent, session);
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
    }


    private SpeechletResponse getWelcomeResponse() {
        String speechText = "You can ask me to provide translations for different languages";
        return getSpeechletResponse(speechText);
    }

    private SpeechletResponse languageChanged(final Intent intent, final Session session) {
        Map<String, Slot> slots = intent.getSlots();
        String languageChosen = slots.get(LANGUAGE_SLOT).getValue();
        System.out.println("Language has been changed to " + languageChosen);
        String speechText = "Language changed to "+languageChosen;
        session.setAttribute(SESSION_LANGUAGE, languageChosen);
        saveChosenLangToS3(languageChosen.toLowerCase());
        return getSpeechletResponse(speechText);
    }

    private void saveChosenLangToS3(String langChosen){
        try {
            S3Helper.saveLangToS3(langChosen.toLowerCase());
        } catch (Exception e) {
            System.err.println("Could not save chosen lang "+langChosen+ " to S3");
            System.err.println(e.getLocalizedMessage());
        }
    }

    private SpeechletResponse translateThis(final Intent intent, final Session session) {
        Map<String, Slot> slots = intent.getSlots();
        String languageChosen = getSessionLanguage(session);
        String phraseToBeTranslated = slots.get(PHRASE_SLOT).getValue();
        System.out.println("Request to translate phrase " + phraseToBeTranslated + " in language " + languageChosen);
        TranslatedText tt = TranslationHandler.handleTranslation(phraseToBeTranslated, languageChosen);
        System.out.println("Obtained TT");
        updateSessionWithoutput(tt, session);
        return getSpeechletResponse(tt);
    }

    private SpeechletResponse repeatIntent(final Session session){
       TranslatedText tt = (TranslatedText) session.getAttribute(LAST_CARD);
        System.out.println("TT obtained from session " + tt);
        if(tt == null) return prepareNothingToRepeatResponse();
        return getSpeechletResponse(tt);
    }

    private SpeechletResponse prepareNothingToRepeatResponse(){
            SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
            outputSpeech.setSsml("<speak>" + "there is nothing to repeat" + "</speak>");
            SpeechletResponse response =  SpeechletResponse.newTellResponse(outputSpeech);
            response.setShouldEndSession(false);
            return response;
    }
    private void updateSessionWithoutput(TranslatedText tt, Session session){
        session.setAttribute(LAST_CARD, tt);
        System.out.println("Updated Session with tt " + tt);
    }

    private String getSessionLanguage(final Session session){
        if(session.getAttribute(SESSION_LANGUAGE) == null) return DEFAULT_TRANSLATION_LANG;
        else return getLastUsedLangFromS3(session);
    }

    private String getLastUsedLangFromS3(final Session session){
        try {
            return S3Helper.getLangFromS3();
        } catch (IOException e) {
            System.err.println("Could not fetch chosen lang from S3");
            System.err.println(e.getLocalizedMessage());
            System.err.println("Using default lang");
            return session.getAttribute(SESSION_LANGUAGE).toString();
        }
    }


    /**
     * Returns a Speechlet response for a speech and reprompt text.
     */
    private SpeechletResponse getSpeechletResponse(String speechText) {
        SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
        outputSpeech.setSsml("<speak>" + speechText + "</speak>");
        SpeechletResponse response =  SpeechletResponse.newTellResponse(outputSpeech);
        response.setShouldEndSession(false);
        return response;
    }

  /**
     * Returns a Speechlet response for a speech and reprompt text.
     */
    private SpeechletResponse getSpeechletResponse(TranslatedText tt) {
        System.out.println("Preparing Speechlet response from TT");
        SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
        outputSpeech.setSsml("<speak>" +"<audio src=\""+tt.getS3Location()+"\"/>" + "</speak>");
        Card card = createCard(tt);
        SpeechletResponse response =  SpeechletResponse.newTellResponse(outputSpeech);
        response.setCard(card);
        response.setShouldEndSession(false);
        System.out.println("Ready to return to caller");
        return response;
    }

    private Card createCard(TranslatedText tt) {
        SimpleCard card = new SimpleCard();
        card.setTitle("Translation Result");
        StringBuilder builder = new StringBuilder();
        builder.append("Source text ").append(tt.getSourceText()).append("\n");
        builder.append("Translated Text ").append(tt.getTranslatedText()).append("\n");
        builder.append("Translation Language ").append(tt.getLanguage());
        card.setContent(builder.toString());
        return card;
    }


}
