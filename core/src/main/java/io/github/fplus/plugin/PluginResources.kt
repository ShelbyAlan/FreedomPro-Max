package io.github.fplus.plugin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.content.res.loader.ResourcesLoader
import android.graphics.Movie
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import com.freegang.ktutils.log.KLogCat
import com.freegang.ktutils.reflect.fieldSetFirst
import com.freegang.ktutils.reflect.methodInvokeFirst
import com.freegang.ktutils.reflect.methodInvokes
import io.github.xpler.core.KtXposedHelpers
import java.io.InputStream

class PluginResources(
    private val originResources: Resources,
) : Resources(
    originResources.assets,
    originResources.displayMetrics,
    originResources.configuration,
) {

    private val pluginResources by lazy {
        if (KtXposedHelpers.modulePath.isEmpty()) {
            KLogCat.d("未获取到模块路径!")
            originResources
        } else {
            val assetManager = AssetManager::class.java.newInstance()
            assetManager.methodInvokes("addAssetPath", args = arrayOf(KtXposedHelpers.modulePath))
            Resources(assetManager, originResources.displayMetrics, originResources.configuration)
        }
    }

    val pluginAssets: AssetManager get() = pluginResources.assets

    override fun getText(id: Int): CharSequence {
        return try {
            originResources.getText(id)
        } catch (e: Exception) {
            try {
                pluginResources.getText(id)
            } catch (e: Exception) {
                "Unknown"
            }
        }
    }

    override fun getText(id: Int, def: CharSequence?): CharSequence {
        return try {
            originResources.getText(id, def)
        } catch (e: Exception) {
            try {
                pluginResources.getText(id, def)
            } catch (e: Exception) {
                "Unknown"
            }
        }
    }

    @SuppressLint("NewApi")
    override fun getFont(id: Int): Typeface {
        return try {
            originResources.getFont(id)
        } catch (e: Exception) {
            pluginResources.getFont(id)
        }
    }

    override fun getQuantityText(id: Int, quantity: Int): CharSequence {
        return try {
            originResources.getQuantityText(id, quantity)
        } catch (e: Exception) {
            try {
                pluginResources.getQuantityText(id, quantity)
            } catch (e: Exception) {
                "Unknown"
            }
        }
    }

    override fun getString(id: Int): String {
        return try {
            originResources.getString(id)
        } catch (e: Exception) {
            try {
                pluginResources.getString(id)
            } catch (e: Exception) {
                "Unknown"
            }
        }
    }

    override fun getString(id: Int, vararg formatArgs: Any?): String {
        return try {
            originResources.getString(id, *formatArgs)
        } catch (e: Exception) {
            try {
                pluginResources.getString(id, *formatArgs)
            } catch (e: Exception) {
                "Unknown"
            }
        }
    }

    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String {
        return try {
            originResources.getQuantityString(id, quantity, *formatArgs)
        } catch (e: Exception) {
            try {
                pluginResources.getQuantityString(id, quantity, *formatArgs)
            } catch (e: Exception) {
                "Unknown"
            }
        }
    }

    override fun getQuantityString(id: Int, quantity: Int): String {
        return try {
            originResources.getQuantityString(id, quantity)
        } catch (e: Exception) {
            try {
                pluginResources.getQuantityString(id, quantity)
            } catch (e: Exception) {
                "Unknown"
            }
        }
    }

    override fun getTextArray(id: Int): Array<CharSequence> {
        return try {
            originResources.getTextArray(id)
        } catch (e: Exception) {
            pluginResources.getTextArray(id)
        }
    }

    override fun getStringArray(id: Int): Array<String> {
        return try {
            originResources.getStringArray(id)
        } catch (e: Exception) {
            pluginResources.getStringArray(id)
        }
    }

    override fun getIntArray(id: Int): IntArray {
        return try {
            originResources.getIntArray(id)
        } catch (e: Exception) {
            pluginResources.getIntArray(id)
        }
    }

    override fun obtainTypedArray(id: Int): TypedArray {
        return try {
            originResources.obtainTypedArray(id)
        } catch (e: Exception) {
            pluginResources.obtainTypedArray(id)
        }
    }

    override fun getDimension(id: Int): Float {
        return try {
            originResources.getDimension(id)
        } catch (e: Exception) {
            pluginResources.getDimension(id)
        }
    }

    override fun getDimensionPixelOffset(id: Int): Int {
        return try {
            originResources.getDimensionPixelOffset(id)
        } catch (e: Exception) {
            pluginResources.getDimensionPixelOffset(id)
        }
    }

    override fun getDimensionPixelSize(id: Int): Int {
        return try {
            originResources.getDimensionPixelSize(id)
        } catch (e: Exception) {
            pluginResources.getDimensionPixelSize(id)
        }
    }

    override fun getFraction(id: Int, base: Int, pbase: Int): Float {
        return try {
            originResources.getFraction(id, base, pbase)
        } catch (e: Exception) {
            pluginResources.getFraction(id, base, pbase)
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getDrawable(id: Int): Drawable {
        return try {
            originResources.getDrawable(id)
        } catch (e: Exception) {
            pluginResources.getDrawable(id)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getDrawable(id: Int, theme: Theme?): Drawable {
        return try {
            originResources.getDrawable(id, theme)
        } catch (e: Exception) {
            pluginResources.getDrawable(id, theme)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun getDrawableForDensity(id: Int, density: Int): Drawable? {
        return try {
            originResources.getDrawableForDensity(id, density)
        } catch (e: Exception) {
            pluginResources.getDrawableForDensity(id, density)
        }
    }

    override fun getDrawableForDensity(id: Int, density: Int, theme: Theme?): Drawable? {
        return try {
            originResources.getDrawableForDensity(id, density, theme)
        } catch (e: Exception) {
            pluginResources.getDrawableForDensity(id, density, theme)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun getMovie(id: Int): Movie {
        return try {
            originResources.getMovie(id)
        } catch (e: Exception) {
            pluginResources.getMovie(id)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun getColor(id: Int): Int {
        return try {
            originResources.getColor(id)
        } catch (e: Exception) {
            pluginResources.getColor(id)
        }
    }

    override fun getColor(id: Int, theme: Theme?): Int {
        return try {
            originResources.getColor(id, theme)
        } catch (e: Exception) {
            pluginResources.getColor(id, theme)
        }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Deprecated("Deprecated in Java")
    override fun getColorStateList(id: Int): ColorStateList {
        return try {
            originResources.getColorStateList(id)
        } catch (e: Exception) {
            pluginResources.getColorStateList(id)
        }
    }

    override fun getColorStateList(id: Int, theme: Theme?): ColorStateList {
        return try {
            originResources.getColorStateList(id, theme)
        } catch (e: Exception) {
            pluginResources.getColorStateList(id, theme)
        }
    }

    override fun getBoolean(id: Int): Boolean {
        return try {
            originResources.getBoolean(id)
        } catch (e: Exception) {
            pluginResources.getBoolean(id)
        }
    }

    override fun getInteger(id: Int): Int {
        return try {
            originResources.getInteger(id)
        } catch (e: Exception) {
            pluginResources.getInteger(id)
        }
    }

    @SuppressLint("NewApi")
    override fun getFloat(id: Int): Float {
        return try {
            originResources.getFloat(id)
        } catch (e: Exception) {
            pluginResources.getFloat(id)
        }
    }

    override fun getLayout(id: Int): XmlResourceParser {
        return try {
            originResources.getLayout(id)
        } catch (e: Exception) {
            pluginResources.getLayout(id)
        }
    }

    override fun getAnimation(id: Int): XmlResourceParser {
        return try {
            originResources.getAnimation(id)
        } catch (e: Exception) {
            pluginResources.getAnimation(id)
        }
    }

    override fun getXml(id: Int): XmlResourceParser {
        /*return try {
            originResources.getXml(id)
        } catch (e: Exception) {
            pluginResources.getXml(id)
        }*/

        return try {
            pluginResources.getXml(id)
        } catch (e: Exception) {
            originResources.getXml(id)
        }
    }

    override fun openRawResource(id: Int): InputStream {
        return try {
            originResources.openRawResource(id)
        } catch (e: Exception) {
            pluginResources.openRawResource(id)
        }
    }

    override fun openRawResource(id: Int, value: TypedValue?): InputStream {
        return try {
            originResources.openRawResource(id, value)
        } catch (e: Exception) {
            pluginResources.openRawResource(id, value)
        }
    }

    override fun openRawResourceFd(id: Int): AssetFileDescriptor {
        return try {
            originResources.openRawResourceFd(id)
        } catch (e: Exception) {
            pluginResources.openRawResourceFd(id)
        }
    }

    override fun getValue(id: Int, outValue: TypedValue?, resolveRefs: Boolean) {
        try {
            originResources.getValue(id, outValue, resolveRefs)
        } catch (e: Exception) {
            pluginResources.getValue(id, outValue, resolveRefs)
        }
    }

    @SuppressLint("DiscouragedApi")
    override fun getValue(name: String?, outValue: TypedValue?, resolveRefs: Boolean) {
        try {
            originResources.getValue(name, outValue, resolveRefs)
        } catch (e: Exception) {
            pluginResources.getValue(name, outValue, resolveRefs)
        }
    }

    override fun getValueForDensity(id: Int, density: Int, outValue: TypedValue?, resolveRefs: Boolean) {
        try {
            originResources.getValueForDensity(id, density, outValue, resolveRefs)
        } catch (e: Exception) {
            pluginResources.getValueForDensity(id, density, outValue, resolveRefs)
        }
    }

    override fun obtainAttributes(set: AttributeSet?, attrs: IntArray?): TypedArray {
        return try {
            originResources.obtainAttributes(set, attrs)
        } catch (e: Exception) {
            pluginResources.obtainAttributes(set, attrs)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun updateConfiguration(config: Configuration?, metrics: DisplayMetrics?) {
        try {
            originResources.updateConfiguration(config, metrics)
        } catch (e: Exception) {
            pluginResources.updateConfiguration(config, metrics)
        }
    }

    override fun getDisplayMetrics(): DisplayMetrics {
        return try {
            originResources.displayMetrics
        } catch (e: Exception) {
            pluginResources.displayMetrics
        }
    }

    override fun getConfiguration(): Configuration {
        return try {
            originResources.getConfiguration()
        } catch (e: Exception) {
            pluginResources.getConfiguration()
        }
    }

    @SuppressLint("DiscouragedApi")
    override fun getIdentifier(name: String?, defType: String?, defPackage: String?): Int {
        return try {
            originResources.getIdentifier(name, defType, defPackage)
        } catch (e: Exception) {
            pluginResources.getIdentifier(name, defType, defPackage)
        }
    }

    override fun getResourceName(resid: Int): String {
        return return try {
            originResources.getResourceName(resid)
        } catch (e: Exception) {
            pluginResources.getResourceName(resid)
        }
    }

    override fun getResourcePackageName(resid: Int): String {
        return try {
            originResources.getResourcePackageName(resid)
        } catch (e: Exception) {
            pluginResources.getResourcePackageName(resid)
        }
    }

    override fun getResourceTypeName(resid: Int): String {
        return try {
            originResources.getResourceTypeName(resid)
        } catch (e: Exception) {
            pluginResources.getResourceTypeName(resid)
        }
    }

    override fun getResourceEntryName(resid: Int): String {
        return try {
            originResources.getResourceEntryName(resid)
        } catch (e: Exception) {
            pluginResources.getResourceEntryName(resid)
        }
    }

    override fun parseBundleExtras(parser: XmlResourceParser?, outBundle: Bundle?) {
        try {
            originResources.parseBundleExtras(parser, outBundle)
        } catch (e: Exception) {
            pluginResources.parseBundleExtras(parser, outBundle)
        }
    }

    override fun parseBundleExtra(tagName: String?, attrs: AttributeSet?, outBundle: Bundle?) {
        try {
            originResources.parseBundleExtra(tagName, attrs, outBundle)
        } catch (e: Exception) {
            pluginResources.parseBundleExtra(tagName, attrs, outBundle)
        }
    }

    @SuppressLint("NewApi")
    override fun addLoaders(vararg loaders: ResourcesLoader?) {
        try {
            originResources.addLoaders(*loaders)
        } catch (e: Exception) {
            pluginResources.addLoaders(*loaders)
        }
    }

    @SuppressLint("NewApi")
    override fun removeLoaders(vararg loaders: ResourcesLoader?) {
        try {
            originResources.removeLoaders(*loaders)
        } catch (e: Exception) {
            pluginResources.removeLoaders(*loaders)
        }
    }
}

// 代理Resources
fun proxyRes(activity: Activity?) {
    activity?.runCatching {
        this.fieldSetFirst("mResources", PluginResources(activity.resources))
    }
}

// 合并Resources
fun injectRes(res: Resources?) {
    if (res != null) {
        val assets = res.assets
        assets.methodInvokeFirst("addAssetPath", args = arrayOf(KtXposedHelpers.modulePath))
    }
}