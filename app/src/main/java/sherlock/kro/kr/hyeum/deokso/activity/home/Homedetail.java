package sherlock.kro.kr.hyeum.deokso.activity.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import sherlock.kro.kr.hyeum.deokso.R;

public class Homedetail extends ActionBarActivity {



    private static String URL_PRIMARY = "http://deokso.hs.kr"; //홈페이지 원본 주소이다.
    private static String getnotice; //홈페이지 의 게시판을 나타내는 뒤 주소, 비슷한 게시판들은 거의 파싱이 가능하므로 응용하여 사용하자.
    private String url;
    private URL URL;

    private Source source;
    private ProgressDialog progressDialog;
    ;
    private int BBSlocate2;
    private int BBSlocate;
    private int a;
    private ConnectivityManager cManager;
    private NetworkInfo mobile;
    private NetworkInfo wifi;
    private TextView title;
    private TextView writer;
    private TextView date;
    private TextView abc;
    private TextView abc1;
    private TextView down;
    private static String BCS_down;
    private String BC_title;
    private String str;
    private String BCS_writer;
    private String BCS_date;
    private String BBS_body;
    private Element BCS_abc;

    @Override
    protected void onStop() { //멈추었을때 다이어로그를 제거해주는 메서드
        super.onStop();
        if ( progressDialog != null)
            progressDialog.dismiss(); //다이어로그가 켜져있을경우 (!null) 종료시켜준다
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Intent int2 =getIntent();
        getnotice=int2.getStringExtra("in1");
        Log.v("tlqkf",getnotice);
        getnotice=getnotice.replaceAll("amp;","");
        title = (TextView)findViewById(R.id.item_title1); //리스트선언
        writer = (TextView)findViewById(R.id.item_writer1); //리스트선언
        date = (TextView)findViewById(R.id.item_date1); //리스트선언
        abc = (TextView)findViewById(R.id.item_abc); //리스트선언\
        abc1 = (TextView)findViewById(R.id.item_abc1); //리스트선언
        down = (TextView)findViewById(R.id.item_down); //리스트선언



        url = URL_PRIMARY+getnotice; //파싱하기전 PRIMARY URL 과 공지사항 URL 을 합쳐 완전한 URL 을만든다.
        Log.v("ddwew",url);

        if(isInternetCon()) { //false 반환시 if 문안의 로직 실행
            Toast.makeText(Homedetail.this, "인터넷에 연결되지않아 불러오기를 중단합니다.", Toast.LENGTH_SHORT).show();
            finish();
        }else{ //인터넷 체크 통과시 실행할 로직
            try {
                process(); //네트워크 관련은 따로 쓰레드를 생성해야 UI 쓰레드와 겹치지 않는다. 그러므로 Thread 가 선언된 process 메서드를 호출한다.

            } catch (Exception e) {
                Log.d("ERROR", e + "");

            }
        }





    }

    public void onClick(View v){

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(BCS_down))); //적출해낸 url 을 이용해 URL_PRIMARY 와 붙이고

    }



    private void process() throws IOException {

        new Thread() {

            @Override
            public void run() {

                Handler Progress = new Handler(Looper.getMainLooper()); //네트워크 쓰레드와 별개로 따로 핸들러를 이용하여 쓰레드를 생성한다.
                Progress.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = ProgressDialog.show(Homedetail.this, "", "게시판 정보를 가져오는중 입니다.");
                    }
                }, 0);

                try {
                    URL = new URL(url);
                    InputStream html = URL.openStream();
                    source = new Source(new InputStreamReader(html, "euc-kr")); //소스를 UTF-8 인코딩으로 불러온다.
                    source.fullSequentialParse(); //순차적으로 구문분석
                } catch (Exception e) {
                }

                List<StartTag> tabletags = source.getAllStartTags(HTMLElementName.DIV); // DIV 타입의 모든 태그들을 불러온다.

                for(int arrnum = 0;arrnum < tabletags.size(); arrnum++) { //DIV 모든 태그중 bbsContent 태그가 몇번째임을 구한다.


                    if (tabletags.get(arrnum).toString().equals("<div class=\"boardRead\">")) {
                        BBSlocate = arrnum; //DIV 클래스가 bbsContent 면 arrnum 값을 BBSlocate 로 몇번째인지 저장한다.
                        break;

                    } else {
                    }
                }
                Element BBS_DIV = (Element) source.getAllElements(HTMLElementName.DIV).get(BBSlocate); //BBSlocate 번째 의 DIV 를 모두 가져온다.



                    // 소스의 효율성을 위해서는 for 문을 사용하는것이 좋지만 , 이해를 돕기위해 소스를 일부로 늘려 두었다.

                    try {
                        Element BC_Title = (Element) BBS_DIV.getAllElements(HTMLElementName.DD).get(0);
                        Element BC_Name = (Element) BBS_DIV.getAllElements(HTMLElementName.DD).get(1); //BC_info 안의 a 태그를 가져온다.
                        Element BC_Date = (Element) BBS_DIV.getAllElements(HTMLElementName.DD).get(2); //BC_info 안의 a 태그를 가져온다.
                        Element BBS_Body = (Element) source.getAllElements(HTMLElementName.DIV).get(42); //BC_info 안의 a 태그를 가져온다.
                        Element BCS_Downa = (Element) source.getAllElements(HTMLElementName.DIV).get(47); //BC_info 안의 a 태그를 가져온다.
                        Element BCS_Down = (Element) BCS_Downa.getAllElements(HTMLElementName.A).get(0); //BC_info 안의 a 태그를 가져온다.

                        BC_title = BC_Title.getContent().toString();
                         BCS_writer = BC_Name.getContent().toString();
                         BCS_date = BC_Date.getContent().toString();
                        BBS_body=BBS_Body.getContent().toString();
                        str = BBS_body.replaceAll("<[^>]*>", "");
                        BCS_down=BCS_Down.getAttributes().getValue("href");



                        Log.d("BCSARR", "제목:" + BC_title + "\n다운:" + BCS_down + "\n글쓴이:" + BCS_writer + "\n날짜:" + BCS_date + "\n내용:" + str + "\n");


                    }catch(Exception e){

                        Log.d("BCSERROR",e+"");
                    }

                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //모든 작업이 끝나면 리스트 갱신
                        title.setText(BC_title);
                        writer.setText(BCS_writer);
                        date.setText(BCS_date);
                        abc.setText(str);
                        down.setText(BCS_down);
                        progressDialog.dismiss(); //모든 작업이 끝나면 다이어로그 종료
                    }
                }, 0);



            }

        }.start();


    }




    private boolean isInternetCon() {
        cManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); //모바일 데이터 여부
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); //와이파이 여부
        return !mobile.isConnected() && !wifi.isConnected(); //결과값을 리턴
    }
}