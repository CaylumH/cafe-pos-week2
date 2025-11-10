package com.cafepos.menu;

import java.util.Iterator;

import com.cafepos.common.Money;

public abstract class MenuComponent {

    public void add(MenuComponent component) {
        throw new UnsupportedOperationException();
    }
    public void remove(MenuComponent component) {
        throw new UnsupportedOperationException();
    }
    public MenuComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    public String name() {
        throw new UnsupportedOperationException();
    }
    public Money price() {
        throw new UnsupportedOperationException();
    }
    public boolean vegetarian() {
        return false;
    }

    public Iterator<MenuComponent> iterator() {
        throw new UnsupportedOperationException();
    }
    public void print() {
        throw new UnsupportedOperationException();
    }
}