package main;

import java.util.ArrayList;

public class Inventory {
	private static final int MAX_INVENTORY_SIZE = 100;
	private ArrayList<Item> items;
	private boolean show = false;

	public Inventory() {
		items = new ArrayList<Item>();
		items.contains(MAX_INVENTORY_SIZE);
	}

	public void addItem(Item item) {
		if (items.size() < MAX_INVENTORY_SIZE) {
			items.add(item);
		} else {
			System.out.println("Inventory full");
		}
	}

	public void setVisible(boolean b) {
		show = b;
	}
	public boolean isVisible(){
		return show;
	}

	public int getInventorySize() {
		return MAX_INVENTORY_SIZE;
	}
	

	public void showInventory() {
		if (show) {
			// print items in array
			// TODO: make and draw menu
			for (int i = 0; i < items.size(); i++) {
				items.get(i).update();

			}
		}
	}

	public void showInventory(int x, int y, int width, int height) {
		if (show) {
			// print items in array
			// TODO: make and draw menu
			for (Item e : items) {
				e.setX(x);
				e.setY(y);
				e.setWidth(width);
				e.setHeight(height);
				e.update();

			}
		}		
	}

	public void remove() {
		// remove everything and turn off for now
		for (int i =0; i < items.size();i++){
			items.remove(i);
		}
		setVisible(false);
	}

}
