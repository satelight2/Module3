package respository;

import util.Column;
import util.Id;
import util.MySQLConnect;
import util.Table;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Repository<T,K> implements  IRepository<T,K>{
    @Override
    public List<T> findAll(Class<T> tClass) {
        List<T> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = MySQLConnect.open();
            String sql = "SELECT * FROM " +tClass.getAnnotation(Table.class).name();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                T entity = tClass.getDeclaredConstructor().newInstance(); // tao moi 1 doi tuong entity
                Field[] fields = tClass.getDeclaredFields(); // lay tat ca cac truong cua entity
                for (Field field : fields){
                    field.setAccessible(true);
                    //field.set(entity,rs.getObject(field.getName()));  // set gia tri cho entity
                    field.set(entity,rs.getObject(field.getAnnotation(Column.class).name()));
                }
                list.add(entity);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MySQLConnect.close(conn);
        }
        return list;
    }

    @Override
    public T findById(K id, Class<T> tClass) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Field[] fields = tClass.getDeclaredFields(); // lay tat ca cac truong cua entity
            conn = MySQLConnect.open();
            //String fieldID = Arrays.stream(fields).filter(f -> f.getAnnotation(Column.class).name().equals("id")).findFirst().get().getName();
            String fieldID = Arrays.stream(fields).filter(f -> f.getAnnotation(Id.class) != null).findFirst().get().getAnnotation(Column.class).name();
            //String sql = "SELECT * FROM " +tClass.getAnnotation(Table.class).name();
            String sql = MessageFormat.format("SELECT * FROM {0} WHERE {1} = ?",tClass.getAnnotation(Table.class).name(),fieldID);
            ps = conn.prepareStatement(sql);
            ps.setObject(1,id);
            rs = ps.executeQuery();
            while (rs.next()){
                T entity = tClass.getDeclaredConstructor().newInstance(); // tao moi 1 doi tuong entity

                for (Field field : fields){
                    field.setAccessible(true);
                    //field.set(entity,rs.getObject(field.getName()));  // set gia tri cho entity
                    field.set(entity,rs.getObject(field.getAnnotation(Column.class).name()));
                }
                return entity;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MySQLConnect.close(conn);
        }
        return null;
    }

    @Override
    public T add(T entity) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = MySQLConnect.open();
            Field[] fields = entity.getClass().getDeclaredFields(); // lay tat ca cac truong cua entity
            String sql = "INSERT INTO " +entity.getClass().getAnnotation(Table.class).name() + " (";
            String values = " VALUES (";
            for (Field field : fields){
                field.setAccessible(true);
//                if (field.getAnnotation(Id.class) != null){ // neu la khoa chinh thi bo qua
//                    continue;
//                }
                sql += field.getAnnotation(Column.class).name() + ",";
                values += "?,";
            }
            sql = sql.substring(0,sql.length()-1) + ")";
            values = values.substring(0,values.length()-1) + ")";
            sql += values;
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            int i = 1;
            for (Field field : fields){
                field.setAccessible(true);
//                if (field.getAnnotation(Id.class) != null){
//                    continue;
//                }
                ps.setObject(i++,field.get(entity));
            }
            ps.executeUpdate();
            return entity;


        } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            finally {
                MySQLConnect.close(conn);
            }
            return null;
        }

        @Override
    public T edit(T entity) {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = MySQLConnect.open();
                Field[] fields = entity.getClass().getDeclaredFields(); // lay tat ca cac truong cua entity
                String sql = "UPDATE " +entity.getClass().getAnnotation(Table.class).name() + " SET ";
                String fieldID = Arrays.stream(fields).filter(f -> f.getAnnotation(Id.class) != null).findFirst().get().getAnnotation(Column.class).name();
                for (Field field : fields){
                    field.setAccessible(true);
//                    if (field.getAnnotation(Id.class) != null){ // neu la khoa chinh thi bo qua
//                        continue;
//                    }
                    sql += field.getAnnotation(Column.class).name() + " = ?,";
                }
                sql = sql.substring(0,sql.length()-1) + " WHERE " + fieldID + " = ?";
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                int i = 1;
                for (Field field : fields){
                    field.setAccessible(true);
//                    if (field.getAnnotation(Id.class) != null){
//                        continue;
//                    }
                    ps.setObject(i++,field.get(entity));
                }
                ps.setObject(i,fields[0].get(entity));
                ps.executeUpdate();
                return entity;

        } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                finally {
                    MySQLConnect.close(conn);
                }
                return null;
            }

            @Override
    public boolean remove(K id,Class<T> tClass) {

                Connection conn = null;
                PreparedStatement ps = null;
                try {

                    conn = MySQLConnect.open();
                    Field[] fields = tClass.getDeclaredFields(); // lay tat ca cac truong cua entity
                    System.out.println("fields = " + fields);
                    String fieldID = Arrays.stream(fields).filter(f -> f.getAnnotation(Id.class) != null).findFirst().get().getAnnotation(Column.class).name();
                    System.out.println("fieldID = " + fieldID);
                    String sql = "DELETE FROM " +tClass.getAnnotation(Table.class).name() + " WHERE " + fieldID + " = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setObject(1,id);
                    System.out.println("xóa thành công id = " + id);
                    return ps.executeUpdate() > 0;

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                finally {
                    MySQLConnect.close(conn);
                }
                return false;

    }
}
