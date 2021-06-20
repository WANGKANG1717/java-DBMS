package com.dbms.db;

import java.io.*;
import java.util.Arrays;

/**
 * 执行数据库的建立，删除、返回列表操作
 */
public class DB {
    protected static final File root = new File("src\\database");
    protected File dbFile;
    protected String dbName;

    public DB(String dbName) throws Exception {
        if (nameValid(dbName) == false) {
            throw new Exception("数据库名"+dbName+"无效");
        }
        this.dbName=dbName;
        dbFile = new File(root, dbName);
    }

    /**
     * 得到数据库系统中所有的数据库
     *
     * @return 列表
     */
    public static String[] getDBList() {
        return root.list();
    }

    /**
     * 返回当前库中的表名
     * @return
     */
    public String[] getTable() {
        return dbFile.list();
    }

    public boolean dbExists() {
        return dbFile.exists();
    }

    public boolean createDB() throws Exception {
        if (dbExists() == true) {
            throw new Exception("数据库"+dbName+"已经存在");
        }
        return dbFile.mkdir();
    }

    public boolean deleteDB() throws Exception {
        if (dbExists() == false) {
            throw new Exception("数据库"+dbName+"不存在");
        }
        return deleteFile(dbFile);
    }

    /**
     * 用来确保要建立的数据库、表、列名名称是有效的
     * 为此我这里将sql中的所有保留字都记录下来
     * 进行判断
     * 在我的数据库中
     * 一个有效的名字，不能和下面的保留字冲突
     * 不能以字母外的东西开头
     * 不能存在除了数字字母外的字符
     */
    protected static final String reservedWord_ = "add,all,alter,and,any,as,asc,authorization,avg,backup," +
            "begin,between,break,browse,bulk,by,cascade,case,check,checkpoint,close,clustered,coalesce," +
            "column,commit,committed,compute,confirm,constraint,contains,containstable,continue,controlrow," +
            "convert,count,create,cross,current,current_date,current_time,current_timestamp,current_user," +
            "cursor,database,dbcc,deallocate,declare,default,delete,deny,desc,disk,distinct,distributed," +
            "double,drop,dummy,dump,else,end,errlvl,errorexit,escape,except,exec,execute,exists,exit,fetch," +
            "file,fillfactor,floppy,for,foreign,freetext,freetexttable,from,full,goto,grant,group,having," +
            "holdlock,identity,identity_insert,identitycol,if,in,index,inner,insert,intersect,into,is," +
            "isolation,join,key,kill,left,level,like,lineno,load,max,min,mirrorexit,national," +
            "nocheck,nonclustered,not,null,nullif,of,off,offsets,on,once,only,open,opendatasource," +
            "openquery,openrowset,option,or,order,outer,over,percent,percision,perm,permanent,pipe,plan," +
            "prepare,primary,print,privileges,proc,procedure,processexit,public,raiserror,read,readtext," +
            "reconfigure,references,repeatable,replication,restore,restrict,return,revoke,right,rollback," +
            "rowcount,rowguidcol,rule,save,schema,select,serializable,session_user,set,setuser,shutdown,some," +
            "statistics,sum,system_user,table,tape,temp,temporary,textsize,then,to,top,tran,transaction,trigger," +
            "truncate,tsequal,uncommitted,union,unique,update,updatetext,use,user,values,varying,view,waitfor," +
            "when,where,while,with,work,writetext";

    protected static final String[] reservedWord=reservedWord_.split(",");

    protected static boolean nameValid(String name) {
        //不能为null或空
        if (name == null || name.length() == 0) return false;
            //开头只能是字母
        else if (Character.isLetter(name.charAt(0)) == false) return false;
        else {
            //检验是否存在非法字符
            for (int i = 0; i < name.length(); i++) {
                if (Character.isLetterOrDigit(name.charAt(i)) == false) return false;
            }
            //最后一步，查看是否是保留字
            if ((Arrays.asList(reservedWord).contains(name))==true) return false;
            else return true;
        }
    }

    protected static boolean deleteFile(File delFile) {
        String[] del = delFile.list();
        if (del != null) {
            for (int i = 0; i < del.length; i++) {
                File f = new File(delFile, del[i]);
                if (f.isFile()) f.delete();
                else deleteFile(f);
            }
        }
        return delFile.delete();
    }
}
