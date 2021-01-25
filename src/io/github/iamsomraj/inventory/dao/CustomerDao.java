package io.github.iamsomraj.inventory.dao;

import java.sql.*;

import io.github.iamsomraj.inventory.database.DatabaseUtil;
import io.github.iamsomraj.inventory.model.Customer;

public class CustomerDao {

	Connection conn = DatabaseUtil.createConnection();
	private static String tableName = "customers".toUpperCase();

	public void init() {
		dropCustomer();
		createCustomer();
	}

	public void createCustomer() {
		try {
			PreparedStatement statement = conn.prepareStatement("" + "" + "create table customers(\r\n"
					+ "	id int(10) primary key, \r\n" + "    name varchar(20),\r\n" + "    homephone varchar(20),\r\n"
					+ "    cellphone varchar(20),\r\n" + "    workphone varchar(20),\r\n"
					+ "    street varchar(20),\r\n" + "    city varchar(20),\r\n" + "    state varchar(20),\r\n"
					+ "    zip varchar(20)\r\n" + ");" + "");
			statement.executeUpdate();
			System.out.println(tableName + " created!");

		} catch (Exception e) {
			System.out.println("Failed to create " + tableName);
		}
	}

	public void dropCustomer() {
		try {
			PreparedStatement statement = conn.prepareStatement("drop table " + tableName);
			statement.executeUpdate();
			System.out.println(tableName + " dropped!");
		} catch (Exception e) {
			System.out.println("Failed to drop " + tableName);
		}
	}

	public void insertCustomer(Customer customer) {
		try {
			PreparedStatement statement = conn
					.prepareStatement("insert into " + tableName + " values(?,?,?,?,?,?,?,?,?)");
			statement.setInt(1, customer.getId());
			statement.setString(2, customer.getName());
			statement.setString(3, customer.getHomePhone());
			statement.setString(4, customer.getCellPhone());
			statement.setString(5, customer.getWorkPhone());
			statement.setString(6, customer.getStreet());
			statement.setString(7, customer.getCity());
			statement.setString(8, customer.getState());
			statement.setString(9, customer.getZip());
			statement.executeUpdate();
			System.out.println(customer.getName() + " is inserted in " + tableName);
		} catch (Exception e) {
			System.out.println("Failed to insert in " + tableName);
		}

	}

	public Customer getCustomerById(int id) {
		try {
			Statement statement = conn.createStatement();
			String query = "select * from " + tableName + " where id=" + id;
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				Customer customer = new Customer(rs.getInt(1), rs.getString(2));
				customer.setPhoneNumbers(rs.getString(3), rs.getString(4), rs.getString(5));
				customer.setPrintingAddress(rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
				System.out.println("Customer with id " + id + ": ");
				System.out.println(customer);
				return customer;
			}
		} catch (Exception e) {
			System.out.println("Failed to fetch customer with id " + id + " in " + tableName);
		}
		return null;
	}

}
