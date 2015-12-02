package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.TestModel;
import com.jph.xibaibai.ui.activity.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2015/7/29.
 */
public class TestActivity extends BaseActivity {

        @ViewInject(R.id.test_btn_test)
        private Button btnTest;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_test);

            ViewUtils.inject(this);


            btnTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TestModel testModel = new TestModel();
                    testModel.setMessage("哈哈哈哈");
                }
            });

        }

    }

