package hook;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import de.robv.android.xposed.XC_MethodHook;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.StaticData;

public class HookLogic extends XC_MethodHook {

    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        Object[] args = param.args;
        Future<Object[]> future = StaticData.es.submit(new ParameterHandleTask(args));
        Object[] res = future.get();
        // 直接args=future.get()好像不会成功，这里直接一个个赋值
        for (int i = 0; i < args.length; i++) {
            args[i] = res[i];
        }
    }

    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

    }


}
