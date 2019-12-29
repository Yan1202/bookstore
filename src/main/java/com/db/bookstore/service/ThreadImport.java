package com.db.bookstore.service;

import com.db.bookstore.connection.OracleConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class ThreadImport {

    public void multiThreadImport( final int ThreadNum){
        final CountDownLatch cdl= new CountDownLatch(ThreadNum);
        long starttime=System.currentTimeMillis();
        for(int k=1;k<=ThreadNum;k++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Connection con=null;

                    try {
                        con= OracleConnection.getConnection();
                        Statement st=con.createStatement();
                        for(int i=1;i<=7200/ThreadNum;i++){
                            st.addBatch("call oracle.COMMIT_ORDER('114','66206',3)");
                            if(i%600==0){
                                st.executeBatch();
                            }
                        }
                        cdl.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }finally{
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        try {
            cdl.await();
            long spendtime=System.currentTimeMillis()-starttime;
            System.out.println( ThreadNum+"个线程花费时间:"+spendtime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) throws Exception {
        ThreadImport ti=new ThreadImport();
        ti.multiThreadImport(40);
        System.out.println("笔记本CPU数:"+Runtime.getRuntime().availableProcessors());
    }

}
