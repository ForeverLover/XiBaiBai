package com.jph.xibaibai.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.Html;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.entity.VersionInfo;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.model.http.XRequestCallBack;
import com.jph.xibaibai.ui.activity.base.AppManager;

import java.io.File;

/**
 * 版本更新
 *
 * @author jph
 */
public class VersionUpdate implements XRequestCallBack {

    private Context context;
    private DownloadManager downloadManager;
    private long idUpdate = 0;// 更新下载任务id
    private boolean showNoUpdate = true;// 是否显示提示

    public VersionUpdate(Context context) {
        super();
        this.context = context;
        downloadManager = (DownloadManager) context
                .getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public VersionUpdate(Context context, boolean showNoUpdate) {
        this(context);
        this.showNoUpdate = showNoUpdate;
    }

    public boolean isShowNoUpdate() {
        return showNoUpdate;
    }

    public void setShowNoUpdate(boolean showNoUpdate) {
        this.showNoUpdate = showNoUpdate;
    }

    public void checkUpdate() {
        new APIRequests(this).getVersionInfo(1);
    }

    private void showDialogUpadate(final VersionInfo versionInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(Html.fromHtml(versionInfo.getUpdate_content()));

//        if (versionInfo.getForceUpdate() != 1) {
//            // 非强制更新
//            builder.setTitle(R.string.dia_normalupdate);
//            builder.setNegativeButton(R.string.dia_negative, null);
//        } else {
            // 强制更新
            builder.setTitle(R.string.dia_forceupdate);
            builder.setNegativeButton(R.string.dia_exit, new OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // 退出程序
                    AppManager.getInstance().finishAllActivity();
                }
            });
//        }
        builder.setPositiveButton(R.string.dia_update,
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, R.string.toast_downloading,
                                Toast.LENGTH_SHORT).show();
                        update(versionInfo.getDownloadAddress().replace("\\", "/"));

//                        if (versionInfo.getForceUpdate() == 1)
                            ((Activity) context).moveTaskToBack(false);
                    }
                });

        Dialog dialogUpdate = builder.create();
//        if (versionInfo.getForceUpdate() == 1) {
            // 强制更新
            dialogUpdate.setCancelable(false);
            dialogUpdate.setCanceledOnTouchOutside(false);
//        }
        dialogUpdate.show();
    }

    /**
     * 更新下载
     *
     * @param url
     */
    private void update(String url) {
        if (idUpdate == 0) {
            // 删除之前的
            File fileSave = new File(Constants.UPDATEA_PATH);
            fileSave.delete();

            // 开始下载
            Uri resource = Uri.parse(url);
            Request request = new Request(
                    resource);
            request.setAllowedNetworkTypes(Request.NETWORK_MOBILE
                    | Request.NETWORK_WIFI);
            // 设置文件类型
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String mimeString = mimeTypeMap
                    .getMimeTypeFromExtension(MimeTypeMap
                            .getFileExtensionFromUrl(url));
            request.setMimeType(mimeString);
            // 在通知栏中显示
            request.setVisibleInDownloadsUi(true);
            // sdcard的目录下的download文件夹
            request.setDestinationUri(Uri.fromFile(new File(
                    Constants.UPDATEA_PATH)));
            request.setTitle(context.getResources().getString(
                    R.string.dialog_update));
            idUpdate = downloadManager.enqueue(request);
            // 保存id

            context.registerReceiver(receiverUpdateComplete, new IntentFilter(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }

    // 接收更新下载成功
    private BroadcastReceiver receiverUpdateComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 这里可以取得下载的id，这样就可以知道哪个文件下载完成了。适用与多个下载任务的监听
            if (idUpdate == intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, 0)) {
                Intent intentInstall = new Intent(Intent.ACTION_VIEW);
                intentInstall.setDataAndType(
                        Uri.fromFile(new File(Constants.UPDATEA_PATH)),
                        "application/vnd.android.package-archive");
                context.startActivity(intentInstall);

                context.unregisterReceiver(this);
            }
        }
    };

    @Override
    public void onSuccess(int taskId, Object... params) {
        ResponseJson responseJson = (ResponseJson) params[0];

        switch (taskId) {
            case Tasks.VERSION_INFO:
                // 获取更新信息

                VersionInfo systemInit = JSON.parseObject(responseJson.getResult()
                        .toString(), VersionInfo.class);

                if (systemInit.getVersionCode() > new AppInfo(context)
                        .getVersionCode(context.getPackageName()))
                    showDialogUpadate(systemInit);
                else if (showNoUpdate)
                    Toast.makeText(context, R.string.toast_noupdate,
                            Toast.LENGTH_SHORT).show();

                break;

            default:
                break;
        }
    }

    @Override
    public void onPrepare(int taskId) {

    }

    @Override
    public void onEnd(int taskId) {

    }

    @Override
    public void onFailed(int taskId, int errorCode, String errorMsg) {

    }

    @Override
    public boolean isCallBack() {
        return true;
    }

    @Override
    public void onLoading(int taskId, long count, long current) {

    }

}
