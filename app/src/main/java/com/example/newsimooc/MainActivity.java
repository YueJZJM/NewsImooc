package com.example.newsimooc;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.lv_main);
        new NewAsyncTask().execute(URL);
      //  Log.d("url", URL);
    }

    /**
     * 将url对应的JSON格式数据转化为我们所封装的NewsBean
     * @param url
     * @return
     */
    private List<NewsBean> getJsonData(String url) {

        List<NewsBean> newsBeanList = new ArrayList<>();
        InputStream is = null;
        try {
            is = new URL(url).openStream();  //通过url得到返回值
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = readStream(is);
    //    Log.d("xys", jsonString);
        JSONObject jsonObject;
        NewsBean newsBean;
        try {
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                newsBean = new NewsBean();
                newsBean.newsIconUrl = jsonObject.getString("picSmall");
                newsBean.newsTltle = jsonObject.getString("name");
                newsBean.newsContent = jsonObject.getString("description");
                newsBeanList.add(newsBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }

    /**
     * 通过is解析网页返回的数组
     * @param is
     * @return
     */
    private String readStream(InputStream is){
        InputStreamReader isr;
        String result = "";

        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null){
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 实现网络的异步访问
     */
    class NewAsyncTask extends AsyncTask<String,Void,List<NewsBean>> {


        @Override
        protected List<NewsBean> doInBackground(String... strings) {

            return getJsonData(strings[0]);   //传入请求网址
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeans) {
            super.onPostExecute(newsBeans);
            NewsAdapter adapter = new NewsAdapter(MainActivity.this, newsBeans);
            mListView.setAdapter(adapter);
        }
    }
}
