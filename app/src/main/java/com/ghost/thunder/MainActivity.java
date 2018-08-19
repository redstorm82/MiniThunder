package com.ghost.thunder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.ghost.thunder.demo.R;

import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    EditText edit_url;
    Button button_download;
    TextView textView_status;
    String filepath = "";
    SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");
    ArrayList<Download> list_download = new ArrayList<Download>();
    ListView listView;
    MyBaseAdapter adapter;

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0) {
                long taskId = (long) msg.obj;
                XLTaskInfo taskInfo = XLTaskHelper.instance(getApplicationContext()).getTaskInfo(taskId);
                filepath = Environment.getExternalStorageDirectory().getPath() + "/" + taskInfo.mFileName;
                /*
                int progress;
                if(taskInfo.mFileSize != 0){
                    progress = (int)(taskInfo.mDownloadSize * 100 / taskInfo.mFileSize);
                }else{
                    progress = 0;
                }
                long duration_left = 0;
                if(taskInfo.mDownloadSpeed != 0){
                    duration_left = (taskInfo.mFileSize - taskInfo.mDownloadSize) / taskInfo.mDownloadSpeed * 1000;
                }
                Date date = new Date(duration_left);

                textView_status.setText("文件大小：" + convertFileSize(taskInfo.mFileSize)
                        + "\n已下载：" + convertFileSize(taskInfo.mDownloadSize)
                        + "\n进度：" + String.valueOf(percent) + "%"
                        + "\n速度：" + convertFileSize(taskInfo.mDownloadSpeed) + "/s"
                        + "\nDCDN速度：" + convertFileSize(taskInfo.mAdditionalResDCDNSpeed) + "/s"
                        + "\n剩余时间：" + SDF.format(date)
                        + "\n文件名称：" + taskInfo.mFileName);
                */
                for(int i=0; i<list_download.size(); i++){
                    if(list_download.get(i).taskId == taskId){
                        Log.e("tackId", taskId + "");
                        list_download.get(i).mFileSize = taskInfo.mFileSize;
                        list_download.get(i).mDownloadSize = taskInfo.mDownloadSize;
                        list_download.get(i).mDownloadSpeed = taskInfo.mDownloadSpeed;
                        list_download.get(i).mAdditionalResDCDNSpeed = taskInfo.mAdditionalResDCDNSpeed;
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }

                handler.sendMessageDelayed(handler.obtainMessage(0,taskId),1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        SDF.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        XLTaskHelper.init(getApplicationContext());

        edit_url = (EditText) findViewById(R.id.edit_url);
        button_download = (Button) findViewById(R.id.button_down);
        textView_status = (TextView) findViewById(R.id.textView_status);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new MyBaseAdapter();
        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(Color.BLUE));
        listView.setDividerHeight(3);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int i = list_download.size() - position -1; // 倒序还原
                String filepath = list_download.get(i).path + "/" + list_download.get(i).filename;
                if(!filepath.equals("")) {
                    String suffix = MimeTypeMap.getFileExtensionFromUrl(filepath);
                    String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
                    Log.e("line127", filepath);
                    Log.e("line128", suffix + " = " + type);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file://" + filepath), type);
                    startActivity(intent);
                }
            }
        });


        button_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager IMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                IMM.hideSoftInputFromWindow(edit_url.getWindowToken(), 0);
                if(!TextUtils.isEmpty(edit_url.getText())) {
                    long taskId = 0;
                    try {
                        String url = edit_url.getText().toString();
                        String path = Environment.getExternalStorageDirectory().getPath() + "/";
                        taskId = XLTaskHelper.instance(getApplicationContext()).addThunderTask(url, path, null);
                        String filename = XLTaskHelper.instance(getApplicationContext()).getFileName(edit_url.getText().toString());
                        list_download.add(new Download(taskId, url, path, filename));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(handler.obtainMessage(0,taskId));
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "退出");
        menu.add(0, 1, 1, "关于");
        menu.add(0, 2, 2, "更新日志");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        switch (item_id) {
            case 0:
                finish();
                break;
            case 1:
                new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher)
                        .setTitle("迷你迅雷 V1.0")
                        .setMessage("使用 https://github.com/oceanzhang01/MiniThunder 提供的迅雷模块。\n作者：黄颖\nQQ：84429027")
                        .setPositiveButton("确定", null).show();
                break;
            case 2:
                new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("更新日志")
                        .setMessage("V1.0 (2018)\n1.增加打开文件、进度百分比、字节转换、剩余时长、下载列表、列表更新。")
                        .setPositiveButton("确定", null).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }


    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list_download.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.listview_item, viewGroup, false);
            }

            TextView textView_taskId = (TextView) view.findViewById(R.id.textView_taskId);
            TextView textView_url = (TextView) view.findViewById(R.id.textView_url);
            TextView textView_filename = (TextView) view.findViewById(R.id.textView_filename);
            TextView textView_filesize = (TextView) view.findViewById(R.id.textView_filesize);
            TextView textView_downloadSize = (TextView) view.findViewById(R.id.textView_downloadSize);
            TextView textView_progress = (TextView) view.findViewById(R.id.textView_progress);
            TextView textView_downloadSpeed = (TextView) view.findViewById(R.id.textView_downloadSpeed);
            TextView textView_DCDNSpeed = (TextView) view.findViewById(R.id.textView_DCDNSpeed);
            TextView textView_timeLeft = (TextView) view.findViewById(R.id.textView_timeLeft);

            int j = getCount() - i -1; // 实现倒序
            textView_taskId.setText("taskId：" + list_download.get(j).taskId + "");
            textView_url.setText("下载地址：" + list_download.get(j).url);
            textView_filename.setText("文件名：" + list_download.get(j).filename);
            textView_filesize.setText("文件大小：" + convertFileSize(list_download.get(j).mFileSize));
            textView_downloadSize.setText("已下载：" + convertFileSize(list_download.get(j).mDownloadSize));

            int progress;
            if(list_download.get(j).mFileSize != 0){
                progress = (int)(list_download.get(j).mDownloadSize * 100 / list_download.get(j).mFileSize);
            }else{
                progress = 0;
            }
            textView_progress.setText("进度：" + progress + "%");

            textView_downloadSpeed.setText("速度：" + convertFileSize(list_download.get(j).mDownloadSpeed) + "/s");
            textView_DCDNSpeed.setText("DCDN速度：" + convertFileSize(list_download.get(j).mAdditionalResDCDNSpeed) + "/s");

            long duration_left = 0;
            if(list_download.get(j).mDownloadSpeed != 0){
                duration_left = (list_download.get(j).mFileSize - list_download.get(j).mDownloadSize) / list_download.get(j).mDownloadSpeed * 1000;
            }
            Date date = new Date(duration_left);
            textView_timeLeft.setText("剩余时间：" + SDF.format(date));

            return view;
        }
    }

}