package com.example.stt_nlp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.snu.ids.kkma.ma.MExpression;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;

import java.util.Arrays;
import java.util.List;

//view binding

public class Kkma extends AppCompatActivity {
    //결과들을 담아서 보여줄 result array
    static String[] result;

    String TAG = "Kkma";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kkma);


        TextView result1 = (TextView) findViewById(R.id.Kkma_result1_tv);
        TextView result2 = (TextView) findViewById(R.id.Kkma_result2_tv);
        Button backToMain = (Button) findViewById(R.id.Kkma_back_to_main_btn);
        Button translation = (Button) findViewById(R.id.Kkma_translation_btn);

        Intent intent = getIntent();
        String analyzeString = intent.getStringExtra("analyizeM");
        maTest(analyzeString, result1);
        extractTest(analyzeString, result2);


        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backIntent);
            }
        });

        translation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("result_check : ", Arrays.toString(result));
//                Intent translationIntent = new Intent(getApplicationContext(), TranslationActivity.class);
//                translationIntent.putExtra("resultArr", result);
//                startActivity(translationIntent);
            }
        });


    }
    public static void maTest(String string, TextView result1) {
        try{
            MorphemeAnalyzer ma = new MorphemeAnalyzer();
            ma.createLogger(null);
            List<MExpression> ret = ma.analyze(string);
            ret = ma.postProcess(ret);
            ret = ma.leaveJustBest(ret);
            List<Sentence>stl = ma.divideToSentences(ret);
            StringBuilder text = new StringBuilder();
            for(int i = 0;i < stl.size();i++){
                Sentence st = stl.get(i);
                Log.d("st에 뭐들어가는지 확인", String.valueOf(st.getSentence()));
                System.out.println("======================================================"+st.getSentence());
                //결과배열에 하나씩 값을 넣어준다 -> 동영상 return을 위해서
                result = st.getSentence().split(" ");
                Log.d("result에 제대로 값들어갔나 확인", result[1]);
                for(int j = 0; j < st.size(); j++){
                    System.out.println(st.get(j));
                    Log.d("st.get(j)에 뭐 들어가는지 확인", String.valueOf(st.get(j)));
                    text.append(String.valueOf(st.get(j)));
                    text.append("\n");
                }
                result1.setText(text);

            }
            ma.closeLogger();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void extractTest(String string, TextView result2){

        KeywordExtractor ke = new KeywordExtractor();
        KeywordList kl = ke.extractKeyword(string,true);
        StringBuilder text = new StringBuilder();
        for(int i = 0; i < kl.size();i++){
            Keyword kwrd = kl.get(i);
            System.out.println(kwrd.getString()+"\t"+kwrd.getCnt());
            text.append(kwrd.getString());
            text.append("\t");
            text.append(kwrd.getCnt());
            text.append("\n");
        }
        result2.setText(text);
    }

}