package io.codewithwinnie.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.codewithwinnie.shopping.dbutils.MySQLDatabaseConnection;
import io.codewithwinnie.shopping.domain.Order;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 17/08/2022
 */

public class OrderDao {
    private static final Logger LOG = Logger.getLogger(OrderDao.class.getName());

    public List<Order> getOrders(int userId) {
        List<Order> orders = new ArrayList<>();
        Connection con = null;
        try{
            con = MySQLDatabaseConnection.getConnectionToDatabase();
            try (PreparedStatement statement = con.prepareStatement("select * from orders where user_id=?")) {
                statement.setInt(1, userId);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Order order = new Order();
                    order.setUserId(rs.getInt("user_id"));
                    order.setId(rs.getInt("order_id"));
                    order.setNoOfItems(rs.getInt("no_of_items"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setDate(rs.getDate("order_date"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Could not execute Query", e);
        }
        return orders;
    }
}
