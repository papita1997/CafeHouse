package com.pawan.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewItems extends AppCompatActivity {

    ImageView foodImg;
    TextView txtIngredient;
    TextView txtProcedure;
    TextView foodName;
    TextView txtAmount;
    TextView link;
    String idMeal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        foodImg=findViewById(R.id.foodimg);
        txtIngredient=findViewById(R.id.txtIngredient);
        txtProcedure=findViewById(R.id.txtProcedure);
        foodName=findViewById(R.id.foodname);
        txtAmount=findViewById(R.id.txtAmount);
        link=findViewById(R.id.link);
        Intent intent=getIntent();
        idMeal=intent.getStringExtra("idMeal");
        loadData();
    }

    private void loadData() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url="https://www.themealdb.com/api/json/v1/1/lookup.php?i="+idMeal;
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray meals=response.getJSONArray("meals");
                            JSONObject object=meals.getJSONObject(0);
                            Glide.with(ViewItems.this).load(object.getString("strMealThumb")).into(foodImg);
                            foodName.setText(object.getString("strMeal"));
                            String ingredients="";
                            String amount="";
                            for(int i=1;i<=20;i++){
                                String ingred=object.getString("strIngredient"+i);
                                String meas=object.getString("strMeasure"+i);
                                if(ingred.equals("")||meas.equals("")||ingred==null||meas==null) {
                                   break;
                                }
                                else{
                                    ingredients = ingredients+ingred +"\n\n";
                                    amount=amount+meas+"\n\n";
                                }
                            }
                            txtIngredient.setText(ingredients);
                            txtAmount.setText(amount);
                            String youtube="<a href='"+object.getString("strYoutube")+"'> click here for video</a>";
                            link.setClickable(true);
                            link.setMovementMethod(LinkMovementMethod.getInstance());
                            link.setText(Html.fromHtml(youtube));
                            String procedure=object.getString("strInstructions");
//                            Log.d("food",""+procedure);
                            txtProcedure.setText(procedure);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ViewItems.this,"Please try after some time!",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(ViewItems.this);
        requestQueue.add(objectRequest);
    }
}
