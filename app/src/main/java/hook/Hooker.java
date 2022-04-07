package hook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.StaticData;

public class Hooker {

    public List<XC_MethodHook.Unhook> hook(XC_LoadPackage.LoadPackageParam loadPackageParam,
                                           Map<String, Map<String, Map<String, List<List<Object>>>>> appMp) {
        List<XC_MethodHook.Unhook> unhookList = new ArrayList<>();
        // appMp: app package name -> class name -> method name -> arguments list
        String curAppPackageName = loadPackageParam.packageName;
        // android can't load class file, so I can't dynamicly load class like what I do in java. Damn!
        if (appMp.containsKey(curAppPackageName)) {
            // classMp: class name -> method name -> arguments list
            Map<String, Map<String, List<List<Object>>>> classMp = appMp.get(curAppPackageName);
            for (Map.Entry<String, Map<String, List<List<Object>>>> classEntry : classMp.entrySet()) {
                Class clazz = null;
                try {
                    clazz = loadPackageParam.classLoader.loadClass(classEntry.getKey());
                    for (Map.Entry<String, List<List<Object>>> methodEntrys : classEntry.getValue().entrySet()) {
                        String methodName = methodEntrys.getKey();
                        for (List<Object> methodParameter : methodEntrys.getValue()) {
                            methodParameter.add(new HookLogic());
                            Object[] arr = methodParameter.toArray();
                            XC_MethodHook.Unhook unhook = XposedHelpers.findAndHookMethod(clazz, methodName, arr);
                            unhookList.add(unhook);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return unhookList;
    }

    public void unhook(List<XC_MethodHook.Unhook> unhookList) {
        for (XC_MethodHook.Unhook unhook : unhookList)
            unhook.unhook();
    }

}
