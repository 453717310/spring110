package cn.bdqn.bean;

import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by dll on 2017/9/27.
 */

//@ContextConfiguration(locations = "classpath:spring-User.xml")
public class UserTest {
    @Resource
    //private static UserService userService;
    // 我们使用反射机制来 动态的给 Student对象赋值
    public static void main(String[] args) {
        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/testdb";
        String userName="root";
        String password="";

        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        //创建需要映射的实体类
        Object student=null;
        try {
            student=Class.forName("cn.bdqn.bean.Student").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //加载驱动
        try {
            Class.forName(driver);
            //获取连接
            connection= DriverManager.getConnection(url,userName,password);
            //书写sql语句
            String sql="select id,name,age from student where id=?";
            //给参数赋值
            ps=connection.prepareStatement(sql);
            ps.setInt(1,4);
            //获取查询结果
            rs= ps.executeQuery();
            while (rs.next()){
                //获取元数据的结果集
                ResultSetMetaData metaData = rs.getMetaData();
                int  count= metaData.getColumnCount();
                for (int i=1;i<=count;i++){
                    String name= metaData.getColumnName(i);  //获取字段名称
                    String type= metaData.getColumnTypeName(i);  //获取字段类型
                    /**
                     * 01.给我一个元数据的名称
                     * 02.我去实体类中找对应的setXxx（）
                     * 03.赋值的时候根据类型
                     */
                    String  method=  getMethod(name);
                    if (type.equals("INT")){
                        student.getClass().getMethod(method,Integer.class).invoke(student,rs.getInt(name));
                    }else if (type.equals("VARCHAR")){
                        student.getClass().getMethod(method,String.class).invoke(student,rs.getString(name));
                    }
                }
                Student stu= (Student) student;
                System.out.println(stu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {//释放资源
                rs.close();
                ps.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *  01.用户给我一个字段名称（数据库中）
     *  02.我们返回一个set+字段名  （首字母大写）
     *  @param  name 字段名称
     *  @return   返回的set方法名称
     */

    private  static  String   getMethod(String name){  //sid  setSid()
        return   "set"+name.substring(0,1).toUpperCase()+name.substring(1);
    }




}