package com.xunlei.downloadlib;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by oceanzhang on 16/10/8.
 */

public class DelegateApplication extends Application {
    private Application app;

    public DelegateApplication(Application app) {
        this.app = app;
    }

    private static final String TAG = "DelegateApplication";
    @Override
    public Context getBaseContext() {
        Log.d(TAG,"----getBaseContext");
        return app.getBaseContext();
    }

    @Override
    public AssetManager getAssets() {
        return app.getAssets();
    }

    @Override
    public Resources getResources() {
        return app.getResources();
    }

    @Override
    public PackageManager getPackageManager() {
        Log.d(TAG,"----getPackageManager");
        return app.getPackageManager();
    }

    @Override
    public ContentResolver getContentResolver() {
        return app.getContentResolver();
    }

    @Override
    public Looper getMainLooper() {
        return app.getMainLooper();
    }

    @Override
    public Context getApplicationContext() {
        Log.d(TAG,"----getApplicationContext");
        return app.getApplicationContext();
    }

    @Override
    public void setTheme(int resid) {
        app.setTheme(resid);
    }

    @Override
    public Resources.Theme getTheme() {
        return app.getTheme();
    }

    @Override
    public ClassLoader getClassLoader() {
        Log.d(TAG,"----getBaseContext");
        return app.getClassLoader();
    }

    @Override
    public String getPackageName() {
        Log.d(TAG,"----getPackageName");
        return "com.xunlei.downloadprovider";
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        Log.d(TAG,"----getApplicationInfo");
        return app.getApplicationInfo();
    }

    @Override
    public String getPackageResourcePath() {
        Log.d(TAG,"----getBaseContext");
        return app.getPackageResourcePath();
    }

    @Override
    public String getPackageCodePath() {
        Log.d(TAG,"----getBaseContext");
        return app.getPackageCodePath();
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        Log.d(TAG,"----getBaseContext");
        return app.getSharedPreferences(name, mode);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
        Log.d(TAG,"----getBaseContext");
        return app.moveSharedPreferencesFrom(sourceContext, name);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean deleteSharedPreferences(String name) {
        Log.d(TAG,"----getBaseContext");
        return app.deleteSharedPreferences(name);
    }

    @Override
    public FileInputStream openFileInput(String name) throws FileNotFoundException {
        Log.d(TAG,"----getBaseContext");
        return app.openFileInput(name);
    }

    @Override
    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        Log.d(TAG,"----getBaseContext");
        return app.openFileOutput(name, mode);
    }

    @Override
    public boolean deleteFile(String name) {
        Log.d(TAG,"----getBaseContext");
        return app.deleteFile(name);
    }

    @Override
    public File getFileStreamPath(String name) {
        Log.d(TAG,"----getBaseContext");
        return app.getFileStreamPath(name);
    }

    @Override
    public String[] fileList() {
        return app.fileList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public File getDataDir() {
        return app.getDataDir();
    }

    @Override
    public File getFilesDir() {
        return app.getFilesDir();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public File getNoBackupFilesDir() {
        return app.getNoBackupFilesDir();
    }

    @Override
    public File getExternalFilesDir(String type) {
        return app.getExternalFilesDir(type);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public File[] getExternalFilesDirs(String type) {
        return app.getExternalFilesDirs(type);
    }

    @Override
    public File getObbDir() {
        return app.getObbDir();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public File[] getObbDirs() {
        return app.getObbDirs();
    }

    @Override
    public File getCacheDir() {
        return app.getCacheDir();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public File getCodeCacheDir() {
        return app.getCodeCacheDir();
    }

    @Override
    public File getExternalCacheDir() {
        return app.getExternalCacheDir();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public File[] getExternalCacheDirs() {
        return app.getExternalCacheDirs();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public File[] getExternalMediaDirs() {
        return app.getExternalMediaDirs();
    }

    @Override
    public File getDir(String name, int mode) {
        return app.getDir(name, mode);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return app.openOrCreateDatabase(name, mode, factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return app.openOrCreateDatabase(name, mode, factory, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean moveDatabaseFrom(Context sourceContext, String name) {
        return app.moveDatabaseFrom(sourceContext, name);
    }

    @Override
    public boolean deleteDatabase(String name) {
        return app.deleteDatabase(name);
    }

    @Override
    public File getDatabasePath(String name) {
        return app.getDatabasePath(name);
    }

    @Override
    public String[] databaseList() {
        return app.databaseList();
    }

    @Override
    public Drawable getWallpaper() {
        return app.getWallpaper();
    }

    @Override
    public Drawable peekWallpaper() {
        return app.peekWallpaper();
    }

    @Override
    public int getWallpaperDesiredMinimumWidth() {
        return app.getWallpaperDesiredMinimumWidth();
    }

    @Override
    public int getWallpaperDesiredMinimumHeight() {
        return app.getWallpaperDesiredMinimumHeight();
    }

    @Override
    public void setWallpaper(Bitmap bitmap) throws IOException {
        app.setWallpaper(bitmap);
    }

    @Override
    public void setWallpaper(InputStream data) throws IOException {
        app.setWallpaper(data);
    }

    @Override
    public void clearWallpaper() throws IOException {
        app.clearWallpaper();
    }

    @Override
    public void startActivity(Intent intent) {
        app.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void startActivity(Intent intent, Bundle options) {
        app.startActivity(intent, options);
    }

    @Override
    public void startActivities(Intent[] intents) {
        app.startActivities(intents);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void startActivities(Intent[] intents, Bundle options) {
        app.startActivities(intents, options);
    }

    @Override
    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        app.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        app.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        app.sendBroadcast(intent);
    }

    @Override
    public void sendBroadcast(Intent intent, String receiverPermission) {
        app.sendBroadcast(intent, receiverPermission);
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String receiverPermission) {
        app.sendOrderedBroadcast(intent, receiverPermission);
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        app.sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle user) {
        app.sendBroadcastAsUser(intent, user);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission) {
        app.sendBroadcastAsUser(intent, user, receiverPermission);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        app.sendOrderedBroadcastAsUser(intent, user, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Override
    public void sendStickyBroadcast(Intent intent) {
        app.sendStickyBroadcast(intent);
    }

    @Override
    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        app.sendStickyOrderedBroadcast(intent, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Override
    public void removeStickyBroadcast(Intent intent) {
        app.removeStickyBroadcast(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {
        app.sendStickyBroadcastAsUser(intent, user);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        app.sendStickyOrderedBroadcastAsUser(intent, user, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {
        app.removeStickyBroadcastAsUser(intent, user);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return app.registerReceiver(receiver, filter);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
        return app.registerReceiver(receiver, filter, broadcastPermission, scheduler);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        app.unregisterReceiver(receiver);
    }

    @Override
    public ComponentName startService(Intent service) {
        return app.startService(service);
    }

    @Override
    public boolean stopService(Intent name) {
        return app.stopService(name);
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return app.bindService(service, conn, flags);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        app.unbindService(conn);
    }

    @Override
    public boolean startInstrumentation(ComponentName className, String profileFile, Bundle arguments) {
        return app.startInstrumentation(className, profileFile, arguments);
    }

    @Override
    public Object getSystemService(String name) {
        return app.getSystemService(name);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public String getSystemServiceName(Class<?> serviceClass) {
        return app.getSystemServiceName(serviceClass);
    }

    @Override
    public int checkPermission(String permission, int pid, int uid) {
        return app.checkPermission(permission, pid, uid);
    }

    @Override
    public int checkCallingPermission(String permission) {
        return app.checkCallingPermission(permission);
    }

    @Override
    public int checkCallingOrSelfPermission(String permission) {
        return app.checkCallingOrSelfPermission(permission);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int checkSelfPermission(String permission) {
        return app.checkSelfPermission(permission);
    }

    @Override
    public void enforcePermission(String permission, int pid, int uid, String message) {
        app.enforcePermission(permission, pid, uid, message);
    }

    @Override
    public void enforceCallingPermission(String permission, String message) {
        app.enforceCallingPermission(permission, message);
    }

    @Override
    public void enforceCallingOrSelfPermission(String permission, String message) {
        app.enforceCallingOrSelfPermission(permission, message);
    }

    @Override
    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        app.grantUriPermission(toPackage, uri, modeFlags);
    }

    @Override
    public void revokeUriPermission(Uri uri, int modeFlags) {
        app.revokeUriPermission(uri, modeFlags);
    }

    @Override
    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
        return app.checkUriPermission(uri, pid, uid, modeFlags);
    }

    @Override
    public int checkCallingUriPermission(Uri uri, int modeFlags) {
        return app.checkCallingUriPermission(uri, modeFlags);
    }

    @Override
    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        return app.checkCallingOrSelfUriPermission(uri, modeFlags);
    }

    @Override
    public int checkUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags) {
        return app.checkUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags);
    }

    @Override
    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {
        app.enforceUriPermission(uri, pid, uid, modeFlags, message);
    }

    @Override
    public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {
        app.enforceCallingUriPermission(uri, modeFlags, message);
    }

    @Override
    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {
        app.enforceCallingOrSelfUriPermission(uri, modeFlags, message);
    }

    @Override
    public void enforceUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags, String message) {
        app.enforceUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags, message);
    }

    @Override
    public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
        return app.createPackageContext(packageName, flags);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public Context createConfigurationContext(Configuration overrideConfiguration) {
        return app.createConfigurationContext(overrideConfiguration);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public Context createDisplayContext(Display display) {
        return app.createDisplayContext(display);
    }

    @Override
    public boolean isRestricted() {
        return app.isRestricted();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Context createDeviceProtectedStorageContext() {
        return app.createDeviceProtectedStorageContext();
    }

    public DelegateApplication() {
        super();
    }

    @Override
    public void onCreate() {
        app.onCreate();
    }

    @Override
    public void onTerminate() {
        app.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        app.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        app.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        app.onTrimMemory(level);
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        app.registerComponentCallbacks(callback);
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        app.unregisterComponentCallbacks(callback);
    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        app.registerActivityLifecycleCallbacks(callback);
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        app.unregisterActivityLifecycleCallbacks(callback);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void registerOnProvideAssistDataListener(OnProvideAssistDataListener callback) {
        app.registerOnProvideAssistDataListener(callback);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void unregisterOnProvideAssistDataListener(OnProvideAssistDataListener callback) {
        app.unregisterOnProvideAssistDataListener(callback);
    }

    @Override
    public int hashCode() {
        return app.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return app.equals(obj);
    }


    @Override
    public String toString() {
        return app.toString();
    }
}
