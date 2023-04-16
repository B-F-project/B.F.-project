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
import org.snu.ids.kkma.ma.Morpheme;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;

import java.util.ArrayList;
import java.util.List;

//view binding

public class Kkma extends AppCompatActivity {
    //결과들을 담아서 보여줄 result array
    static String[] result;
    static ArrayList<String> lemma = new ArrayList<String>();
    static ArrayList<String> tag = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kkma);


        TextView result1 = (TextView) findViewById(R.id.Kkma_result1_tv);
        TextView result2 = (TextView) findViewById(R.id.Kkma_result2_tv);
        Button backToMain = (Button) findViewById(R.id.Kkma_back_to_main_btn);
        Button translation = (Button) findViewById(R.id.Kkma_translation_btn);

        Intent intent = getIntent();
//        String analyzeString = intent.getStringExtra("analyizeM");
        //여기 아래에다가 원하는 예시 문장을 넣으면 됩니다.
        String analyzeString = "나는 오늘 학교에 갔다.";
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
                Intent translationIntent = new Intent(getApplicationContext(), TranslationActivity.class);
                translationIntent.putExtra("resultArr", result);
                startActivity(translationIntent);
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

                System.out.println("======================================================"+st.getSentence());
                //결과배열에 하나씩 값을 넣어준다 -> 동영상 return을 위해서
                result = st.getSentence().split(" ");
                Log.d("result에 제대로 값들어갔나 확인", result[1]);
                for(int j = 0; j < st.size(); j++){

                    //add lemma
                    //VV가 있는 동사에 대해서만 작동하는 lemmatization코드
                    //st.get(j)로 들어온 예시 문장
                    String test = String.valueOf(st.get(j));
                    //만약 VV가 있으면
                    if(test.contains("VV")){
                        //VV앞의 / /에 있는 덩어리 뽑아오기 위한 코드 - split하고
                        String[] splitSentence = test.split("/");
                        int index = -1;
                        //split된 덩어리들중 VV를 가지고있는 문장의 인덱스 뽑아서
                        for(int k = 0; k < splitSentence.length; k++){
                            if(splitSentence[k].contains("VV")){
                                index = k;
                                break;
                            }
                        }
                        //그 앞의 덩어리에 저장된 string(어간)에 +다 해서 lemma에 집어넣음.
                        //lemma에 VV에해당하는 거 저장
                        lemma.add(splitSentence[index-1]+"다");
                        Log.d("VV value: ", lemma.get(j));
                        //tag에 tag저장
                        tag.add(splitSentence[index]);
                    }
                    //서술격 조사 "이다" -> . 예외처리
//                    else if(test.contains("VCP")){
//                        //VCP앞의 / /에 있는 덩어리 뽑아오기 위한 코드 - split하고
//                        String[] splitSentence = test.split("/");
//                        int index = -1;
//                        //split된 덩어리들중 VCP를 가지고있는 덩어리의 인덱스 뽑아서
//                        for(int k = 0; k < splitSentence.length; k++){
//                            if(splitSentence[k].contains("VCP")){
//                                index = k;
//                                break;
//                            }
//                        }
//                        //그 앞의 덩어리에 저장된 string서술격 조사에 +다 해서 lemma에 집어넣음.
//                        lemma.add("+splitSentence[index-1]+"다");
//                        Log.d("VCP value: ", lemma.get(j));
//                    }
                    //VV없으면 그냥 pass한다.
                    else {
                        //유소연 파트.
                        //여기서 => 이전의 문장을 잡아옴.
                        int spaceIndex = test.indexOf("=>"); // 첫 번째 띄어쓰기의 인덱스를 찾음
                        String result_space = test.substring(0, spaceIndex - 1); // 첫 번째 띄어쓰기 이전의 부분 문자열 추출
                        lemma.add(result_space);
                        Log.d("passing value: ", lemma.get(j));
                    }

                    Log.d("st.get("+Integer.toString(j)+ ")에 뭐 들어가는지 확인", String.valueOf(st.get(j)));
                    text.append(String.valueOf(st.get(j)));
                    text.append("\n");
                }
                result1.setText(text);
                //최종 결과 확인
                for(int l = 0; l<lemma.size(); l++){
                    Log.d("lemma_last_result: "+Integer.toString(l), lemma.get(l));
                }
                for(int p = 0; p<tag.size(); p++){
                    Log.d("lemma_last_tag: "+Integer.toString(p), tag.get(p));
                }

            }
            ma.closeLogger();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//                    if (pos.startsWith("VV")) {
//                        // Lemmatize verb
//                        if (word.endsWith("하다")) {
//                            word = word.substring(0, word.length() - 2);
//                        } else if (word.endsWith("되다")) {
//                            word = word.substring(0, word.length() - 2);
//                        } else if (word.endsWith("르다")) {
//                            word = word.substring(0, word.length() - 2);
//                        } else if (word.endsWith("이다")) {
//                            word = "이다";
//                        } else {
//                            word = word.substring(0, word.length() - 1);
//                        }
//                    }


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