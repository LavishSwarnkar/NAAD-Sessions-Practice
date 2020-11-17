# Objective

To create Cart class for saving items to be ordered

# Task

* Create a new IntelliJ Project. 

* Create a Cart class based on following text and code in this branch. 

* In the main class, play with the cart 
  
  * Add new products
  
  * update qty 
  
  * look for bugs 

* Push the code and acknowledge on Discord.

---

# CART Class

## What User app gets from Server

- List < Product >

---

## When order is placed

- Ordered Items are to be sent to Admin app

---

## Need of CartItem class

- Product class has Several irrelevant information for Admin like MinQty, PricePerKg and info about all Variants.

Eg -

```json
"Apple" : {
    pricePerKg = 20
    , minQty = 2
    , qty = 3
    , price = 60
},

"Kiwi" : {
    Variants = {
        "100g" : {
            unitPrice = 20
            , qty = 0
            , price = 0
        },
        "200g" : {
            unitPrice = 40
            , qty = 2
            , price = 80
        },
    }
}
```

Instead of sending all this information including overhead, we can save bandwidth & storage by sending only relevant information like

```json
"Apple" : {
    qty = 3
    , price = 60
},

"Kiwi 200g" : {
    qty = 2
    , price = 80
}
```

So, we create a CartItem class with only these attributes :

- Name (String)

- Qty (float)

- Price (int)

---

## Need of Cart class

Along with List< CartItem >, we need a class to save additional information like

- Cart SubTotal (60 + 80 = 140 in above case) (int)

- Total no of items in cart (int)

These additional details will be shown as follows :
<img title="" src="need for cart class.jpeg" alt="need for cart class.jpeg" width="319" data-align="center">

---

## Using Map< String, CartItem > instead of List< CartItem >

When user updates qty, instead of linear search in list, we need a faster mechanism. So, for this, we'll use Map where key will be FullName (i.e. ProductName + VariantName)

Eg. (In our previous case) -

```json
//Weight based, only Product name is enough
"Apple" : {
    qty = 3
    , price = 60
},

//Variant based so (ProductName + VariantName)
"Kiwi 200g" : {
    qty = 2
    , price = 80
}, 
"Bread Small packet" : {
    qty = 2
    , price = 80
},
"Bread Big packet" : {
    qty = 3
    , price = 80
}
```

---

## Need of totalVariantsQty Map

- For Variant based (VB) products, qty for each variant is already saved in map (Values in Blue)

- But we also need to show total Qty of a VB Product (Value in Yellow)

<img title="" src="need for totalQty map.jpeg" alt="need for totalQty map.jpeg" width="237">  <img title="" src="need for totalQty map (zoomed in).jpeg" alt="need for totalQty map (zoomed in).jpeg" width="302">

To do so, we need an additional Map< String, Integer > to save totalQty of a VB Product like

```json
"Aashirvaad Aata" : 3
, "Bread" : 5
```

---

## Operations for VB Product in Cart

- Add to Cart : qty++

        Corresponding method in Cart :

```java
int addToCart(Product product, Variant variant)
```

- Remove from Cart : qty--

        Corresponding method in Cart :

```java
int removeFromCart(Product product, Variant variant)
```

---

## Operations for WB Product in Cart

- Update qty

        Corresponding method in Cart :

```java
void updateWBPQuantity(Product product, float qty)
```

Eg -

```json
null -> "1kg"
"1kg" -> "2kg 500g"
```

- Remove

        Corresponding method in Cart :

```java
void removeWBPFromCart(Product product)
```
