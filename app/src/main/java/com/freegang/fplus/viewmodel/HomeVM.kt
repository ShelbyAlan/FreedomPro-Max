package com.freegang.fplus.viewmodel

import android.app.Application
import android.content.res.AssetManager
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.freegang.config.ConfigV1
import com.freegang.config.Version
import com.freegang.config.VersionConfig
import com.freegang.ktutils.app.KAppUtils
import com.freegang.ktutils.app.appVersionCode
import com.freegang.ktutils.app.appVersionName
import com.freegang.ktutils.app.readAssetsAsText
import com.freegang.ktutils.io.child
import com.freegang.ktutils.io.storageRootFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeVM(application: Application) : AndroidViewModel(application) {
    val app: Application get() = getApplication()

    private var _versionConfig = MutableLiveData<VersionConfig>()
    val versionConfig: LiveData<VersionConfig> = _versionConfig

    // module config
    private val config: ConfigV1 get() = ConfigV1.get()

    // 检查版本更新
    fun checkVersion() {
        if (KAppUtils.isAppInDebug(app)) return //测试包不检查更新
        if (app.appVersionName.contains(Regex("beta|alpha"))) return //非release包不检查更新
        viewModelScope.launch {
            val version = withContext(Dispatchers.IO) { Version.getRemoteReleasesLatest() }
            if (version != null) _versionConfig.value = version
        }
    }

    // 获取远程版本适配列表
    fun updateVersions() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val versions = Version.getVersions() ?: return@withContext
                val file = ConfigV1.getConfigDir(app).child("versions.json")
                file.writeText(versions)
            }
        }
    }

    // Freedom -> 外置存储器/Download/Freedom/
    val freedomData
        get() = getApplication<Application>().storageRootFile
            .child(Environment.DIRECTORY_DOWNLOADS)
            .child("Freedom")

    // FreedomPlus -> 外置存储器/DCIM/Freedom/
    val freedomPlusData
        get() = getApplication<Application>().storageRootFile
            .child(Environment.DIRECTORY_DCIM)
            .child("Freedom")

    // 是否开启去插件化
    val isDisablePlugin get() = config.isDisablePlugin

    // 是否显示兼容提示
    fun isSupportHint(value: Boolean) {
        config.isSupportHint = value
    }

    // 保存版本信息
    fun setVersionConfig(asset: AssetManager) {
        val version = asset.readAssetsAsText("version").split("-")
        config.isSupportHint = version[1].toLong() != config.versionConfig.versionCode
        config.versionConfig = ConfigV1.Version(
            version[0],
            version[1].toLong(),
            app.appVersionName,
            app.appVersionCode
        )
    }

    suspend fun isSupportVersions(versionName: String): String {
        /*val list = listOf(
            "23.5.0", "23.6.0", "23.7.0", "23.8.0", "23.9.0",
            "24.0.0", "24.1.0", "24.2.0", "24.3.0", "24.4.0",
            "24.5.0", "24.6.0", "24.7.0", "24.8.0", "24.9.0",
            "25.0.0", "25.1.0", "25.2.0", "25.3.0", "25.4.0",
            "25.5.0", "25.6.0", "25.7.0", "25.8.0", "25.9.0",
            "26.0.0", "26.1.0", "26.2.0", "26.3.0", "26.4.0",
            "26.5.0",
        )
        return if (list.contains(versionName)) "版本功能正常" else "自行测试功能"*/

        return withContext(Dispatchers.IO) {
            try {
                val versions = ConfigV1.getConfigDir(app).child("versions.json")
                val text = versions.readText()
                if (text.contains(versionName)) "版本功能正常" else "自行测试功能"
            } catch (e: Exception) {
                "自行测试功能"
            }
        }
    }
}