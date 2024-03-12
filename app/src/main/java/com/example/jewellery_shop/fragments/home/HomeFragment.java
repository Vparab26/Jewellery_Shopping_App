package com.example.jewellery_shop.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.jewellery_shop.R;
import com.example.jewellery_shop.adapters.CategoryAdapter;
import com.example.jewellery_shop.adapters.NewProductsAdapter;
import com.example.jewellery_shop.databinding.FragmentHomeBinding;
import com.example.jewellery_shop.models.CategoryModel;
import com.example.jewellery_shop.models.HomeViewModel;
import com.example.jewellery_shop.models.NewProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    CategoryAdapter categoryAdapter;
    RecyclerView catRecyclerView,newProductRecyclerview;
    List<CategoryModel> categoryModelList;
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;
    FirebaseFirestore db;

   public HomeFragment(){

   }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        catRecyclerView=root.findViewById(R.id.rec_category);
        newProductRecyclerview=root.findViewById(R.id.new_product_rec);

        db=FirebaseFirestore.getInstance();

        ImageSlider imageSlider=root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels =new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner11,"20% Discount on Earings", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner22,"New Collection - Mangalsutra", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner33,"15% Discount on Bangles", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);

        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList=new ArrayList<>();
        categoryAdapter =new CategoryAdapter(getContext(),categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);


        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel=document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //new Products
        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsModelList=new ArrayList<>();
        newProductsAdapter =new NewProductsAdapter(getContext(),newProductsModelList);
        newProductRecyclerview.setAdapter(newProductsAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductsModel newProductsModel=document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;

    }
}