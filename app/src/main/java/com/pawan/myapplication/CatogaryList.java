package com.pawan.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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

public class CatogaryList extends AppCompatActivity {

    RecyclerView recyclerView;
    CategoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catogary_list);
        recyclerView=findViewById(R.id.categoryList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CatogaryList.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        getData();
    }

    private void getData() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url="https://www.themealdb.com/api/json/v1/1/categories.php";
        final ArrayList<CategoryModel> models=new ArrayList<>();
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray category=response.getJSONArray("categories");
                            for(int i=0;i<category.length();i++){
                                JSONObject object=category.getJSONObject(i);
                                String text=object.getString("strCategory");
                                String url=object.getString("strCategoryThumb");
                                models.add(new CategoryModel(url,text));
//                                Log.d("food","data added"+text+url);
                            }
                            adapter=new CategoryAdapter(CatogaryList.this,models,"CategoryList");
                            recyclerView.setAdapter(adapter);
//                            Log.d("food","adapter set");
                           progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(CatogaryList.this,"Please try after some time!",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(objectRequest);
    }
}
