package com.example.dc.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by 戴超 on 2017/10/24.
 *
 *
 * 数据库帮助类
 */

public class DatabaseManager {

    private DaoSession mDaoSession = null;
    //用户表实例
    private UserProfileDao mDao = null;

    private DatabaseManager() {
    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    //静态内部类 实现单例
    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }


    /** 初始化 数据库
     * @param context
     */
    private void  initDao(Context context){

        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context,"fast_dc.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mDao = mDaoSession.getUserProfileDao();

    }


    public final UserProfileDao getDao() {
        return mDao;
    }



}




