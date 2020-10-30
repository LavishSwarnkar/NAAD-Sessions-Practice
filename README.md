This branch contains implementation code for ListView and RecyclerView

Code for [this](https://codelabs.developers.google.com/codelabs/android-training-create-recycler-view/index.html?index=..%2F..%2Fandroid-training#0) Codelab of RecyclerView is also here.

**Checkout Various implementations of ListView in [this](https://github.com/lswarnkar1/NAAD-Sessions-Practice/blob/listview_recyclerview/app/src/main/java/com/lavish/android/practice/ListViewSampleActivity.java) Activity**

# 1. Simple ListView

- Easiest implementation
- Using List<String> and default itemView by Android

```java
private void setupSimpleListView() {
        //Data : List of Strings
        List<String> cities = new ArrayList<>(
                Arrays.asList("Ahmadabad", "Jaipur", "Surat", "Bombay", "Ahmadabad", "Jaipur"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay")
        );

        //Create Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this  /*Context*/

                //Layout for each item
                , android.R.layout.simple_list_item_1

                //Data
                , cities);

        //Set adapter
        b.list.setAdapter(adapter);
}
```

---

# 2. Custom view ListView

- Provide custom layout for each item
- Supports only List<String>

```java
private void setupCustomViewListView() {
        //Data : List of Strings
        List<String> cities = new ArrayList<>(
                Arrays.asList("Ahmadabad", "Jaipur", "Surat", "Bombay", "Ahmadabad", "Jaipur"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay")
        );

        //Create Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this  /*Context*/

                //Layout for each item
                , R.layout.list_item_with_bg

                //Id for TextView in above layout
                , R.id.text

                //Data
                , cities);

        //Set adapter
        b.list.setAdapter(adapter);
}
```

---

# 3. Custom Adapter ListView

## Obsolete!! Use RecyclerView instead

- Create list of any object you want
- Custom view also supported
- Not efficient for large data sets (Use RECYCLER VIEW)

```java
private void setupCustomAdapterListView() {
        //Data : List of products
        List<Product> products = getProducts();

        //Create adapter
        ProductAdapter adapter = new ProductAdapter(this  /*Context*/
                , R.layout.list_item_product
                , products);

        //Set adapter to ListView
        b.list.setAdapter(adapter);
}
```

```java
public class ProductAdapter extends ArrayAdapter<Product> {

    private final Context context;
    private final List<Product> products;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> products) {
        super(context, resource, products);

        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //View Binding class
        ListItemProductBinding b;

        //No recyclable view available
        if(convertView == null){
            //View is not inflated yet

            b = ListItemProductBinding.inflate(LayoutInflater.from(context));
            convertView = b.getRoot();
        } else {
            //View is already inflated

            b = ListItemProductBinding.bind(convertView);
        }

        //Get data item
        Product product = products.get(position);

        //Bind data to view
        b.name.setText(product.name);
        b.price.setText("₹" + product.price);

        return convertView;
    }

}
```

---

# 4. RecyclerView

- Advanced & Flexible implementation of RecyclerView
- Learning Resources
  - [Study Material](https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-2-user-experience/lesson-4-user-interaction/4-5-c-recyclerview/4-5-c-recyclerview.html)
  - [CodeLab](https://codelabs.developers.google.com/codelabs/android-training-create-recycler-view/index.html?index=..%2F..%2Fandroid-training#0)
  - [Slide Deck](https://docs.google.com/presentation/d/1tLLYBSGl9d8nHc_88007kTOZvXdSY0oqIRF3APIgm34/edit#slide=id.g16d0713f7e_0_23)
  - [Article](https://guides.codepath.com/android/using-the-recyclerview)
  - [Video 1](https://www.youtube.com/watch?v=QW0o21ZjmDM&list=PLlyCyjh2pUe9wv-hU4my-Nen_SvXIzxGB&index=28), [Video 2](https://www.youtube.com/watch?v=552-7VGGYMM&list=PLlyCyjh2pUe9wv-hU4my-Nen_SvXIzxGB&index=29)

```java
private void setupRecyclerView() {
        //Create adapter
        ProductRecyclerAdapter adapter = new ProductRecyclerAdapter(this, getProducts());

        //Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        b.recyclerView.setLayoutManager(layoutManager);

        //Divider
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        b.recyclerView.addItemDecoration(itemDecor);

        //Set adapter
        b.recyclerView.setAdapter(adapter);
}
```

```java
public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<Product> products;

    public ProductRecyclerAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemProductBinding b = ListItemProductBinding.inflate(LayoutInflater.from(context)
                , parent
                , false);

        return new ViewHolder(b);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get data item
        Product product = products.get(position);

        //Bind data to view
        holder.b.name.setText(product.name);
        holder.b.price.setText("₹" + product.price);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ListItemProductBinding b;

        public ViewHolder(ListItemProductBinding b) {
            super(b.getRoot());
            this.b = b;
        }
    }

}
```
