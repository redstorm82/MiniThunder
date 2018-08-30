package com.ghost.thunder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ghost.thunder.demo.R;

import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    EditText editText_new_url, editText_new_path;
    String filepath = "", path = "";
    SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");
    ArrayList<Download> list_download = new ArrayList<>();
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

                handler.sendMessageDelayed(handler.obtainMessage(0, taskId), 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        path = Environment.getExternalStorageDirectory().getPath() + "/";
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        SDF.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        XLTaskHelper.init(getApplicationContext());

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "新建");
        menu.add(0, 1, 1, "关于");
        menu.add(0, 2, 2, "更新日志");
        menu.add(0, 3, 3, "退出");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        switch (item_id) {
            case 0:
                dialog_new_download();
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
                        .setMessage("V1.0 (2018)\n1.增加打开文件、进度百分比、字节转换、剩余时长、下载列表、列表更新、新建下载对话框。")
                        .setPositiveButton("确定", null).show();
                break;
            case 3:
                finish();
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


    void dialog_new_download(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_new_download, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("新建下载")
                .setIcon(R.mipmap.ic_launcher)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,	int which) {
                        //Toast.makeText(getApplicationContext(), "开始下载", Toast.LENGTH_SHORT).show();
                        String url = editText_new_url.getText().toString();
                        if(!url.equals("")) {
                            long taskId = 0;
                            try {
                                taskId = XLTaskHelper.instance(getApplicationContext()).addThunderTask(url, path, null);
                                String filename = XLTaskHelper.instance(getApplicationContext()).getFileName(editText_new_url.getText().toString());
                                list_download.add(new Download(taskId, url, path, filename));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            handler.sendMessage(handler.obtainMessage(0, taskId));
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,	int which) {
                    }
                })
                .setNeutralButton("打开BT种子", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,	int which) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("application/x-bittorrent");
                        startActivityForResult(intent, 2);
                    }
                })
                .create();

        editText_new_url = (EditText) view.findViewById(R.id.editText_new_url);
        EditText editText_new_filename = (EditText) view.findViewById(R.id.editText_new_filename);
        editText_new_path = (EditText) view.findViewById(R.id.editText_new_path);
        ImageButton imageButton_path = (ImageButton) view.findViewById(R.id.imageButton_path);

        // 获取剪贴板文本，填入网址和文件名
        ClipboardManager CM = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = CM.getPrimaryClip();
        if(data != null) {
            ClipData.Item item = data.getItemAt(0);
            String s = item.getText().toString();
            if (s.contains("://") || s.startsWith("magnet:?xt=urn:btih:")) {
                editText_new_url.setText(s);
                editText_new_filename.setText(XLTaskHelper.instance(getApplicationContext()).getFileName(s));
            }
        }
        String path = Environment.getExternalStorageDirectory().getPath() + "/";
        editText_new_path.setText(path);

        dialog.show();

        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4
        //dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)/4*3), LinearLayout.LayoutParams.WRAP_CONTENT);

        imageButton_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) { //是否选择，没选择就不会继续
            Uri uri = data.getData();   // 得到uri，后面就是将uri转化成file的过程。
            //String scheme = uri.getScheme();
            Log.e("uri", uri.toString());
            String[] projection = { "_data" };
            Cursor cursor  = getContentResolver().query(uri, projection, null, null, null);
            if(cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow("_data");
                cursor.moveToFirst();
                String filepath = cursor.getString(column_index);
                Log.e("filepath", filepath);
                if (requestCode == 1) {
                    int endIndex = filepath.lastIndexOf("/");
                    if (endIndex != -1) {
                        path = filepath.substring(0, endIndex);
                        Log.e("path", path);
                        //Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
                        editText_new_path.setText(path);
                    }
                } else if (requestCode == 2) {
                    Toast.makeText(MainActivity.this, filepath, Toast.LENGTH_SHORT).show();
                }
            }
        }
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
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

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
            progressBar.setProgress(progress);

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