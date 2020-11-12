# Adding Views to LinearLayout in Java

```java
private void showVariants() {
        //Data
        List<Variant> variants = Arrays.asList(
                new Variant("ABC", 1)
                , new Variant("DEF", 2)
                , new Variant("GHI", 3)
                , new Variant("JKL", 4)
        );

        for(Variant variant : variants){
            //Inflate layout for each variant
            VariantItemBinding ib = VariantItemBinding.inflate(getLayoutInflater());

            //Bind data to view
            ib.variantName.setText(variant.toString());

            //Add view to LinearLayout
            b.list.addView(ib.getRoot());

            //OR : VariantItemBinding ib = VariantItemBinding.inflate(getLayoutInflater(), b.list, true);
        }
    }
```
