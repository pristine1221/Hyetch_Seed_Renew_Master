package com.example.pristineseed.ui.inspection.planting.createplanting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.DataBaseRepository.Planting.PlantingDetailHeaderTable;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingHeaderDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineTable;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;

import com.example.pristineseed.ui.adapter.PlantingAdapter.PlantingDetailExpendedListAdapter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantingDetailFragment  extends Fragment {
    private String planting_Number="";
    private ExpandableListView planting_detail_expanded_listview;
    private SessionManagement sessionManagement;
    private int row_per_page=400,page_number=0,total_rows=400;
    private Chip chp_add_planting_header;
    private int selectedGroupPosition;
    private boolean isloadmore =false;
    List<PlantingHeaderModel> plantingList = new ArrayList<>();
    private PlantingDetailExpendedListAdapter expandableListAdapter;
    private MaterialButton prevBtn,nextBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.planting_detail_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        initView(view);

        boolean isNetwork = NetworkUtil.getConnectivityStatusBoolean(getActivity());
        if (isNetwork) {
            getAllPlantingData();
        } else {
            Toast.makeText(getActivity(), "Please wait for internet connection !", Toast.LENGTH_SHORT).show();
        }

        chp_add_planting_header.setOnClickListener(v -> {
            AddPlantingHeaderLineDetailFragment addPlantingHeaderLineDetailFragment = new AddPlantingHeaderLineDetailFragment();
            StaticMethods.loadFragmentsWithBackStack(getActivity(), addPlantingHeaderLineDetailFragment, "add_planting_header_fragment");
        });

            planting_detail_expanded_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    int ChildCountWay1 = expandableListAdapter.getChildrenCount(groupPosition);
                        if (ChildCountWay1>0) {
                            planting_detail_expanded_listview.setOnChildClickListener((parent1, v1, gp, childPosition, id1) -> {
                                int index = parent1.getFlatListPosition(ExpandableListView.getPackedPositionForChild(gp, childPosition));
                                parent1.setItemChecked(index, true);
                                selectedGroupPosition = index;
                                Bundle bundle = new Bundle();
                                AddPlantingHeaderLineDetailFragment addPlantingHeaderLineDetailFragment = new AddPlantingHeaderLineDetailFragment();
                                bundle.putString("planting_list", new Gson().toJson(plantingList.get(gp)));
                                bundle.putString("view_flag", "view_header_line");
                                addPlantingHeaderLineDetailFragment.setArguments(bundle);
                                StaticMethods.loadFragmentsWithBackStack(getActivity(), addPlantingHeaderLineDetailFragment, "add_planting_header_fragment");
                                return false;
                            });

                        } else {
                            int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPosition));
                            parent.setItemChecked(index, true);
                            selectedGroupPosition = index;
                            Bundle bundle = new Bundle();
                            AddPlantingHeaderLineDetailFragment addPlantingHeaderLineDetailFragment = new AddPlantingHeaderLineDetailFragment();
                            bundle.putString("planting_list", new Gson().toJson(plantingList.get(groupPosition)));
                            bundle.putString("view_flag", "view_header_line");
                            addPlantingHeaderLineDetailFragment.setArguments(bundle);
                            StaticMethods.loadFragmentsWithBackStack(getActivity(), addPlantingHeaderLineDetailFragment, "add_planting_header_fragment");
                        }

                    return false;

                }
            });


        //NAVIGATE
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page_number += 1;
                toggleButtons();
                getAllPlantingData();

            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page_number -= 1;
                //  expandiblelistbind(page_number);
                toggleButtons();
            }
        });
    }

   /*   planting_detail_expanded_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
          @Override
          public void onScrollStateChanged(AbsListView view, int scrollState) {

          }

          @Override
          public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
              int lastInScreen = firstVisibleItem + visibleItemCount;

              if((lastInScreen == totalItemCount)){
                  if(isloadmore == false)
                  { isloadmore = true;
                      getAllPlantingData();
                  }
              }
          }
      });*/


    private void initView(View view) {
        planting_detail_expanded_listview=view.findViewById(R.id.planting_detail_list);
        chp_add_planting_header=view.findViewById(R.id.add_planting_header);
        prevBtn=view.findViewById(R.id.prevBtn);
        nextBtn=view.findViewById(R.id.nextBtn);
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void getAllPlantingData() {
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<PlantingHeaderModel>> call = mAPIService.getPlantingDetail(sessionManagement.getUserEmail(),row_per_page,page_number,total_rows);
        LoadingDialog progressDialogLoading=new LoadingDialog();
        progressDialogLoading.showLoadingDialog(getActivity());
        call.enqueue(new Callback<List<PlantingHeaderModel>>(){
            @Override
            public void onResponse(Call<List<PlantingHeaderModel>> call, Response<List<PlantingHeaderModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        List<PlantingHeaderModel> tempplantingList = response.body();
                        if (tempplantingList!=null && tempplantingList.size() > 0 && tempplantingList.get(0).condition) {
                            plantingList = tempplantingList;
                           //addMoreItem();
                            expandiblelistbind(page_number,tempplantingList);
                            //InsertPlanitngHeaderLine(tempplantingList);
                          } else {
                            Toast.makeText(getActivity(), "No data found ! ", Toast.LENGTH_SHORT).show();
                            progressDialogLoading.hideDialog();
                           }
                        } else {
                        progressDialogLoading.hideDialog();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                       }

                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "planting_inspection_fragment_", getActivity());
                }
                finally {
                    progressDialogLoading.hideDialog();
                }
            }
            @Override
            public void onFailure(Call<List<PlantingHeaderModel>> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "planting_inspection_fragment_", getActivity());
            }
        });
    }

    private void expandiblelistbind(int currentpage,List<PlantingHeaderModel> plantingList) {
       //  plantingList= getCurrentGalaxys(currentpage,plantingList);
        total_rows=plantingList.get(0).total_rows;
        expandableListAdapter = new PlantingDetailExpendedListAdapter(this.getActivity(),plantingList);
        planting_detail_expanded_listview.setAdapter(expandableListAdapter);
        planting_detail_expanded_listview.setOnGroupExpandListener(groupPosition -> {
            for (int i = 0; i < expandableListAdapter.getGroupCount(); i++) {
                if (i != groupPosition) {
                    planting_detail_expanded_listview.collapseGroup(i);
                }
            }
        });
    }

/*    private void InsertPlanitngHeaderLine(List<PlantingHeaderModel> tempPlantingList) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            PlantingHeaderDao plantingHeaderDao = db.plantingHeaderDao();
            PlantingLineDao plantingLineDao = db.plantingLineDao();
            for (int i = 0; i < tempPlantingList.size(); i++) {
                PlantingDetailHeaderTable planting_header_table = PlantingDetailHeaderTable.getPlantingHeaderData(tempPlantingList.get(i));

                if(plantingHeaderDao.isDataExist(planting_header_table.getCode())){
                    plantingHeaderDao.update(planting_header_table);
                }else {
                    plantingHeaderDao.insert(planting_header_table);
                }

                if(tempPlantingList.get(i).pl.get(0).code!=null){
                    for (int j = 0; j < tempPlantingList.get(i).pl.size(); j++) {
                        PlantingLineTable plantingLineTable = PlantingLineTable.getLineDataFormate(tempPlantingList.get(i).pl.get(j));
                        int count = plantingLineDao.isDataExist_on_line(plantingLineTable.getLine_no(), planting_header_table.getCode());//empPlantingList.get(i).pl.get(j).code
                        if (count <= 0) {
                            plantingLineDao.insert(plantingLineTable);
                        } else {
                            // PlantingLineTable plantingLineTable = PlantingLineTable.getLineDataFormate(tempPlantingList.get(i).pl.get(j));
                            plantingLineDao.update(plantingLineTable);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exc",e.getMessage());
        }
        finally {
            db.close();
            db.destroyInstance();
        }
    }

    private void getDataFromLocal() {
        plantingList.clear();
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            PlantingHeaderDao plantingHeaderDao = db.plantingHeaderDao();
            PlantingLineDao plantingLineDao = db.plantingLineDao();
            List<PlantingDetailHeaderTable> plantingDetailHeaderTableList = plantingHeaderDao.getAllData();
            for (int k = 0; k < plantingDetailHeaderTableList.size(); k++) {
                PlantingHeaderModel plantingHeaderModel = PlantingDetailHeaderTable.getServerFormat(plantingDetailHeaderTableList.get(k));
                //todo bind scheduler Line
                List<PlantingLineTable> plantingLineTableList = plantingLineDao.getAllDatabyPlantingCode(plantingHeaderModel.code);
                for (int i = 0; i < plantingLineTableList.size(); i++) {
                    plantingHeaderModel.pl.add(PlantingLineTable.getServerTypeFormat(plantingHeaderModel, plantingLineTableList.get(i)));
                }
                plantingList.add(plantingHeaderModel);
            }
        } catch (Exception e) {
            Log.e("msg",e.getMessage());
            e.printStackTrace();
        } finally {
            db.close();
            db.destroyInstance();
            //expandiblelistbind();
        }
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void addMoreItem(){
        for (PlantingHeaderModel plantingheader:plantingList) {
            if(plantingheader!=null) {
                plantingList.add(plantingheader);
            }
        }
    }

    public int getTotalPages() {
        int remainingItems=plantingList.size() % row_per_page;
        if(remainingItems>0)
        {
            return plantingList.size() / row_per_page;
        }
        return (plantingList.size() / row_per_page)-1;
    }

    public ArrayList<PlantingHeaderModel> getCurrentGalaxys(int currentPage,List<PlantingHeaderModel> plantingList) {
        int startItem = currentPage * row_per_page;
        int lastItem = startItem + row_per_page;
        ArrayList<PlantingHeaderModel> currentGalaxys = new ArrayList<>();
        try {
            for (int i = 0; i <plantingList.size(); i++) {

                if (i >= startItem && i < lastItem) {
                    currentGalaxys.add(plantingList.get(i));
                }
            }
            return currentGalaxys;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void toggleButtons() {
        //SINGLE PAGE DATA
        if (getTotalPages() <= 1) {
            nextBtn.setEnabled(false);
            prevBtn.setEnabled(false);
        }
        //LAST PAGE
        else if (page_number == getTotalPages()) {
            nextBtn.setEnabled(false);
            prevBtn.setEnabled(true);
        }
        //FIRST PAGE
        else if (page_number == 0) {
            prevBtn.setEnabled(false);
            nextBtn.setEnabled(true);
        }
        //SOMEWHERE IN BETWEEN
        else if (page_number >= 1 && page_number <= getTotalPages()) {
            nextBtn.setEnabled(true);
            prevBtn.setEnabled(true);
        }

    }

}
