package overun.utils;

import java.util.UUID;

/**
 * @Description
 * @Author ZhangPY
 * @Date 2020/5/9
 */
public class TextUtils {

    /**
     * 获取32位UUID
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").trim();
    }
}
