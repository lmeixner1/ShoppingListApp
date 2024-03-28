package edu.fvtc.grocerylist;

public class GroceryItem
{
    //Variables
    private int id;
    private String Description;
    private Integer IsOnShoppingList;
    private Integer IsInCart;

    public GroceryItem()
    {
        this.id = -1;
        this.Description = "";
        this.IsOnShoppingList = 0;
        this.IsInCart = 0;

    }
    public GroceryItem(int id, String description, int isOnShoppingList, int isInCart)
    {
        this.id = id;
        this.Description = description;
        this.IsOnShoppingList = isOnShoppingList;
        this.IsInCart = isInCart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    public Integer getIsOnShoppingList()
    {
        return IsOnShoppingList;
    }

    public void setIsOnShoppingList(int isOnShoppingList) {IsOnShoppingList = isOnShoppingList;}

    public Integer getIsInCart() {
        return IsInCart;
    }

    public void setIsInCart(Integer isInCart) {
        IsInCart = isInCart;
    }


    public String toString(){
        return id+ "|"+ Description + "|" + IsOnShoppingList + "|" + IsInCart;
    }


}

