package cn.com.oasis.base.net.ionnet.ion;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.FutureBuilder;
import com.koushikdutta.ion.loader.AsyncHttpRequestFactory;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import cn.com.oasis.base.net.ionnet.InterceptNet;
import cn.com.oasis.base.net.ionnet.InterceptResult;
import cn.com.oasis.base.net.ionnet.NetInterface;
import cn.com.oasis.base.net.ionnet.NetworkUtils;
import cn.com.oasis.base.net.ionnet.RequestParem;
import cn.com.oasis.base.net.ionnet.ResultCode;
import cn.com.oasis.base.net.ionnet.ResultMessage;
import cn.com.oasis.base.net.ionnet.convert.ConvertJson;
import cn.com.oasis.base.net.ionnet.convert.GsonConvertJson;
import cn.com.oasis.base.net.ionnet.convert.GsonListConvertJson;
import cn.com.oasis.base.net.ionnet.exception.DataException;
import cn.com.oasis.base.net.ionnet.exception.ErroCode;
import cn.com.oasis.base.net.ionnet.exception.NoDataException;
import cn.com.oasis.base.net.ionnet.exception.ServerException;
import cn.com.oasis.base.net.ionnet.exception.ServerTokenException;
import cn.com.oasis.base.net.ionnet.request.RequestListner;
import cn.com.oasis.base.utils.LogUtils;

/**
 * @author jackyhe
 * @time   2016/3/5 16:16
 */
public class IonNetInterface implements NetInterface {



    private static IonNetInterface ionNetInterface;
    private Handler mHandler;
    private IonNetInterface(){
        mHandler = new Handler();
    }

    public static IonNetInterface get(){
        if(ionNetInterface==null){
            synchronized (IonNetInterface.class){
                if(ionNetInterface==null){
                    ionNetInterface = new IonNetInterface();
                }
            }
        }

        return ionNetInterface;
    }


    private Context app;
    private InterceptNet interceptNet;
    @Override
    public void start(Context app) {
        if(this.app==null){
            this.app = app;

            //此处获取拦截接口的实现类，剥离json数据的封装
            interceptNet = new AirIntercept();
            //setupIon(app);
            ErroCode.init();
        }
    }

    private void setupIon(Context context) {
        final AsyncHttpRequestFactory current = Ion.getDefault(context).configure().getAsyncHttpRequestFactory();
        Ion.getDefault(context).configure().setAsyncHttpRequestFactory(new AsyncHttpRequestFactory() {
            public AsyncHttpRequest createAsyncHttpRequest(Uri uri, String method, Headers headers) {
                AsyncHttpRequest ret = current.createAsyncHttpRequest(uri, method, headers);
                ret.setTimeout(100);
                return ret;
            }
        });
    }

    @Override
    public void stop() {
        Ion.getDefault(app).cancelAll();
    }

    @Override
    public <T> void doRequest(RequestParem requestParem, RequestListner<T> requestListner) {
        doRequest(requestParem, requestListner, null);
    }

    @Override
    public <T> void doRequest(RequestParem requestParem, final RequestListner<T> requestListner, final ConvertJson<T> convertJson) {

        if (requestParem == null) return;

        if (requestParem.getInterceptNet() == null){
            requestParem.setInterceptNet(new AirIntercept());
        }

        requestListner.onStart();

        if(!NetworkUtils.isNetwork()){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ResultMessage e = ResultMessage.error(ResultCode.ERROR_NO_NET, "网络不用，请检查网路");
                    requestListner.onEnd(e);
                }
            }, 300);
            return;
        }


        //处理参数
        FutureBuilder builder = handlerParems(requestParem);

        //处理回调
        FutureCallback<String> callback = handlerCallBack(requestListner,convertJson,requestParem.isIntercept(),requestParem.getInterceptNet());


        builder.group(requestListner.getTag()).asString(Charset.forName("UTF-8")).setCallback(callback);

    }



    @Override
    public void cancel(Object tag) {
        Ion.getDefault(app).cancelAll(tag);
    }


    /**
     * 处理ion的请求参数
     * @param requestParem
     * @return
     */
    private FutureBuilder handlerParems(RequestParem requestParem){

        LogUtils.d(requestParem.toString());

        Map<String,String> headerParams = requestParem.getMapHeader();
        Map<String,Object> mapParameter = requestParem.getMapParems();
        Map<String,File> fileMap = requestParem.getFileMap();
        boolean jsonParem = false;//java post 参数
        boolean fileParem = false;//表单上传参数

        if(fileMap!=null&&fileMap.size()>0){
            headerParams.put("Content-Type","multipart/form-data");
            fileParem = true;
        }

        //setupIon(app);
        Builders.Any.B b = Ion.with(app).
                load(requestParem.getRequestMethod(), requestParem.getUrl());
        b.setTimeout(10000);

        if(headerParams!=null&&headerParams.size()>0){

            if(!fileParem){
                for(String key:headerParams.keySet()){
                    b = b.addHeader(key,headerParams.get(key));
                }
            }
            jsonParem = headerParams.containsKey("Content-Type")&&headerParams.get("Content-Type").contains("json");
        }
        if(mapParameter!=null&&mapParameter.size()>0){
            if(jsonParem){

                LogUtils.e("JAVAPAREM","url:"+requestParem.getUrl()+"\nheader:"+requestParem.getMapHeader()+"\nbody"+RequestParem.toJsonFormMap(mapParameter));
                return b.setStringBody(RequestParem.toJsonFormMap(mapParameter));

            } if(fileParem){
               return handlerMultipartParameter(b,fileMap,mapParameter);
            }else{
                return handlerBodyParameter(b,mapParameter);
            }
        }
        return b;
    }


    private Builders.Any.U handlerBodyParameter(Builders.Any.B b,Map<String,Object> mapParameter){
        Builders.Any.U u = null;
        for(String key : mapParameter.keySet()){
            if(null==u){
                u = b.setBodyParameter(key,String.valueOf(mapParameter.get(key)));
            }else{
                u = u.setBodyParameter(key,String.valueOf(mapParameter.get(key)));
            }
        }
        return u;
    }

    private Builders.Any.B handlerMultipartParameter(Builders.Any.B b,Map<String,File> fileMap,Map<String,Object> mapParameter){
        for(String key : mapParameter.keySet()){
            b.setMultipartParameter(key,String.valueOf(mapParameter.get(key)));
        }
        for(String key : fileMap.keySet()){
            File file = fileMap.get(key);
            if(file!=null){
                b.setMultipartFile(key,"application/jpg",fileMap.get(key));
            }
        }
        return b;
    }

    private <T> FutureCallback<String> handlerCallBack(final RequestListner<T> requestListner, final ConvertJson<T> convertJson, final boolean intercept,final InterceptNet cstInterceptNet){
        FutureCallback<String> callback = new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception exception, String response) {
                if(response!=null&&response.length()>0){
                    if(response.charAt(0) == 65279){//去掉Bom头
                        response = response.substring(1, response.length());
                    }
                }

                LogUtils.e("onCompleted() called with " + "class:"+requestListner.getClazz().getSimpleName()+"exception = [" + exception + "], response = [" + response + "]");
                ResultMessage resultMessage = null;
                if(null==exception){
                    if(response!=null&&response.length()>0){
                        if(response.charAt(0) == 65279){//去掉Bom头
                            response = response.substring(1, response.length());
                        }
                    }

                    //拦截到服务器返回的字符串，剥离包装的flag，message
                    InterceptNet tempInterceptNet = cstInterceptNet==null?interceptNet:cstInterceptNet;
                    if (tempInterceptNet != null&&intercept) {
                        // 需要intercept过滤
                        InterceptResult result = null;
                        try {
                            result = tempInterceptNet.handler(response);
                        } catch (DataException e) {
                            e.printStackTrace();
                            resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, e);
                        } catch (ServerTokenException e){
                            requestListner.onEnd(ResultMessage.errorToken(e.getErrorCode(), e.getMessage()));
                            return;
                        }catch (ServerException e) {
                            resultMessage = e.parse();
                            e.printStackTrace();
                        } catch (Exception e) {
                            resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, DataException.ERROR_PARSE, e);
                        }
                        if (result == null) {
                            requestListner.onEnd(resultMessage == null ? ResultMessage.error(ResultMessage.ERROR_OTHER, "未知错误") : resultMessage);
                            return;
                        } else {
                            response = result.getResult();
                            resultMessage = ResultMessage.success(result.getMessage());
                        }
                    }
                    try {
                        boolean success = false;
                        if(convertJson != null){
                            T t = convertJson.convert(response);
                            success = requestListner.onSuccess(t);
                        }else if(null != response && response.length() > 1 && response.charAt(0)=='['&&response.charAt(response.length()-1)==']'){
                            List<T> t = new GsonListConvertJson<>(requestListner.getClazz()).convert(response);//解析层已经处理空数据情况
                            success = requestListner.onSuccess(t);
                        }else if(requestListner.getClazz() == String.class){
                            success = requestListner.onSuccess((T)response);
                        }else if(null != response && response.length() > 1 && response.charAt(0)=='{'&&response.charAt(response.length()-1)=='}'){
                            T t = new GsonConvertJson<>(requestListner.getClazz()).convert(response);//解析层已经处理空数据情况
                            success = requestListner.onSuccess(t);
                        }
                        if(!success){
                            resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, "数据处理失败");
                        }
                    } catch (NoDataException e) {
                        e.printStackTrace();
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_NO_DATA, e);
                    }catch (DataException e) {
                        e.printStackTrace();
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, e);
                    } catch (Exception e) {
                        LogUtils.e("hw----------", e);
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_UI, "数据处理异常", e);
                    } finally {
                        requestListner.onEnd(resultMessage == null ? ResultMessage.success() : resultMessage);
                    }

                }else{
                    if (exception instanceof TimeoutException){
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_TIMEOUT, "网络请求超时", exception);
                    } else if (exception instanceof CancellationException){
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_CANCEL, "已取消请求", exception);
                    }else{
                        resultMessage = ResultMessage.error(ResultMessage.EROOR_NET, exception);
                    }
                    requestListner.onEnd(resultMessage);
                }
            }
        };
        return callback;
    }


    @Override
    public String doSyncRequest(String tag, RequestParem requestParem) {
        if (requestParem == null) return null;
        if(!NetworkUtils.isNetwork()){
            ResultMessage e = ResultMessage.error(ResultCode.ERROR_NO_NET,"无网络");
            return null;
        }
        //处理参数
        FutureBuilder builder = handlerParems(requestParem);

        try {
            String re = builder.group(tag).asString(Charset.forName("UTF-8")).get();
            return  re;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //处理
        return null;

    }
}
