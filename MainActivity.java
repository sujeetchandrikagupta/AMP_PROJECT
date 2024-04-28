package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView assistantResponseTextView;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assistantResponseTextView = findViewById(R.id.assistantResponseTextView);
        Button voiceInputButton = findViewById(R.id.voiceInputButton);
        textToSpeech = new TextToSpeech(this, this);
        voiceInputButton.setOnClickListener(v -> promptSpeechInput());
    }

    // Showing Google speech input dialog
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something...");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Speech recognition not supported on this device", Toast.LENGTH_SHORT).show();
        }
    }

    // Receiving speech input

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String spokenText = result.get(0);
                assistantResponseTextView.setText(spokenText);
                handleCommand(spokenText.toLowerCase(Locale.getDefault()));
            }
        }
    }

    // Handling commands based on spoken text
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void handleCommand(String spokenText) {
        // Simple command check
        if (spokenText.contains("hello")) {
            respond("Hello How can I help you.");
            speakOut("Hello How can I help you.");
        }
        else if (spokenText.contains("namaste")) {
            respond("namaste! mai aap ki kya sahayata kar sakta hu.");
            speakOut("namaste! mai aap ki kya sahayata kar sakta hu.");
        }
        else if(spokenText.contains("how are you")){
            respond("I am fine thank you. How can I help you.");
            speakOut("I am fine thank you. How can I help you.");
        }
        else if(spokenText.contains("tum kaise ho")){
            respond("mai achchha hu dhannyavad. mai aap ki kya sahayata kar sakta hu.");
            speakOut("mai achchha hu dhannyavad. mai aap ki kya sahayata kar sakta hu.");
        }
        else if(spokenText.contains("who are you")){
            respond("I'm an assistant . I'm here to help answer questions, provide information, and assist with a wide range of topics. How can I assist you today?.");
            speakOut("I'm an assistant . I'm here to help answer questions, provide information, and assist with a wide range of topics. How can I assist you today?");
        }
        else if(spokenText.contains("tum kaun ho")){
            respond("main ek sahaayak hoon. main yahaan prashnon ke uttar dene, jaanakaaree pradaan karane aur vibhinn vishayon par sahaayata karane ke lie hoon. aaj main aapakee kaise sahaayata kar sakata hoon?.");
            speakOut("main ek sahaayak hoon. main yahaan prashnon ke uttar dene, jaanakaaree pradaan karane aur vibhinn vishayon par sahaayata karane ke lie hoon. aaj main aapakee kaise sahaayata kar sakata hoon?.");
        }
        else if(spokenText.contains("what you do.")){
            respond("i listening after the clicking and perform specific task.");
            speakOut("i listening after the clicking and perform specific task.");
        }
        else if(spokenText.contains("famouse authors in indian.")){
            respond("Famouse india authors sume name is ,Arundhati roy ,khushwant singh, r k narayan.");
            speakOut("Famouse india authors sume name is ,Arundhati roy ,khushwant singh, r k narayan.");
        }
        else if(spokenText.contains("i am hungry.")){
            respond("you are hungry then go fast and eat food.");
            speakOut("you are hungry then go fast and eat food.");
        }
        else if (spokenText.contains("date")) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(" dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            String todays_date = df.format(cal.getTime());
            respond("The Today's date is "+ todays_date);
            speakOut("The Today's date is "+ todays_date);
        }
        else if (spokenText.contains("time")) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            String currentTime = sdf.format(calendar.getTime());
            respond("The time is "+ currentTime);
            speakOut("The time is "+ currentTime);
        }

        else if (spokenText.contains("call")) {
                if (spokenText.contains("amol")) {
                    respond("Calling Amol.");
                    speakOut("Ok,Calling Amol. ");
                    makePhoneCall("amol");
                } else if (spokenText.contains("rohit")) {
                    respond("Ok,Calling Rohit.");
                    speakOut("Ok,Calling Rohit. ");
                    makePhoneCall("rohit");
                } else if (spokenText.contains("nitin")) {
                    respond("Calling Nitin.");
                    speakOut("Ok,Calling Nitin.");
                    makePhoneCall("nitin");
                } else if (spokenText.contains("avinash")) {
                    respond("Calling Avinash.");
                    speakOut("Ok,Calling Avinash.");
                    makePhoneCall("avinash");
                } else if (spokenText.contains("krishna")) {
                    respond("Calling Krishna.");
                    speakOut("Ok,Calling Krishna.");
                    makePhoneCall("krishna");
                } else {
                    respond("Sorry, contact not found.");
                }
        }

        else if(spokenText.contains("open ")) {
            if(spokenText.contains("youtube")){
                respond("open YouTube");
                speakOut("ok ! open YouTube");
                openYouTube();
            }
            else if(spokenText.contains("facebook")){
                respond("open facebook");
                speakOut("ok ! open facebook");
                openFaceBook();
            }
            else if(spokenText.contains("instagram")){
                respond("open Instagram");
                speakOut("ok ! open Instagram");
                openInstagram();
            }
            else if(spokenText.contains("google")){
                respond("open google");
                speakOut("ok ! open google");
                openGoogle();
            }
            else if(spokenText.contains("map")){
                respond("open google map");
                speakOut("ok ! open google map");
                openGoogleMaps();
            }
            else if(spokenText.contains("telegram")){
                respond("open telegram");
                speakOut("ok ! open telegram");
                openTelegram();
            }
            else if(spokenText.contains("snapchat")){
                respond("open snapchat");
                speakOut("ok ! open snapchat");
                openSnapchat();
            }
            else if(spokenText.contains("guitar")){
                respond("open github");
                speakOut("ok ! open github");
                openGithub();
            }
            else if(spokenText.contains("whatsapp")){
                respond("open WhatsApp");
                speakOut("ok ! open WhatsApp");
                openWhatapp();
            }
            else{
                respond("sorry ! this app is not open.");
                speakOut("sorry ! this app is not open.");
            }
        }
        else{
            respond("I'm not sure what you said.");
            speakOut("I'm not sure what you said.");
        }
    }

    private void makePhoneCall(String contactName) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        switch (contactName) {
            case "amol":
                intent.setData(Uri.parse("9137853098"));
                break;
            case "rohit":
                intent.setData(Uri.parse("9326534142"));
                break;
            case "nitin":
                intent.setData(Uri.parse("9892252837"));
                break;
            case "avinash":
                intent.setData(Uri.parse("7977945246"));
                break;
            case "krishna":
                intent.setData(Uri.parse("9324096624"));
                break;
            default:
                // Handle contact not found
                respond("sorry, contact not found.");
                return;
        }
        try {
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
            // Handle permission denial
            respond("Permission denied to make a phone call.");
        }
    }


    // Method to open YouTube using an Intent
    private void openYouTube(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
        startActivity(intent);
    }
    // Method to open FaceBook using an Intent
    private void openFaceBook() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
        startActivity(intent);
    }
    //Method to open Telegram using an Intent
    private void openTelegram() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://t.me/telegram"));
        startActivity(intent);
    }
    // Method to open Instagram using an Intent
    private void openInstagram() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"));
        startActivity(intent);
    }
    // Method to open google using an Intent
    private void openGoogle(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        startActivity(intent);
    }
    private void openGoogleMaps() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=latitude,longitude(label)");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    private void openSnapchat() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.snapchat.com"));
        startActivity(intent);
    }
    private void openGithub() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com"));
        startActivity(intent);
    }
    private void openWhatapp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("whatsapp://send?phone=PHONE"));
        startActivity(intent);
    }

    // Method to display the assistant's response
    private void respond(String response) {
        assistantResponseTextView.setText(response);
        // You can perform additional actions based on the response if needed
    }
    // TextToSpeech initialization callback
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "TTS initialization failed", Toast.LENGTH_SHORT).show();
        }
    }
    // Method to speak out the given text
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speakOut(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }
    // Don't forget to release TextToSpeech resources
    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}