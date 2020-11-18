# Objective

To Create RecyclerView for List of Products. This branch includes code for SingleVariant based products only.

# Task

* Create the two type of product item layouts as shown below

* Implement RecyclerView to view products list using RecyclerView & create its adapter

* Create binder class for buttons (as we created SingleVBProductViewBinder)

# APK

Download apk here

---

# Two types of layouts for products

## Single Variant based Product

* Layout for qty = 0

<img title="" src="images\single_vb_0.PNG" alt="single_vb_0.jpeg" width="319" border="5" data-align="center">

* Layout for qty > 0

<img title="" src="images\single_vb_gt0.PNG" alt="single_vb_gt0.jpeg" width="319" data-align="center">

    Increment button, Decrement button and qty textview are added to a group named **QtyGroup**. Either AddBtn or QtyGroup will be visible at any moment. This is one type of layout.

See layout XML [here](app\src\main\res\layout\product_item_single_vb.xml).

---

## Weight based / Multiple VB Product

* Weight based layout - qty = 0

<img title="" src="images\wb_0.PNG" alt="wb_0.jpeg" width="319" border="5" data-align="center">

* Weight based layout - qty > 0

<img title="" src="images\wb_gt0.PNG" alt="wb_gt0.jpeg" width="319" border="5" data-align="center">

* Multiple VB layout - qty = 0

<img title="" src="images\multiple_vb_0.PNG" alt="multiple_vb_0.jpeg" width="319" border="5" data-align="center">

* Multiple VB layout - qty > 0

<img title="" src="images\multiple_vb_gt0.PNG" alt="multiple_vb_gt0.jpeg" width="319" border="5" data-align="center">

    Here also, EditBtn & Qty TV are grouped together as **QtyGroup**. Except text of price TV and qty TV, everything is similar for WB & multiple VB products. So, this is another type of layout.

See layout XML [here](app/src/main/res/layout/product_item_wb_multi_vb.xml).
