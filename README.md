DEPRECATED!
====================================

Swipe to dismiss feature is added to Android support library.
So, it would be better to use that implementation.

How to
------------------------------------
- Add to your project `build.gradle` file
```
dependencies {
    compile 'com.android.support:appcompat-v7:22.2.0'
    compile 'com.android.support:recyclerview-v7:22.2.0'
}
```
- In your project init `RecyclerView`, `LayoutManager`, `Adapter` and `ItemTouchHelper`
```
    // init recycler view
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    
    // init layout manager
    LayoutManager layoutManager = new LinearLayoutManager(this);
    // or 
    // LayoutManager layoutManager = new GridLayoutManager(this, 2);
    // or
    // LayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    
    // init data
    final List<String> items = getItems(); // implement #getItems method by yourself

    // init adapter
    RecyclerView.Adapter<CustomViewHolder> adapter = new RecyclerView.Adapter<CustomViewHolder>() {
        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // inflate your view here
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1
                        , viewGroup, false);
            // optionally, to support selectors (different 'colors' on touch/press)
            view.setBackgroundResource(android.R.drawable.list_selector_background);
            view.setClickable(true);
        
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder viewHolder, int i) {
            // populate view holder
            viewHolder.mTextView.setText(items.get(i));
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    };
    
    recyclerView.setAdapter(adapter);

    // init swipe to dismiss logic
    ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // callback for drag-n-drop, false to skip this feature
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // callback for swipe to dismiss, removing item from data and adapter
            items.remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    });
    swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerView);
```

That's it.

Android Swipe-to-Dismiss RecyclerView Sample Code
====================================

Sample code that shows how to make `RecyclerView` support the swipe-to-dismiss Android UI pattern.

See the original [Google+ post](https://plus.google.com/+RomanNurik/posts/Fgo1p5uWZLu) for discussion.

See also [Jake Wharton's port](https://github.com/JakeWharton/SwipeToDismissNOA) of this sample code to old versions of Android using the [NineOldAndroids](http://nineoldandroids.com/) compatibility library.

© Roman Nurik (original library)

© Vasya Drobushkov (recycler view support)
