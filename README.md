[>>> Download APK here <<<](https://github.com/lswarnkar1/NAAD-Sessions-Practice/raw/ecom_admin_0.2/app/build/outputs/apk/debug/app-debug.apk)

# What we are building

## Admin eCom

* CatalogEditor / InventoryManagement (Products Add/Edit/Remove)

## User eCom

* List of products  

---

## We have 2 type of products :

1. WEIGHT_BASED (PricePerKg, MinQty)  
2. VARIANTS_BASED (List<Variant>)  
   
   Variants Class (Name, Price)

---

## Implementing Product Class

##### Approach 1 (Using Inheritance)

- class Product (Name)  

- class WeightBasedProduct extends Product (PricePerKg, MinQty)  

- class VariantsBasedProduct extends Product (List of Variant)

#### Approach 2 (Single class) (We are going with)

* class Product (ProductType, Name, PricePerKg, MinQty, List Variant)

---

## Hybrid RecyclerView

* We have two types of products, so we have create two layouts, two viewHolders & two binding classes  

* Methods we have to use
  
  * getItemViewType() : position -> type  
  
  * onCreateViewHolder(type) : Inflate View & return VH based on type
  
  * onBindViewHolder : Bind data to view based on type
