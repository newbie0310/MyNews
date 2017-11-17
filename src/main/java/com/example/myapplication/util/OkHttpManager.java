package com.example.myapplication.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by john1 on 2017/11/8.
 */

public class OkHttpManager {
    private static OkHttpClient client;
    private static OkHttpManager okHttpManager;
    private Handler mHandler;

    /**
     * 单例获取okhttpManager
     */
    private static OkHttpManager getInstance(){
        if (okHttpManager == null){
            okHttpManager = new OkHttpManager();
        }
        return okHttpManager;
    }

    public OkHttpManager() {
        client = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }
    /**
     * 内部使用的方法
     */
    private Response p_getSync(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute( );
        return response;
    }

    private String p_getSyncAsString(String url) throws IOException{
        Response response = getInstance().p_getSync(url);
        return response.body().string();
    }

    private void p_getAsync(String url, final DataCallBack dataCallBack){
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request,e,dataCallBack);
            }

            @Override
            public void onResponse(Call call, Response response){
                try {
                    String result = response.body().string();
                    deliverDataSuccess(result,dataCallBack);
                } catch (IOException e) {
                    deliverDataFailure(request,e,dataCallBack);
                }
            }
        });
    }

    private void p_postAsync (String url, Map<String,String>parms, final DataCallBack callBack){
        RequestBody requestBody = null;
        if (parms == null){
            parms = new HashMap<String, String>();
        }
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String,String> entry:parms.entrySet()) {
            String key = entry.getKey().toString();
            String value = null;
            if (entry.getValue() == null){
                value = "";
            }else {
                value = entry.getValue().toString();
            }
            Log.d("p_postAsync","提交的key是：" + key +"   提交的value是：" +  value + "*******************");
            formBody.add(key,value);
        }
        requestBody = formBody.build();
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request,e,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    deliverDataSuccess(result,callBack);
                } catch (IOException e) {
                    deliverDataFailure(request,e,callBack);
                }
            }
        });
    }

    private void p_downloadAsync(final String url, final String desDir, final DataCallBack callBack){
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request,e,callBack);
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream inputStream = null;
                FileOutputStream fos = null;
                try {
                    File file = new File(desDir,getFileName(url));
                    fos = new FileOutputStream(file);
                    inputStream = response.body().byteStream();
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1){
                        fos.write(buffer,0,len);
                    }
                    fos.flush();
                    deliverDataSuccess(file.getAbsolutePath(),callBack);
                }catch (IOException e){
                    deliverDataFailure(request,e,callBack);
                }finally {
                    try {
                        if (fos != null){
                            fos.close();
                        }
                        if (inputStream != null){
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        deliverDataFailure(request,e,callBack);
                    }
                }
            }
        });
    }

    //******************数据分发****************************
    private void deliverDataFailure(final Request request, final IOException e, final DataCallBack callBack){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null){
                    callBack.requestFailue(request,e);
                }
            }
        });
    }

    private void deliverDataSuccess(final String result, final DataCallBack callBack){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null){
                    callBack.requestSucess(result);
                }
            }
        });
    }
    //******************对外公布的方法***********************

    /**
     * 同步get请求
     */
    public static Response getSync(String url) throws IOException{
        return getInstance().p_getSync(url);
    }
    public static String getSyncAsString(String url) throws IOException{
        return getInstance().p_getSyncAsString(url);
    }

    /**
     * 异步get请求
     */
    public static void getAsync(String url, DataCallBack dataCallBack ){
        Log.d("getAsync","getAsync");
        getInstance().p_getAsync(url,dataCallBack);
    }

    /**
     * post表单提交
     */
    public static void postAsync(String url, Map<String,String>parms,DataCallBack callBack){
        getInstance().p_postAsync(url,parms,callBack);
    }

    /**
     * 文件下载
     */
    public static void downloadAsync(String url,String desDir,DataCallBack callBack){
        getInstance().p_downloadAsync(url,desDir,callBack);
    }

    //********************************数据回调接口*******
    public interface DataCallBack{
        void requestFailue(Request request, IOException e);
        void requestSucess(String result);
    }

    /**
     * 根据文件url地址获取文件的路径文件名
     */
    private String getFileName(String pUrl){
        int separatorIndex = pUrl.lastIndexOf("/");
        String path = (separatorIndex < 0) ? pUrl : pUrl.substring(separatorIndex + 1,pUrl.length());
        return path;
    }
}
