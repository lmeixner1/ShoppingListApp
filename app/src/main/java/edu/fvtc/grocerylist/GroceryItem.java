package edu.fvtc.grocerylist;

public class GroceryItem
{
    //Variables
    private Integer Id;
    private String Name;
    private Integer IsOnShoppingList;
    private Integer IsInCart;

    public GroceryItem(String name, int isOnShoppingList, int isInCart)
    {
        this.Name = name;
        this.IsOnShoppingList = isOnShoppingList;
        this.IsInCart = isInCart;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public Integer getIsOnShoppingList()
    {
        return IsOnShoppingList;
    }

    public void setIsOnShoppingList(int isOnShoppingList) {
        this.IsOnShoppingList = isOnShoppingList;
    }

    public Integer getIsInCart() {
        return IsInCart;
    }

    public void setIsInCart(Integer isInCart) {
        IsInCart = isInCart;
    }


    public String toString(){
        return Name + "|" + IsOnShoppingList + "|" + IsInCart;
    }
}

