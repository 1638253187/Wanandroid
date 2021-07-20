package com.example.windqq.util;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.windqq.app.QQApp;
import com.example.windqq.bean.DaoCallBean;
import com.example.windqq.bean.DaoLocation;
import com.example.windqq.bean.DaoMsg;
import com.example.windqq.bean.DaoUserBean;
import com.example.windqq.dao.DaoCallBeanDao;
import com.example.windqq.dao.DaoLocationDao;
import com.example.windqq.dao.DaoMsgDao;
import com.example.windqq.dao.DaoUserBeanDao;

import java.util.ArrayList;
import java.util.List;

/**
 * DATE：2019/11/25
 * USER： WindHang
 * DESC：
 */

public class VoiceDbUtil {

    private static class VoiceDbUtilHolder {
        private static VoiceDbUtil instance = new VoiceDbUtil();
    }

    public static VoiceDbUtil getInstance() {
        return VoiceDbUtilHolder.instance;
    }

    private VoiceDbUtil() {
    }

    private DaoMsgDao getVoiceMsgDao() {
        return QQApp.getInstances().getDaoSession().getDaoMsgDao();
    }


    /*地址数据库*/
    public DaoLocationDao getDaoLocation() {
        return QQApp.getInstances().getDaoSession().getDaoLocationDao();
    }

    public DaoCallBeanDao getVoiceDaoCall() {
        return QQApp.getInstances().getDaoSession().getDaoCallBeanDao();
    }

    public DaoUserBeanDao getVoiceDaoUser() {
        return QQApp.getInstances().getDaoSession().getDaoUserBeanDao();
    }

    public DaoMsgDao getDaoMsgDao() {
        return QQApp.getInstances().getDaoSession().getDaoMsgDao();
    }

    public static SQLiteDatabase getSqlDb() {
        return QQApp.getInstances().getDb();
    }

    //增
    public boolean insert(DaoMsg msg) {
        boolean flag = false;
        try {
            getVoiceMsgDao().insertOrReplace(msg);
            flag = true;
        } catch (Exception e) {
            Log.e("kk", "insert: -----------" + e.getMessage());
        }
        return flag;
    }



    //增用户聊天记录
    public boolean insertUser(DaoCallBean msg) {
        boolean flag = false;
        try {
            getVoiceDaoCall().insertOrReplace(msg);
            flag = true;
        } catch (Exception e) {
            Log.e("kk", "insert: -----------" + e.getMessage());
        }
        return flag;
    }

    //删除所有聊天记录
    public boolean DeleteAll() {
        boolean flag = false;
        try {
            getDaoMsgDao().deleteAll();
            flag = true;
        } catch (Exception e) {
            Log.e("kk", "insert: -----------" + e.getMessage());
        }
        return flag;
    }

    public List<DaoLocation>  isHased(String daoLocation) {
        List<DaoLocation> list = getDaoLocation().queryBuilder().where(DaoLocationDao.Properties.UserId.eq(daoLocation)).list();
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /*增用户位置信息*/
    public boolean insertLocation(DaoLocation location) {
        boolean flag = false;
        try {
            getDaoLocation().insertOrReplace(location);
            flag = true;
        } catch (Exception e) {
            Log.e("kk", "insert: -----------" + e.getMessage());
        }
        return flag;
    }

    //增用户聊天
    public boolean insertUsers(DaoUserBean msg) {
        boolean flag = false;
        try {
            getVoiceDaoUser().insertOrReplace(msg);
            flag = true;
        } catch (Exception e) {
            Log.e("kk", "insert: -----------" + e.getMessage());
        }
        return flag;
    }

    /*
     * 插入多条数据对象
     * 可能会存在耗时 操作 所以new 一个线程
     * */
    public Boolean insertMultUser(final List<DaoMsg> msgs) {
        boolean flag = false;
        try {
            QQApp.getInstances().getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (DaoMsg msg : msgs) {
                        insert(msg);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return flag;
    }

    //根据条件删除聊天记录
    public boolean deleteMsg(String FromUser, String ToUser) {
        boolean flag = false;
        try {
            List<DaoMsg> list = getDaoMsgDao().queryBuilder().where(DaoMsgDao.Properties.Fromuser.eq(FromUser), DaoMsgDao.Properties.Touser.eq(ToUser)).list();
            for (DaoMsg dao : list) {
                Log.e("tagdao", "内容是:" + dao.getContent());
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //删
    public boolean delete(DaoUserBean msg) {
        boolean flag = false;
        try {
            getVoiceDaoUser().deleteAll();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /*删除聊天列表(仅删除双人聊天)*/
    public boolean DeleteGroupUser(int Type) {
        boolean flag = false;
        try {
            getVoiceDaoCall().queryBuilder().where(DaoCallBeanDao.Properties.Type.eq(Type)).buildDelete().executeDeleteWithoutDetachingEntities();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //删
    public boolean deleteUser(DaoCallBean bean) {
        boolean flag = false;
        try {
            getVoiceDaoCall().delete(bean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    //改

    public boolean update(DaoMsg msg) {
        boolean flag = false;
        try {
            getVoiceMsgDao().update(msg);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //未完成 实时修改
    public boolean updatescall(DaoCallBean msg) {
        boolean flag = false;
        try {
            getVoiceDaoCall().update(msg);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    //实时修改数据库位置
    public boolean updateLocation(List<DaoLocation> location) {
        boolean flag = false;
        try {
            getDaoLocation().updateInTx(location);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //查
    public List<DaoMsg> getAll(String fromuser, String touser) {
        List<DaoMsg> msgs = new ArrayList<>();
        Log.e("tag", "touser:" + touser);
        msgs = getVoiceMsgDao().queryBuilder().where(DaoMsgDao.Properties.Fromuser.eq(touser)).list();
        return msgs;
    }

    public List<DaoUserBean> getAll(int Size) {
        List<DaoUserBean> list = getVoiceDaoUser().queryBuilder().limit(Size).list();
        return list;
    }

    //根据ID获取用户位置
    public List<DaoLocation> getAllDaoLocation(String id) {
        List<DaoLocation> list = getDaoLocation().queryBuilder().where(DaoLocationDao.Properties.UserId.eq(id)).orderDesc(DaoLocationDao.Properties.Id).list();
        return list;
    }


    //根据ID获取用户位置
    public List<DaoLocation> getUserDaoLocation(String userId) {
        List<DaoLocation> msgs = new ArrayList<>();
        msgs = getDaoLocation().queryBuilder().where(DaoLocationDao.Properties.UserId.eq(userId)).list();
        return msgs;
    }

    //根据ID获取用户名
    public List<DaoUserBean> getUser(String userId) {
        List<DaoUserBean> msgs = new ArrayList<>();
        msgs = getVoiceDaoUser().queryBuilder().where(DaoUserBeanDao.Properties.UserId.eq(userId)).list();
        return msgs;
    }

    //根据用户名获取ID
    public List<DaoUserBean> getUserID(String userName) {
        List<DaoUserBean> msgs = new ArrayList<>();
        Log.e("tag", "fromuser:" + "touser:" + userName);
        msgs = getVoiceDaoUser().queryBuilder().where(DaoUserBeanDao.Properties.User.eq(userName)).list();
        return msgs;
    }

    public List<DaoCallBean> getAllUser(String touser, String fromuser, int Type) {
        List<DaoCallBean> daoCallBeans = getVoiceDaoCall().loadAll();
        return daoCallBeans;
    }

    public List<DaoCallBean> getGroupUser(int Type) {
        List<DaoCallBean> list = getVoiceDaoCall().queryBuilder().where(DaoCallBeanDao.Properties.Type.eq(Type)).list();
        return list;
    }

    /**
     * 分页加载  20条
     *
     * @param offset 页
     * @return
     */
    public List<DaoMsg> getTwentyMsg(int offset) {
        if (offset < 1) {
            throw new IllegalArgumentException("offset need input the number over zero");
        }
        List<DaoMsg> listMsg = getVoiceMsgDao().queryBuilder()
                .offset((offset - 1) * 20).limit(20).list();
        return listMsg;
    }

    /**
     * 类似于微信的从后面加载数据
     *
     * @param offset
     * @return
     */
    public List<DaoMsg> getWXTwentyMsg(int offset, String user, String myuser) {
        List<DaoMsg> msgs = new ArrayList<>();
        List<DaoMsg> voiceMsgs = getVoiceMsgDao().loadAll();
        int size = voiceMsgs.size();
        if (size > offset * 20) {
            Log.e("tag", "数据库的user:" + user);
            msgs = getVoiceMsgDao().queryBuilder().where(DaoMsgDao.Properties.Fromuser.eq(user), DaoMsgDao.Properties.Touser.eq(myuser)).offset(size - offset * 20).limit(20).list();
        } else if (size > (offset - 1) * 20) {
            msgs = getVoiceMsgDao().queryBuilder().where(DaoMsgDao.Properties.Fromuser.eq(user), DaoMsgDao.Properties.Touser.eq(myuser)).offset(0).limit(size - (offset - 1) * 20).list();
        }
        return msgs;
    }

    /**
     * 类似于微信的从后面加载数据
     *
     * @param offset
     * @return
     */
    public List<DaoCallBean> getWXTwentyuser(int offset) {
        List<DaoCallBean> msgs = new ArrayList<>();
        List<DaoCallBean> voiceMsgs = getVoiceDaoCall().loadAll();
        int size = voiceMsgs.size();
        if (size > offset * 20) {
            msgs = getVoiceDaoCall().queryBuilder().offset(size - offset * 20).limit(20).list();
        } else if (size > (offset - 1) * 20) {
            msgs = getVoiceDaoCall().queryBuilder().offset(0).limit(size - (offset - 1) * 20).list();
        }

        return msgs;
    }


}
