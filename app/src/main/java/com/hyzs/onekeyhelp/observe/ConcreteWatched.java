package com.hyzs.onekeyhelp.observe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wubin on 2017/4/17.
 */

public class ConcreteWatched implements Watched {
    // 定义一个List来封装Watcher
    private List<Watcher> list = new ArrayList<Watcher>();
    @Override
    public void add(Watcher watcher) {
        list.add(watcher);
    }
    @Override
    public void remove(Watcher watcher) {
        list.remove(watcher);
    }
    @Override
    public void notifyWatcher(Content content) {
        for (Watcher watcher : list) {
            watcher.updateNotify(content);
        }
    }
}