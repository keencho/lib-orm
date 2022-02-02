package com.keencho.lib.orm.support;

import java.util.List;

public class KcPage<T> {

    private long start;

    private long size;

    private long total;

    private boolean first;

    private boolean last;

    private List<T> data;

    public KcPage(int start, long size, long total, List<T> data) {
        this.start = start;
        this.size = size;
        this.total = total;
        this.data = data;

        if (start == 0) {
            this.first = true;
            this.last = false;
        }

        if (start + size >= total) {
            this.first = false;
            this.last = true;
        }
    }
}
