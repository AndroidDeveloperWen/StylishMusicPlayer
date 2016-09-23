package io.github.ryanhoo.music.utils;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/1/16
 * Time: 10:13 PM
 * Desc: GradientUtils
 * 使用GradientDrawable实现渐变色效果的工具类
 */
public class GradientUtils {

    public static GradientDrawable create(@ColorInt int startColor, @ColorInt int endColor, int radius,
                                          @FloatRange(from = 0f, to = 1f) float centerX,
                                          @FloatRange(from = 0f, to = 1f) float centerY) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColors(new int[]{
                startColor,
                endColor
        });
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gradientDrawable.setGradientRadius(radius);
        gradientDrawable.setGradientCenter(centerX, centerY);
        return gradientDrawable;
    }
}
