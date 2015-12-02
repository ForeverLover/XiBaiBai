package com.jph.xibaibai.mview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jph.xibaibai.R;

/**
 * 自定义dialog,
 */
public class SelfDialogView extends Dialog {

    private CustomClickListener clickListener;
    private TextView my_dialog_message;
    private TextView my_dialog_tips;
    private TextView my_dialog_confirm;
    private String message;
    private String msgTips;

    public SelfDialogView(Context context) {
        super(context, R.style.DialogStyleBottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);
        my_dialog_tips = (TextView) findViewById(R.id.my_dialog_tips);
        my_dialog_message = (TextView) findViewById(R.id.my_dialog_message);
        my_dialog_confirm = (TextView) findViewById(R.id.my_dialog_confirm);
        my_dialog_confirm.setOnClickListener(listener);
        my_dialog_message.setText(message);
        my_dialog_tips.setText(msgTips);
        setCanceledOnTouchOutside(false);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMsgTips(String msgTips) {
        this.msgTips = msgTips;
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                switch (v.getId()) {
                    case R.id.my_dialog_confirm:
                        clickListener.confirm();
                        break;
                }
            }
        }
    };

    public void setClickListener(CustomClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface CustomClickListener {
        public void cancel();
        public void confirm();
    }
}
