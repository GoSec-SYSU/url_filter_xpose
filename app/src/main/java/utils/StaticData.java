package utils;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StaticData {
    public static ExecutorService es = Executors.newFixedThreadPool(4);
    public static String prefFileName = "data";
    public static String SinkKey = "sink_url";
    public static String RootPath = File.separator + "sdcard" + File.separator;
    public static String[] NeededPermission = {
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.INTERNET",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.MOUNT_UNMOUNT_FILESYSTEMS"
    };
//    public static String sinkURL = "http://106.52.218.192:10598/get_sink";
//    public static String DataHandlerURL = "http://106.52.218.192:10598/sink_class";

    public static String ROOT_URL = "http://211.66.130.46:5555/";
    public static String sinkURL = ROOT_URL + "get_sink";
    public static String dataHandlerURL = ROOT_URL + "sink_class";
    public static String urlFilteURL = ROOT_URL + "filter_url";
}
