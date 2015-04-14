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

	public void removeItem(String item) {
		// crude removal
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).itemName.equals(item)) {
				items.remove(i);
				break;
			}
		}
	}

	public void setVisible(boolean b) {
		show = b;
	}

	public Item getItem(String item) {
		// crude search
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).itemName.equals(item)) {
				return items.get(i);
			}
		}
		return null;
	}

	public int getInventorySize() {
		return MAX_INVENTORY_SIZE;
	}

	public void showInventory() {
		if (show) {
			// print items in array
			// TODO: make and draw menu
			for (int i = 0; i < items.size(); i++) {
				items.get(i).draw();

			}
		}
	}

}
