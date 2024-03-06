package edu.fvtc.grocerylist;

public class GroceryItem
{
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    private int Id;
    private String Description;

    public Integer getOnShoppingList(Integer isOnShoppingList) {
        return IsOnShoppingList;
    }

    public void setOnShoppingList(Integer isOnShoppingList) {
        IsOnShoppingList = isOnShoppingList;
    }

    private int IsOnShoppingList;

    public Integer getIsInCart() {
        return IsInCart;
    }

    public void setIsInCart(Integer isInCart) {
        IsInCart = isInCart;
    }

    private int IsInCart;


    public GroceryItem(String description, int isOnShoppingList, int isInCart)
    {
        this.Description = description;
        this.IsOnShoppingList = isOnShoppingList;
        this.IsInCart = isInCart;
    }

    public String toString(){
        return Description + "|" + IsOnShoppingList + "|" + IsInCart;
    }
}

