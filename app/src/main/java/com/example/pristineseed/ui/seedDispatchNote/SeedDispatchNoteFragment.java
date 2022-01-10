package com.example.pristineseed.ui.seedDispatchNote;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListDao;
import com.example.pristineseed.DataBaseRepository.GeographicalRepo.PlantingLineLotListTable;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingDetailHeaderTable;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingHeaderDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineDao;
import com.example.pristineseed.DataBaseRepository.Planting.PlantingLineTable;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedDispatchHeaderDao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedDispatchNoteHeaderTable;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedDispatchNoteLineDao;
import com.example.pristineseed.DataBaseRepository.seed_dispatch_note.SeedDispatchNoteLineTable;
import com.example.pristineseed.GlobalNotification.NetworkUtil;
import com.example.pristineseed.R;
import com.example.pristineseed.RoomDataBase.PristineDatabase;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.global.LoadingDialog;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.model.PlantingModel.PlantingHeaderModel;
import com.example.pristineseed.model.seed_dispatch_note.SeedDispatchHeaderModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.seedDispatchNote.creat_seed_dispatch_note.CreateSeedDispachNoteFragment;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeedDispatchNoteFragment extends Fragment {
    private int selectedchildPosition = 0;
    private ExpandableListView expandableListView;
    private SessionManagement sessionManagement;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seed_dispach_note, container, false);
    }

   private Chip chip_add_planting;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement=new SessionManagement(getActivity());
        initView(view);

        boolean isNetowrk= NetworkUtil.getConnectivityStatusBoolean(getContext());
        if(isNetowrk){
            getSeedDispatchNoteData();
        }
        else {
            Toast.makeText(getActivity(), "Please wait for internet connections.", Toast.LENGTH_SHORT).show();
        }
        chip_add_planting.setOnClickListener(v -> {
            StaticMethods.loadFragmentsWithBackStack(getActivity(), new CreateSeedDispachNoteFragment(), "Create_Seed_Dispach Note");
        });

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
            parent.setItemChecked(index, true);
            selectedchildPosition = index;
            Bundle bundle = new Bundle();
            bundle.putString("SDN_Number", seedDispatchHeaderModelList.get(groupPosition).dispatch_no);
            bundle.putString("Seed_Dispatch_note_details",new Gson().toJson(seedDispatchHeaderModelList.get(groupPosition)));
            bundle.putString("flag","view_flag");
            CreateSeedDispachNoteFragment fragment = new CreateSeedDispachNoteFragment();
            fragment.setArguments(bundle);
            StaticMethods.loadFragmentsWithBackStack(getActivity(),fragment, "create_seed_dispatch_note_fragment");
            return false;
        });
    }
    private void initView(@NonNull View view) {
        expandableListView = view.findViewById(R.id.schedule_inspection_expnd_list);
        chip_add_planting = view.findViewById(R.id.chip_add_planting);

    }

    @Override
    public void onResume() {
        getSeedDispatchNoteData();
        super.onResume();
    }

    private List<SeedDispatchHeaderModel> seedDispatchHeaderModelList;
    private void getSeedDispatchNoteData(){
        NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
        Call<List<SeedDispatchHeaderModel>> call = mAPIService.getSeedDispatchNoteData(sessionManagement.getUserEmail());
        LoadingDialog progressDialogLoading=new LoadingDialog();
        progressDialogLoading.showLoadingDialog(getActivity());
        call.enqueue(new Callback<List<SeedDispatchHeaderModel>>(){
            @Override
            public void onResponse(Call<List<SeedDispatchHeaderModel>> call, Response<List<SeedDispatchHeaderModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialogLoading.hideDialog();
                        List<SeedDispatchHeaderModel> temSeedDispatchNoteList = response.body();
                        if (temSeedDispatchNoteList!=null && temSeedDispatchNoteList.size() > 0 && temSeedDispatchNoteList.get(0).condition) {
                            seedDispatchHeaderModelList = temSeedDispatchNoteList;
                            expandiblelistbind();
                          //  InsertSeedDispatchHeaderLine(seedDispatchHeaderModelList);
                        } else {
                            progressDialogLoading.hideDialog();
                            Toast.makeText(getActivity(), temSeedDispatchNoteList.size() > 0 ? "Record not found !" : "Api not respoding.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialogLoading.hideDialog();
                        Toast.makeText(getActivity(), response.message() + ". Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    progressDialogLoading.hideDialog();
                    Log.e("exception database", e.getMessage() + "cause");
                    ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "seed_dispatch_note", getActivity());
                }
                finally {
                    progressDialogLoading.hideDialog();
                }
            }
            @Override
            public void onFailure(Call<List<SeedDispatchHeaderModel>> call, Throwable t) {
                progressDialogLoading.hideDialog();
                ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "seed_dispatch_note", getActivity());
            }
        });

    }

    public void expandiblelistbind() {
       SeedDispatchNoteExpandableListAdapter expandableListAdapter = new SeedDispatchNoteExpandableListAdapter(this.getActivity(), seedDispatchHeaderModelList);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            for (int g = 0; g < expandableListAdapter.getGroupCount(); g++) {
                if (g != groupPosition) {
                    expandableListView.collapseGroup(g);
                }
            }
        });
    }

    private void InsertSeedDispatchHeaderLine(List<SeedDispatchHeaderModel> tempSeedDispatchList) {
        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            SeedDispatchHeaderDao seedDispatchHeaderDao = db.seedDispatchHeaderDao();
            SeedDispatchNoteLineDao seedDispatchNoteLineDao = db.seedDispatchNoteLineDao();
            for (int i = 0; i < tempSeedDispatchList.size(); i++) {
                SeedDispatchNoteHeaderTable seedDispatchNoteHeaderTable = SeedDispatchNoteHeaderTable.getDispatchHeaderData(tempSeedDispatchList.get(i));
                if(seedDispatchHeaderDao.isDataExist(tempSeedDispatchList.get(i).dispatch_no)){
                    seedDispatchHeaderDao.update(seedDispatchNoteHeaderTable);
                }else {
                    seedDispatchHeaderDao.insert(seedDispatchNoteHeaderTable);
                }

                if(tempSeedDispatchList.get(i).dispatch_line.get(0).dispatch_no!=null){
                    for (int j = 0; j < tempSeedDispatchList.get(i).dispatch_line.size(); j++) {
                        SeedDispatchNoteLineTable seedDispatchNoteLineTable = SeedDispatchNoteLineTable.getLineDataFormate(tempSeedDispatchList.get(i).dispatch_line.get(j));
                        int count = seedDispatchNoteLineDao.isDataExist_on_line(tempSeedDispatchList.get(i).dispatch_line.get(j).line_no, tempSeedDispatchList.get(i).dispatch_no);//empPlantingList.get(i).pl.get(j).code
                        if (count <= 0) {
                            seedDispatchNoteLineDao.insert(seedDispatchNoteLineTable);
                        } else {
                            seedDispatchNoteLineDao.update(seedDispatchNoteLineTable);
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

   /* private void getDataFromLocal() {
        if(seedDispatchHeaderModelList!=null && seedDispatchHeaderModelList.size()>0){
            seedDispatchHeaderModelList.clear();
        }

        PristineDatabase db = PristineDatabase.getAppDatabase(getActivity());
        try {
            SeedDispatchHeaderDao seedDispatchHeaderDao = db.seedDispatchHeaderDao();
            SeedDispatchNoteLineDao seedDispatchNoteLineDao = db.seedDispatchNoteLineDao();
            List<SeedDispatchNoteHeaderTable> seedDispatchNoteHeaderTableList = seedDispatchHeaderDao.getAllData();
            for (int k = 0; k < seedDispatchNoteHeaderTableList.size(); k++) {
                SeedDispatchHeaderModel seedDispatchHeaderModel = SeedDispatchNoteHeaderTable.getDispatchServerFormat(seedDispatchNoteHeaderTableList.get(k));
                //todo bind scheduler Line
                List<SeedDispatchNoteLineTable> seedDispatchNoteLineTableList = seedDispatchNoteLineDao.getAllDatabyDispatchCode(seedDispatchHeaderModel.dispatch_no);
                for (int i = 0; i < seedDispatchNoteLineTableList.size(); i++) {
                    seedDispatchHeaderModel.dispatch_line.add(SeedDispatchNoteLineTable.getServerTypeFormat(seedDispatchHeaderModel, seedDispatchNoteLineTableList.get(i)));
                    }
                    seedDispatchHeaderModelList.add(seedDispatchHeaderModel);
            }
        } catch (Exception e) {
            Log.e("msg",e.getMessage());
            e.printStackTrace();
        } finally {
            db.close();
            db.destroyInstance();
            expandiblelistbind();
        }
    }*/


}