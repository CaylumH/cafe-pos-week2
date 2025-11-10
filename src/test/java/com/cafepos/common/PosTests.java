package com.cafepos.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.cafepos.menu.Menu;
import com.cafepos.menu.MenuComponent;
import com.cafepos.menu.MenuItem;
import com.cafepos.state.OrderFSM;

public class PosTests {

    @Test
    void depth_first_iteration_collects_all_nodes() {
        Menu root = new Menu("ROOT");
        Menu main = new Menu("Main");
        Menu sides = new Menu("Sides");

        root.add(main);
        root.add(sides);

        main.add(new MenuItem("Vegetarian Burger", Money.of(7.0), true));
        sides.add(new MenuItem("Chips", Money.of(3.0), false));
        List<String> names = root.allItems().stream().map(MenuComponent::name).toList();
        assertTrue(names.contains("Vegetarian Burger"));
        assertTrue(names.contains("Chips"));
    }

    @Test
    void order_fsm_happy_path() {
        OrderFSM fsm = new OrderFSM();
        assertEquals("NEW", fsm.status());
        fsm.pay();
        assertEquals("PREPARING", fsm.status());
        fsm.markReady();
        assertEquals("READY", fsm.status());
        fsm.deliver();
        assertEquals("DELIVERED", fsm.status());
    }

    @Test
    void illegal_transitions_do_not_change_state() {
        OrderFSM fsm = new OrderFSM();
        assertEquals("NEW", fsm.status());
        fsm.prepare();
        assertEquals("NEW", fsm.status());
        fsm.pay();
        assertEquals("PREPARING", fsm.status());
        fsm.deliver();
        assertEquals("PREPARING", fsm.status());
    }

    @Test
    void vegetarianItems_returns_only_veg_items() {
        Menu root = new Menu("ROOT");
        root.add(new MenuItem("Salad", Money.of(5.0), true));
        root.add(new MenuItem("Burger", Money.of(7.0), false));
        root.add(new MenuItem("Eggs", Money.of(5.0), true));
        List<MenuItem> vegItems = root.vegetarianItems();
        assertEquals(2, vegItems.size());
        for (MenuItem mi : vegItems) {
            assertTrue(mi.vegetarian());
        }
    }
}
