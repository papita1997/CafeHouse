package com.pawan.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pawan.myapplication.adapter.CategoryAdapter;
import com.pawan.myapplication.model.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleCategoryList extends AppCompatActivity {

    RecyclerView recyclerView;
    CategoryAdapter adapter;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_category_list);
        recyclerView=findViewById(R.id.singleCategoryList);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent=getIntent();
        category=intent.getStringExtra("categoryName");
        loadData();
    }

    private void loadData() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url="https://www.themealdb.com/api/json/v1/1/filter.php?c="+category;
        final ArrayList<CategoryModel> model=new ArrayList<>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray meals=response.getJSONArray("meals");
                            for(int i=0;i<meals.length();i++){
                                JSONObject object=meals.getJSONObject(i);
                                String name=object.getString("strMeal");
                                String url=object.getString("strMealThumb");
                                String mealId=object.getString("idMeal");
                                model.add(new CategoryModel(url,name,mealId));
                            }
                            adapter=new CategoryAdapter(SingleCategoryList.this,model,"SingleCategoryList");
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SingleCategoryList.this,"Please try after some time!",Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(SingleCategoryList.this);
        requestQueue.add(jsonObjectRequest);
    }
}
