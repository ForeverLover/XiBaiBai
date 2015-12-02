package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSON;
import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.RecyclerCommentAdapter;
import com.jph.xibaibai.model.entity.Comment;
import com.jph.xibaibai.model.entity.ResponseJson;
import com.jph.xibaibai.model.http.APIRequests;
import com.jph.xibaibai.model.http.IAPIRequests;
import com.jph.xibaibai.model.http.Tasks;
import com.jph.xibaibai.mview.DividerItemDecoration;
import com.jph.xibaibai.mview.morerecyclerview.MoreRecyclerView;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.Constants;
import com.jph.xibaibai.utils.sp.SPUserInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部评价
 * Created by jph on 2015/9/13.
 */
public class CommentActivity extends TitleActivity implements
        SwipeRefreshLayout.OnRefreshListener, MoreRecyclerView.OnLoadMoreListener {
    private int page = 0;
    private IAPIRequests mAPIRequests;
    private RecyclerCommentAdapter mCommentIngAdapter;

    @ViewInject(R.id.comment_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @ViewInject(R.id.comment_recycler_comment)
    MoreRecyclerView mRecyclerViewComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        onRefresh();
    }

    @Override
    public void initData() {
        super.initData();
        mAPIRequests = new APIRequests(this);
        mCommentIngAdapter = new RecyclerCommentAdapter(new ArrayList<Comment>());
    }

    @Override
    public void initView() {
        super.initView();
        mSwipeRefreshLayout.setColorSchemeColors(Constants.REFRESH_COLORS);

        mRecyclerViewComment.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerViewComment.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL, R.drawable.shape_divideline_order));
        mRecyclerViewComment.setAdapter(mCommentIngAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerViewComment.setOnLoadMoreListener(this);
    }

    @Override
    public void onRefresh() {
        page = 0;
        mAPIRequests.getComment(SPUserInfo.getsInstance(this).getSPInt(
                SPUserInfo.KEY_USERID), page);
    }
    @Override
    public void onLoadMore() {
        mAPIRequests.getComment(SPUserInfo.getsInstance(this).getSPInt(
                SPUserInfo.KEY_USERID), ++page);
    }
    @Override
    public void onPrepare(int taskId) {
    }

    @Override
    public void onEnd(int taskId) {
        super.onEnd(taskId);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerViewComment.stopLoadMore();
    }

    @Override
    public void onSuccess(int taskId, Object... params) {
        super.onSuccess(taskId, params);
        ResponseJson responseJson = (ResponseJson) params[0];
        switch (taskId) {
            case Tasks.GET_COMMENT:
                //得到评论
                List<Comment> listComment = JSON.parseArray(responseJson.getResult().toString(),
                        Comment.class);
                if (listComment == null) {
                    listComment = new ArrayList<Comment>();
                }
                if (page == 0) {
                    mCommentIngAdapter.setList(listComment);
                } else {
                    mCommentIngAdapter.addList(listComment);
                }
                mRecyclerViewComment.setLoadable(listComment.size() == 20);
                break;
        }
    }


}
