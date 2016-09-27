package io.github.ryanhoo.music.data.source.db;

import com.litesuits.orm.LiteOrm;
import io.github.ryanhoo.music.BuildConfig;
import io.github.ryanhoo.music.Injection;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/10/16
 * Time: 4:00 PM
 * Desc: LiteOrmHelper
 * LiteOrm工具类，使用Cascade实例，将会无限级联操作（不会死循环），将所有与这个Model相关的实体、关系都保存下来
 */
public class LiteOrmHelper {

    private static final String DB_NAME = "music-player.db";

    private static volatile LiteOrm sInstance;

    private LiteOrmHelper() {
        // Avoid direct instantiate
    }

    public static LiteOrm getInstance() {
        if (sInstance == null) {
            synchronized (LiteOrmHelper.class) {
                if (sInstance == null) {
                    sInstance = LiteOrm.newCascadeInstance(Injection.provideContext(), DB_NAME);
                    sInstance.setDebugged(BuildConfig.DEBUG);
                }
            }
        }
        return sInstance;
    }
}
