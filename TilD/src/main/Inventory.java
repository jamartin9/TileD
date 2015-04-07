package main;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Inventory 
{
	private static final int MAX_INVENTORY_SIZE = 100;
	
	private Array items[];

    private Item Item[] = new Item[MAX_INVENTORY_SIZE];

    public Inventory() {
    }

    public void addItem(Array item) {
          int slot = findFreeSlot();
          if (slot >= 0) {
              items[slot] = item;
          }
    }

    public Array removeItem(Item item) {
          for (int i=0;i<items.length;i++) {
               if (items[i].equals(item)) {
                     Array temp = items[i];
                     items[i] = null;
                     return temp;
               }
           }

           return null;
    }

    public Array getItemAt(int index) {
           return items[index];
    }

    public int getInventorySize() {
            return MAX_INVENTORY_SIZE;
    }

    private int findFreeSlot() {
         for (int i=0;i<items.length;i++) {
              if (items[i] == null) {
                   return i;
              }
         }

         return -1;
    }
    
    public void showInventory()
    {
        //print items in array
        for(int i=0;i<Item.length;i++)
        {
            //null is used for an empty spot
            if(Item[i]==null)
            {
                //+1 makes sure player choices matches up with menu player sees since array starts at 0
                System.out.println(i+1 + ": Empty Space");
            }
           
        }
    }

}
