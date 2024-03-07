package edu.fvtc.grocerylist;

public class GroceryItem
{
    //Variables
    private Integer Id;
    private String Description;
    private Integer IsOnShoppingList;
    private Integer IsInCart;

    public GroceryItem(String description, int isOnShoppingList, int isInCart)
    {
        this.Description = description;
        this.IsOnShoppingList = isOnShoppingList;
        this.IsInCart = isInCart;
    }

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
        return Description + "|" + IsOnShoppingList + "|" + IsInCart;
    }


}

