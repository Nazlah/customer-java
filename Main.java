import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        String sql = "SELECT * FROM customers";
        List<Customer> customers = jdbcTemplate.query(sql, new CustomerMapper());
        customers.stream()
                .map(customer -> "ID: " + customer.getId() + ", Name: " + customer.getName() + ", Email: " + customer.getEmail())
                .forEach(System.out::println);
    }

    private static class CustomerMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
            Customer customer = new Customer();
            customer.setId(resultSet.getLong("id"));
            customer.setName(resultSet.getString("name"));
            customer.setEmail(resultSet.getString("email"));
            return customer;
        }
    }
}
