package com.example.onlineshopp.Database;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
    //Doi ten database
    private  final String DATABASE="EcommerceDT";
//    tk sa cung dc
    private  final String US="hieu";
//    dung  ma da dat trc do neu khong cai thi ko chay dc
    private  final String PWD="hieulsdd123";


    //Doan nay thi lay ip cua may minh` nhe dung lenh ifconfig lay ipv4
    private  final String IP="";
    private ResultSet rs;
    public Connection conn;
    private  String TAG="ERROR";

    public Connection getConn(){
        Connection conn1=null;
        StrictMode.ThreadPolicy smode= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(smode);
        String ConnURL=null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            ConnURL="jdbc:jtds:sqlserver://"+IP+";databaseName="+DATABASE;
            conn1= DriverManager.getConnection(ConnURL,US,PWD);
            if(conn1!=null){
                Log.i("SUCCESS", "getConn: connect thanh cong");
                conn=conn1;
            }else{
                Log.e("ERROR", "getConn: connect khong  thanh cong toi "+IP);
            }
        }catch (SQLException e){
            Log.e(TAG+" Driver", "Không thể tải lớp Driver\n"+e.getMessage() );
        } catch (ClassNotFoundException e) {
            Log.e("ERROR2","Xuất hiện vấn đề truy cập trong khi tải! "+ e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR3", "Không thể khởi tạo Driver! "+e.getMessage());
        }

        return conn;
    }

    //Truy van sql
    public String Query(String field,String table){
        String sql= "SELECT "+field+" FROM "+table;
        Statement st=null;
        StringBuilder sbl=new StringBuilder();
        String result=null;
        try {
            if(conn!=null){
                st=conn.createStatement();
                rs=st.executeQuery(sql);
                ResultSetMetaData rsmd= rs.getMetaData();;
                int column=rsmd.getColumnCount();
                while(rs.next()){
                    for(int i=1;i<=column;i++){
                        sbl.append(rs.getString(i)).append("\t");
                    }
                    sbl.append("\n");
                }
                rs.close();
                st.close();
                result=sbl.toString();
            }
            conn.close();

        }catch (Exception e){
            Log.e(TAG,"Xuất hiện vấn đề truy cập trong khi tải!"+ e.getMessage());
        }
        return result;
    }

    ///Truy van cau lenh
    public String Query(String sql) {
        Statement st = null;
        StringBuilder sbl = new StringBuilder();
        String result = null;
        try {
            if (conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                ;
                int column = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= column; i++) {
                        sbl.append(rs.getString(i)).append("\t");
                    }
                    sbl.append("\n");
                }

                result = sbl.toString();
                rs.close();
                st.close();
                conn.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "Xuất hiện vấn đề truy cập trong khi tải!" + e.getMessage());
        }

        return result;
    }
}
