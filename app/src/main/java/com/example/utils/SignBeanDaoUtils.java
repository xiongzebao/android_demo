package com.example.utils;

 

import android.content.Context;
import android.util.Log;

import com.example.bean.DaoManager;
import com.example.bean.SignBean;
import com.example.bean.SignBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


public class SignBeanDaoUtils
{
    private static final String TAG = SignBeanDaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public SignBeanDaoUtils(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成meizi记录的插入，如果表未创建，先创建SignBean表
     * @param meizi
     * @return
     */
    public boolean insertSignBean(SignBean meizi){
        boolean flag = false;
        flag = mManager.getDaoSession().getSignBeanDao().insert(meizi) == -1 ? false : true;
        Log.i(TAG, "insert SignBean :" + flag + "-->" + meizi.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     * @param meiziList
     * @return
     */
    public boolean insertMultSignBean(final List<SignBean> meiziList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (SignBean meizi : meiziList) {
                        mManager.getDaoSession().insertOrReplace(meizi);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     * @param meizi
     * @return
     */
    public boolean updateSignBean(SignBean meizi){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(meizi);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     * @param meizi
     * @return
     */
    public boolean deleteSignBean(SignBean meizi){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(meizi);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(SignBean.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<SignBean> queryAllSignBean(){

        return mManager.getDaoSession().loadAll(SignBean.class);
    }
    public List<SignBean> queryByName(String name){

        return  querySignBeanByNativeSql("where name = ?",new String[]{name});
    }






    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public SignBean querySignBeanById(long key){

        return mManager.getDaoSession().load(SignBean.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<SignBean> querySignBeanByNativeSql(String sql, String[] conditions){
        return mManager.getDaoSession().queryRaw(SignBean.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     * @return
     */
    public List<SignBean> querySignBeanByQueryBuilder(long id){
        QueryBuilder<SignBean> queryBuilder = mManager.getDaoSession().queryBuilder(SignBean.class);
        return queryBuilder.where(SignBeanDao.Properties._id.eq(id)).list();
//        return queryBuilder.where(SignBeanDao.Properties._id.ge(id)).list();
    }
}