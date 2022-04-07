package utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hookObserve.Subject;
import de.robv.android.xposed.XSharedPreferences;

public class MetadataListener extends Thread {
    private Subject subject;
    public int preHash = 0;

    public MetadataListener(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void run() {
        // 心跳检测
        Map<String, Map<String, Map<String, List<List<Object>>>>> curMp = getEmpty();
        while (true) {
            String sinkUrl = StaticData.sinkURL;
            try {
                curMp = getHookMethodFromNet(StaticData.sinkURL);
                String curMpStr = curMp.toString();
                Log.i("sinkURL: ", sinkUrl);
                Log.i("mySinkData!!!  ", curMpStr);
                int curMpHash = curMpStr.hashCode();
                // 跟之前的hash不同才重新hook，避免重复hook相同的
                if(curMpHash!=preHash){
                    preHash = curMpHash;
                    subject.notifyObserver(curMp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 睡眠
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Map<String, Map<String, List<List<Object>>>>> getHookMethodFromNet(String url) {
        String sinkUrl = url;
        Map<String, Map<String, Map<String, List<List<Object>>>>> curMp = new HashMap<>();
        try {
            String sinkJSONStr = DownloadUtil.downloadJSONByOkHttp(sinkUrl);
            System.out.println("sinkJSONStr:" + sinkJSONStr);
            curMp = (Map<String, Map<String, Map<String, List<List<Object>>>>>) JSON.parse(sinkJSONStr);
            System.out.println("curMp in func:" + curMp);
            Log.i("sinkData in getHookMethodFromNet: ", curMp.toString());
//            subject.notifyObserver(curMp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curMp;
    }

    public Map<String, Map<String, Map<String, List<List<Object>>>>> getEmpty() {
        Map<String, Map<String, Map<String, List<List<Object>>>>> mp = new HashMap<>();
        return mp;
    }

    // for demo
    public static Map<String, Map<String, Map<String, List<List<Object>>>>> getHookMethod() {
        Map<String, Map<String, List<List<Object>>>> appMp = new HashMap<>();
        List<List<Object>> sumList = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        list.add("java.lang.String");
        list.add("java.lang.Integer");
        list.add("int");
        list.add("[C");
        sumList.add(list);
        Map<String, List<List<Object>>> tmp = new HashMap<>();
        tmp.put("hello", sumList);
        appMp.put("gosec.xpose_victim.MainActivity", tmp);
        Map<String, Map<String, Map<String, List<List<Object>>>>> mp = new HashMap<>();
        mp.put("gosec.xpose_victim", appMp);
        return mp;
    }
}
