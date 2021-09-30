package com.example.automatedhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.automatedhouse.Tasks.Greetings;

public class VoiceRecognition extends AppCompatActivity {

    private SpeechRecognizer recognizer;
    private TextToSpeech textToSpeech;
    private TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognition);
        display = (TextView) findViewById(R.id.display);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        initializeResult();
                        initializeTextToSpeech();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        System.exit(0);
                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();



    }
    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (textToSpeech.getEngines().size()==0) {
                    Toast.makeText(getApplicationContext(),"Engine Not Found",Toast.LENGTH_SHORT).show();
                }
                else {
                    String greetings = Greetings();
                    speak(greetings+" i am allen how may i help you ?");
                }
            }
        });
    }
    private void initializeResult() {
        if(SpeechRecognizer.isRecognitionAvailable(this)) {
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    display.setText(result.get(0));
                    response(result.get(0));


                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }
    private void speak(String msg) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(msg,TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else{
            textToSpeech.speak(msg,TextToSpeech.QUEUE_FLUSH,null);
        }

    }
    private void response(String msg) {
        String msgs = msg.toLowerCase();
        Automate automate = new Automate();

        if (msgs.indexOf("hi")!= -1) {
            speak("Hello ! How are you ?");
        }

        if(msgs.indexOf("hello")!= -1){
            speak("Hello ! How are you ?");
        }

        if(msgs.indexOf("fine")!= -1 ){
            speak("good to know how may i assist you");
        }else if(msgs.indexOf("iam") != -1){
            if(msgs.indexOf("not") !=-1)
            {
                if(msgs.indexOf("fine") != -1)
                {
                    speak("take some rest");
                }

            }

        }

        if(msgs.indexOf("turn") != -1)
        {
            if(msgs.indexOf("on") != -1)
            {
                if(msgs.indexOf("bedroom") != -1)
                {
                    if(msgs.indexOf("light") != -1)
                    {
                        speak("turning on bedroom lights");
                        automate.Automated(1,"LED1");
                    }
                    else if(msgs.indexOf("fan") != -1)
                    {
                        speak("turning on bedroom fan");
                        automate.Automated(1,"FAN1");
                    }
                }
                else if(msgs.indexOf("study") != -1)
                {
                    if(msgs.indexOf("room") != -1)
                    {
                        if(msgs.indexOf("light") != -1)
                        {
                            speak("turning on study room lights");
                            automate.Automated(1,"LED3");
                        }
                        else if(msgs.indexOf("fan") != -1)
                        {
                            speak("turning on study room fan");
                            automate.Automated(1,"FAN3");
                        }
                    }
                }
                else if(msgs.indexOf("dining") != -1)
                {
                    if(msgs.indexOf("hall") != -1)
                    {
                        if(msgs.indexOf("light") != -1)
                        {
                            speak("turning on dining hall lights");
                            automate.Automated(1,"LED4");
                        }
                        else if(msgs.indexOf("fan") != -1)
                        {
                            speak("turning on dining hall  fan");
                            automate.Automated(1,"FAN4");

                        }
                    }
                }
                else if(msgs.indexOf("living") != -1)
                {
                    if(msgs.indexOf("room") != -1)
                    {
                        if(msgs.indexOf("light") != -1)
                        {
                            speak("turning on living room lights");
                            automate.Automated(1,"LED2");
                        }
                        else if(msgs.indexOf("fan") != -1)
                        {
                            speak("turning on living room fan");
                            automate.Automated(1,"FAN2");
                        }
                    }
                }

            }
            else if(msgs.indexOf("off") != -1)
            {
                if(msgs.indexOf("bedroom") != -1)
                {
                    if(msgs.indexOf("light") != -1)
                    {
                        speak("turning off bedroom lights");
                        automate.Automated(0,"LED1");
                    }
                    else if(msgs.indexOf("fan") != -1)
                    {
                        speak("turning off bedroom fan");
                        automate.Automated(0,"FAN1");
                    }
                }
                else if(msgs.indexOf("study") != -1)
                {
                    if(msgs.indexOf("room") != -1)
                    {
                        if(msgs.indexOf("light") != -1)
                        {
                            speak("turning off study room lights");
                            automate.Automated(0,"LED3");
                        }
                        else if(msgs.indexOf("fan") != -1)
                        {
                            speak("turning off study room fan");
                            automate.Automated(0,"FAN3");
                        }
                    }
                }
                else if(msgs.indexOf("dining") != -1)
                {
                    if(msgs.indexOf("hall") != -1)
                    {
                        if(msgs.indexOf("light") != -1)
                        {
                            speak("turning off dining hall lights");
                            automate.Automated(0,"LED4");
                        }
                        else if(msgs.indexOf("fan") != -1)
                        {
                            speak("turning off dining hall fan");
                            automate.Automated(0,"FAN4");
                        }
                    }
                }
                else if(msgs.indexOf("living") != -1)
                {
                    if(msgs.indexOf("room") != -1)
                    {
                        if(msgs.indexOf("light") != -1)
                        {
                            speak("turning off living room light");
                            automate.Automated(0,"LED2");
                        }
                        else if (msgs.indexOf("fan") != -1)
                        {
                            speak("turning off living room fan");
                            automate.Automated(0,"FAN2");
                        }
                    }



                }


            }




        }

        if(msgs.indexOf("open") != -1)
        {
            if(msgs.indexOf("main") != -1)
            {
                if(msgs.indexOf("gate") != -1)
                {
                    speak("opening main gate");
                    automate.Automated(1,"Door");
                }
                else if(msgs.indexOf("entrance") != -1)
                {
                    speak("opening main entrance");
                    automate.Automated(1,"Door");

                }
            }
            else if(msgs.indexOf("front") != -1)
            {
                if(msgs.indexOf("gate") != -1)
                {
                    speak("opening front gate");
                    automate.Automated(1,"Door");

                }
            }

        }

        if(msgs.indexOf("close") != -1)
        {
            if(msgs.indexOf("main") != -1)
            {
                if(msgs.indexOf("gate") != -1)
                {
                    speak("closing main gate");
                    automate.Automated(0,"Door");
                }
                else if(msgs.indexOf("entrance") != -1)
                {
                    speak("closing main entrance");
                    automate.Automated(0,"Door");

                }
            }
            else if(msgs.indexOf("front") != -1)
            {
                if(msgs.indexOf("gate") != -1)
                {
                    speak("closing front gate");
                    automate.Automated(0,"Door");

                }
            }

        }


    }


    public void start_listening(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);

        recognizer.startListening(intent);
    }
}