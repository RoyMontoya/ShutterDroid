package com.example.amado.shutterdroid;

import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;

import com.example.amado.shutterdroid.API.Image;
import com.example.amado.shutterdroid.API.ShutterStock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener {
    private ImagesAdapter mAdapter;
    private List<Image> mImages;
    private SearchView mSearchView;
    private List<Image> mPreviousImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImages = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter= new ImagesAdapter(this,mImages);
        recyclerView.setAdapter(mAdapter);

        ShutterStock.getRecent(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new Callback<List<Image>>() {
            @Override
            public void success(List<Image> images, Response response) {
                Log.d("MainActivity", "success");
                mImages.clear();
                mImages.addAll(images);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void updateImages(List<Image> images) {
        mImages.clear();
        mImages.addAll(images);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchView = (SearchView)menu.findItem(R.id.search_view).getActionView();
        mSearchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search_view), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d("MainActivity", "menu");
                mPreviousImages = new ArrayList<Image>(mImages);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                updateImages(mPreviousImages);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_view) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.clearFocus();
        ShutterStock.search(query, new Callback<List<Image>>() {
            @Override
            public void success(List<Image> images, Response response) {
             updateImages(images);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
