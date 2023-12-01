package org.example;

import java.lang.reflect.Field;
import java.sql.*;

public class Requests {
    public static void createTable(Object obj) throws SQLException {
        try {
            Table table = obj.getClass().getAnnotation(Table.class);
            StringBuilder sql = new StringBuilder("CREATE TABLE " + table.title() + " (");
            StringBuilder sqlDEL = new StringBuilder("DROP TABLE IF EXISTS " + table.title() + ";");
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    sql.append(field.getName()).append(" ");

                    if (field.getType().getSimpleName().equals("int")) {
                        sql.append("INTEGER");
                    }
                    else {
                        sql.append("TEXT");
                    }
                    sql.append(",");
                }

            }

            sql.deleteCharAt(sql.length() - 1);
            sql.append(");");
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.bd")) {
                Statement statement = connection.createStatement();
                statement.execute(sqlDEL.toString());
                statement.execute(sql.toString());

                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet columns = metaData.getColumns(null, null, table.title(), null);

                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");

                    System.out.println("Колонка: " + columnName + ", Тип: " + columnType);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (NullPointerException e){
            System.out.println("Аннотация к таблице отсутствует");
        }
    }
    public static void insertIntoTable(Object obj) throws SQLException {
        try {
            String tableName = obj.getClass().getAnnotation(Table.class).title();
            Field[] fields = obj.getClass().getDeclaredFields();
            StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    query.append(field.getName()).append(",");
                }
            }
            query.deleteCharAt(query.length() - 1).append(") VALUES (");
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    try {
                        query.append("'").append(field.get(obj)).append("',");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            query.deleteCharAt(query.length() - 1).append(")");
            Connection connection = null;
            try{
                connection = DriverManager.getConnection("jdbc:sqlite:database.bd");
                Statement statement = connection.createStatement();
                statement.execute(query.toString());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                assert connection != null;
                connection.close();
            }
        }catch (NullPointerException e){
        }
    }
}

