package com.jph.xibaibai.model.utils;

import com.squareup.otto.Bus;

/**
 * 获取OttoBus的单例
 * Created by jph on 2015/8/12.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}