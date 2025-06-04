package com.restaurant.core;

import java.util.List;

public interface MenuItem {
	String getName();
    double getPrice();
    List<String> ingredients();
	
}
