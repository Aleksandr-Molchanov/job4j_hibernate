package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Or;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class OrdersStoreTest {

    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void dropTable() throws SQLException{
        pool.getConnection().prepareStatement("DROP TABLE orders").executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenUpdateOrder() {
        OrdersStore store = new OrdersStore(pool);
        Order order = new Order(1, "name1", "description1", new Timestamp(System.currentTimeMillis()));
        store.save(order);
        Order orderNew = new Order(1, "name1", "description2", new Timestamp(System.currentTimeMillis()));
        store.update(orderNew);

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getId(), is(orderNew.getId()));
        assertThat(all.get(0).getName(), is(orderNew.getName()));
        assertThat(all.get(0).getDescription(), is(orderNew.getDescription()));
        assertThat(all.get(0).getCreated(), is(orderNew.getCreated()));
    }

    @Test
    public void whenFindById() {
        OrdersStore store = new OrdersStore(pool);
        Order order1 = new Order(1, "name1", "description1", new Timestamp(System.currentTimeMillis()));
        store.save(order1);
        Order order2 = new Order(2, "name2", "description2", new Timestamp(System.currentTimeMillis()));
        store.save(order2);

        Order rsl1 = store.findById(1);

        assertThat(rsl1.getId(), is(order1.getId()));
        assertThat(rsl1.getName(), is(order1.getName()));
        assertThat(rsl1.getDescription(), is(order1.getDescription()));
        assertThat(rsl1.getCreated(), is(order1.getCreated()));

        Order rsl2 = store.findById(2);

        assertThat(rsl2.getId(), is(order2.getId()));
        assertThat(rsl2.getName(), is(order2.getName()));
        assertThat(rsl2.getDescription(), is(order2.getDescription()));
        assertThat(rsl2.getCreated(), is(order2.getCreated()));
    }

    @Test
    public void whenFindByName() {
        OrdersStore store = new OrdersStore(pool);
        Order order1 = new Order(1, "name1", "description1", new Timestamp(System.currentTimeMillis()));
        store.save(order1);
        Order order2 = new Order(2, "name2", "description2", new Timestamp(System.currentTimeMillis()));
        store.save(order2);

        Order rsl1 = store.findByName("name1");

        assertThat(rsl1.getId(), is(order1.getId()));
        assertThat(rsl1.getName(), is(order1.getName()));
        assertThat(rsl1.getDescription(), is(order1.getDescription()));
        assertThat(rsl1.getCreated(), is(order1.getCreated()));

        Order rsl2 = store.findByName("name2");

        assertThat(rsl2.getId(), is(order2.getId()));
        assertThat(rsl2.getName(), is(order2.getName()));
        assertThat(rsl2.getDescription(), is(order2.getDescription()));
        assertThat(rsl2.getCreated(), is(order2.getCreated()));
    }
}