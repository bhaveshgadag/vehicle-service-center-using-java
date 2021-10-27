/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vehservicemgmt;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.*;
import java.util.Vector;

/**
 *
 * @author bhave
 */
public class dbconn {
    
    private static MysqlDataSource ds = null;

    public static MysqlDataSource getDataSource(String db_name) {
        if (ds == null) {
            // db variables set here
            String db_url = "localhost";
            String db_user = "root";
            String  db_password = "root";
            int db_port = 3306;
            getDataSource(db_url, db_user, db_password, db_port);
        }
        ds.setDatabaseName(db_name);
        return ds;
    }

    private static void getDataSource(String db_url, String db_user, String db_password, int db_port) {
        try {
            ds = new MysqlDataSource();
            ds.setServerName(db_url);
            ds.setUser(db_user);
            ds.setPassword(db_password);
            ds.setPort(db_port);
        } catch (Exception e) {
            System.out.println("MysqlDataSource err: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static Vector serviceDetails() throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT s.service_id, s.description,s.service_date,s.total_price, " +
                                "v.registration_no, c.customer_name,e.employee_name " +
                                "FROM service s, vehicle v, customer c, employee e " +
                                "WHERE s.vehicle_id=v.vehicle_id " +
                                "AND v.customer_id=c.customer_id " +
                                "AND s.employee_id=e.employee_id " +
                                "ORDER BY s.service_date DESC;");
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            return data;
            
        } catch (Exception e) {
            System.out.println("serviceDetails block: "+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector serviceDetailByID(int serviceID) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT s.service_id, s.description,s.service_date,s.distance,s.damages, " +
                                "v.registration_no, c.customer_name,e.employee_name " +
                                "FROM service s, vehicle v, customer c, employee e " +
                                "WHERE s.vehicle_id = v.vehicle_id " +
                                "AND v.customer_id = c.customer_id " +
                                "AND s.employee_id = e.employee_id " +
                                "AND s.service_id = ?");
            ps.setInt(1, serviceID);
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Object> vector = new Vector<Object>();
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
            }
            return vector;
        } catch (Exception e) {
            System.out.println("serviceDetails block: "+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void serviceInsert(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("INSERT INTO service(description,service_date,distance,damages,total_price,vehicle_id,employee_id) " +
                      "VALUES (?,?,?,?,?,?,?)");
            System.out.println(values.elementAt(1));
            ps.setString(1, (String)values.elementAt(1));
            ps.setString(2, (String)values.elementAt(0));
            ps.setString(3, (String)values.elementAt(2));
            ps.setString(4, (String)values.elementAt(3));
            ps.setString(5, (String)"0.0");
            ps.setLong(6, (Long)values.elementAt(4));
            ps.setInt(7, (int)values.elementAt(5));
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("serviceInsert block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector serviceVehicleSearch(int serviceID) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT v.vehicle_id,v.registration_no,v.company,v.model,v.vehicle_type " +
                                "FROM vehicle v,service s " +
                                "WHERE s.vehicle_id=v.vehicle_id " +
                                "AND s.service_id=?");
            ps.setInt(1, serviceID);
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Object> vector = new Vector<Object>();
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
            }
            return vector;
        } catch (Exception e) {
            System.out.println("serviceVehicleSearch block: "+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector serviceCustomerSearch(int serviceID) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT c.customer_id,c.customer_name,c.address,c.contact_no,c.email " +
                                "FROM customer c,vehicle v, service s " +
                                "WHERE s.vehicle_id=v.vehicle_id " +
                                "AND v.customer_id=c.customer_id " +
                                "AND s.service_id=?;");
            ps.setInt(1, serviceID);
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Object> vector = new Vector<Object>();
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
            }
            return vector;
        } catch (Exception e) {
            System.out.println("serviceCustomerSearch block: "+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector serviceSearch(int serviceID) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT * FROM service " +
                                "WHERE service_id=?");
            ps.setInt(1, serviceID);
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Object> vector = new Vector<Object>();
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
            }
            return vector;
        }
        catch(Exception e){
            System.out.println("serviceSearch"+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void serviceUpdate(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            System.out.println(values);
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("UPDATE  service SET description=?, service_date=?, distance=?, damages=?, vehicle_id=?, employee_id=? " +
              "WHERE service_id=?");
            ps.setString(1, (String)values.elementAt(1));
            ps.setString(2, (String)values.elementAt(0));
            ps.setString(3, (String)values.elementAt(2));
            ps.setString(4, (String)values.elementAt(3));
            ps.setLong(5, (Long)values.elementAt(4));
            ps.setInt(6, (int)values.elementAt(5));
            ps.setInt(7, (int)values.elementAt(6));
            System.out.println("serviceUpdate "+ ps);
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("serviceUpdate block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void serviceDelete(int serviceID) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("DELETE FROM service WHERE service_id = ? ");
            ps.setInt(1, serviceID);
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("serviceDelete block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector serviceEmployeeSearch(int serviceID) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT e.employee_name,e.employee_contact_no " +
                                "FROM employee e,service s " +
                                "WHERE s.employee_id=e.employee_id " +
                                "AND s.service_id=?");
            ps.setInt(1, serviceID);
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Object> vector = new Vector<Object>();
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
            }
            return vector;
        } catch (Exception e) {
            System.out.println("serviceEmployeeSearch block: "+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void serviceUpdateTotal(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            System.out.println(values);
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("UPDATE service SET total_price = ? WHERE service_id=? ");
            ps.setDouble(1, (double)values.elementAt(0));
            ps.setInt(2, (int)values.elementAt(1));
            System.out.println(ps);
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("serviceUpdateTotal block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void partsUsedInsert(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            System.out.println(values);
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("INSERT INTO parts_used(Service_service_id,Parts_part_id,quantity) " +
              "values(?,?,?)");
            ps.setInt(1, (int)values.elementAt(0));
            ps.setInt(2, (int)values.elementAt(1));
            ps.setString(3, (String)values.elementAt(2));
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("partsUsedInsert block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void partsUsedDelete(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            System.out.println(values);
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("DELETE FROM parts_used WHERE Service_service_id=? AND Parts_part_id=? ");
            ps.setInt(1, (int)values.elementAt(0));
            ps.setInt(2, (int)values.elementAt(1));
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("partsUsedDelete block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector partUsedDetails(int serviceID) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT pu.Parts_part_id,p.part_name,p.part_price,pu.quantity,(p.part_price*pu.quantity) Amount " +
              "FROM parts_used pu ,parts p ,service s " +
              "WHERE pu.Parts_part_id=p.part_id " +
              "AND pu.Service_service_id=s.service_id  " +
              "AND s.service_id=?");
            ps.setInt(1, serviceID);
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            return data;
            
        } catch (Exception e) {
            System.out.println("partUsedDetails block: "+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector custDetails() throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT * FROM customer");
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            return data;
            
        } catch (Exception e) {
            System.out.println("custDetails block: "+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector custSearch(int cID) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT * FROM customer " +
                                "WHERE customer_id=? ");
            ps.setInt(1, cID);
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Object> vector = new Vector<Object>();
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
            }
            return vector;
        }
        catch(Exception e){
            System.out.println("custSearch"+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void custInsert(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("INSERT INTO customer(customer_name,address,contact_no,email) VALUES (?,?,?,?) " );
            ps.setString(1, (String)values.elementAt(0));
            ps.setString(2, (String)values.elementAt(1));
            ps.setString(3, (String)values.elementAt(2));
            ps.setString(4, (String)values.elementAt(3));
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("custInsert block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void custUpdate(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("UPDATE customer SET customer_name=?, address=?,contact_no=?,email=? WHERE customer_id=?");
            ps.setString(1, (String)values.elementAt(0));
            ps.setString(2, (String)values.elementAt(1));
            ps.setString(3, (String)values.elementAt(2));
            ps.setString(4, (String)values.elementAt(3));
            ps.setInt(5, (int)values.elementAt(4));
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("custUpdate block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void custDelete(int custID) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("DELETE FROM customer WHERE customer_id = ? ");
            ps.setInt(1, custID);
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("custDelete block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector vehDetails() throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT A.vehicle_id,A.registration_no,A.company,A.model," +
                                "A.vehicle_type,A.transmission,B.customer_name " +
                                "from `vehicle` AS A " +
                                "LEFT JOIN `customer` AS B " +
                                "ON B.`customer_id` = A.`customer_id`;");
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            return data;
            
        } catch (Exception e) {
            System.out.println("custDetails block: "+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector vehSearch(long vID) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT * FROM vehicle " +
                                "WHERE vehicle_id=? ");
            ps.setLong(1, vID);
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Object> vector = new Vector<Object>();
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
            }
            return vector;
        }
        catch(Exception e){
            System.out.println("vehSearch"+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void vehInsert(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            System.out.println(values);
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("INSERT INTO vehicle(registration_no,company,model,vehicle_type,transmission,customer_id)"
                    + " VALUES (?,?,?,?,?,?);" );
            ps.setString(1, (String)values.elementAt(0));
            ps.setString(2, (String)values.elementAt(1));
            ps.setString(3, (String)values.elementAt(2));
            ps.setString(4, (String)values.elementAt(3));
            ps.setString(5, (String)values.elementAt(4));
            ps.setInt(6, (int)values.elementAt(5));
            System.out.println(ps);
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("vehInsert block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void vehUpdate(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("UPDATE vehicle " +
              "SET registration_no=?,company=?,model=?,vehicle_type=?,transmission=?,customer_id=? " +
              "WHERE vehicle_id=?");
            ps.setString(1, (String)values.elementAt(0));
            ps.setString(2, (String)values.elementAt(1));
            ps.setString(3, (String)values.elementAt(2));
            ps.setString(4, (String)values.elementAt(3));
            ps.setString(5, (String)values.elementAt(4));
            ps.setInt(6, (int)values.elementAt(5));
            ps.setLong(7, (Long)values.elementAt(6));
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("vehUpdate block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void vehDelete(Long vehID) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("DELETE FROM vehicle WHERE vehicle_id = ? ");
            ps.setLong(1, vehID);
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("vehDelete block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector vehDetailsByCustomer(int index) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT * FROM vehicle WHERE customer_id = ?");
            ps.setInt(1, index);
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            return data;
            
        } catch (Exception e) {
            System.out.println("vehDetailsByCustomer block: "+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector partDetails() throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT * FROM parts");
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            return data;
            
        } catch (Exception e) {
            System.out.println("partDetails block: "+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void partInsert(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("INSERT INTO parts(part_name, part_price, brand) VALUES(?,?,?)");
            ps.setString(1, (String)values.elementAt(0));
            ps.setString(2, (String)values.elementAt(1));
            ps.setString(3, (String)values.elementAt(2));
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("partInsert block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector partSearch(int pid) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT * FROM parts " +
                                "WHERE part_id=? ");
            ps.setInt(1, pid);
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Object> vector = new Vector<Object>();
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
            }
            return vector;
        }
        catch(Exception e){
            System.out.println("partSearch"+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void partUpdate(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("UPDATE parts " +
              "SET part_name=?, brand=?, part_price=? " +
              "WHERE part_id=?");
            ps.setString(1, (String)values.elementAt(0));
            ps.setString(2, (String)values.elementAt(1));
            ps.setString(3, (String)values.elementAt(2));
            ps.setInt(4, (int)values.elementAt(3));
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("partUpdate block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void partDelete(int pid) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("DELETE FROM parts WHERE part_id = ? ");
            ps.setInt(1, pid);
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("partDelete block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector empDetails() throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT * FROM employee");
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            return data;
            
        } catch (Exception e) {
            System.out.println("empDetails block: "+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static Vector empSearch(int empid) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbconn.getDataSource("service_management").getConnection();
            ps = conn.prepareStatement("SELECT * FROM employee " +
                                "WHERE employee_id=? ");
            ps.setInt(1, empid);
            ResultSet rs =  ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Object> vector = new Vector<Object>();
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
            }
            return vector;
        }
        catch(Exception e){
            System.out.println("empSearch"+e);
            return null;
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void empInsert(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("INSERT INTO employee(employee_name, employee_address, " +
              "employee_contact_no, date_of_joining, salary) " +
              "VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, (String)values.elementAt(0));
            ps.setString(2, (String)values.elementAt(1));
            ps.setString(3, (String)values.elementAt(2));
            ps.setString(4, (String)values.elementAt(3));
            ps.setString(5, (String)values.elementAt(4));
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("empInsert block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void empUpdate(Vector values) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("UPDATE employee " +
              "SET employee_name=?, employee_address=?, " +
              "employee_contact_no=?, date_of_joining=?, salary=? " +
              "WHERE employee_id=?");
            ps.setString(1, (String)values.elementAt(0));
            ps.setString(2, (String)values.elementAt(1));
            ps.setString(3, (String)values.elementAt(2));
            ps.setString(4, (String)values.elementAt(3));
            ps.setString(5, (String)values.elementAt(4));
            ps.setInt(6, (int)values.elementAt(5));
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("empUpdate block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
    
    public static void empDelete(int empid) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = getDataSource("service_management").getConnection();
            ps =conn.prepareStatement("DELETE FROM employee WHERE employee_id = ? ");
            ps.setInt(1, empid);
            ps.executeUpdate();            
        }
        catch(Exception e){
            System.out.println("empDelete block :"+e);
        }
        finally{
            ps.close();
            conn.close();
        }
    }
}