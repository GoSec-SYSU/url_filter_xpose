package hook;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.concurrent.Callable;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.StaticData;

class ParameterHandleTask implements Callable<Object[]> {
    private Object[] args;

    public ParameterHandleTask(Object[] args) {
        this.args = args;
    }

    @Override
    public Object[] call() throws Exception {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                String res = filterUrlByOkHttp(StaticData.urlFilteURL, (String) args[i]);
                String filtedUrl = ((Map<String, String>) JSON.parse(res)).get("url");
                System.out.println("filtedUrl: " + filtedUrl);
                args[i] = filtedUrl;
//                args[i] = "I am ur mom, rememeber that, bull!";
            } else {

            }
        }
        return args;
    }

    public static String filterUrlByOkHttp(String url, String oriUrl) {
        OkHttpClient client = new OkHttpClient();
        MultipartBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("ori_url", oriUrl)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        String result = "";
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}