
package com.example.pristineseed.ui.bootmMainScreen.ui.menuHandler;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.pristineseed.SessionManageMent.SessionManagement;

import com.example.pristineseed.ui.bootmMainScreen.ui.DailyActivtiy.ViewDailyActivityDetailFragment;
import com.example.pristineseed.ui.bootmMainScreen.ui.DailyActivtiy.DailyActivityFragment;
import com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.CreateBookingOrderRename_Fragment;
import com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.OrderApproval.MarketingApprovalFragment;
import com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.OrderBookingApprovalFragment;
import com.example.pristineseed.ui.bootmMainScreen.ui.OrderBookingRenew.marketing_indent.Marketing_Indent_Fragment;
import com.example.pristineseed.ui.employee.HRPortalEmployee.EmployeeAttendanceFragment;
import com.example.pristineseed.ui.employee.HRPortalEmployee.LeaveApplicationFragment;
import com.example.pristineseed.ui.employee.HRPortalEmployee.appliedLeave.LeaveAppliedFragment;
import com.example.pristineseed.ui.employee.HRPortalEmployee.approveLeave.LeaveApprovelPendingDetailFragment;
import com.example.pristineseed.ui.employee.HRPortalEmployee.employeeAttendanceDetail.EmployeeAttendanceDetailFragment;
import com.example.pristineseed.ui.employee.HRPortalEmployee.viewLeave.ViewApprovelPendingDetailFragment;
import com.example.pristineseed.ui.inspection.planting.schedualer_inspection.CreateInspectionFragment;
import com.example.pristineseed.ui.inspection.planting.schedualer_inspection.SchedulerInspection;
import com.example.pristineseed.ui.reports.LotsDueforInspectionFragment;
import com.example.pristineseed.ui.reports.PLDandSownAcreViewFragment;
import com.example.pristineseed.ui.reports.ProdInspectionStatusFragment;
import com.example.pristineseed.ui.reports.QAInspectionStatusReportFragment;
import com.example.pristineseed.ui.reports.QAInspectionFlagWiseFragment;
import com.example.pristineseed.ui.reports.SDNDetailsFragment;
import com.example.pristineseed.ui.reports.SownAcresFragment;
import com.example.pristineseed.ui.reports.ZoneOrDistributorwiseMktIndentDetailsFragment;
import com.example.pristineseed.ui.reports.ZoneOrDistributorwiseOrderDetailsFragment;
import com.example.pristineseed.ui.reports.ZoneOrDistributorwiseSupplyDetailsFragment;
import com.example.pristineseed.ui.vendor.Dealer_master.DealerMaster_Fragment;
import com.example.pristineseed.ui.vendor.POG.POGApprovalFragment;
import com.example.pristineseed.ui.vendor.YieldEstimatesFragment;
import com.example.pristineseed.ui.vendor.distribution_master.DistributionMasterFragment;
import com.example.pristineseed.ui.bootmMainScreen.ui.notifications.NotificationsFragment;

import com.example.pristineseed.ui.eventManagement.approveEvent.EventApprovalFragment;
import com.example.pristineseed.ui.eventManagement.createEvent.CreateEventFragment;
import com.example.pristineseed.ui.eventManagement.viewEvent.ViewEventDetailFragment;
import com.example.pristineseed.ui.vendor.farmar_mst.FarmarMasterFragment;
import com.example.pristineseed.ui.inspection.landselection.LandSelectionFragment;
import com.example.pristineseed.ui.inspection.planting.createplanting.PlantingDetailFragment;

import com.example.pristineseed.ui.seedDispatchNote.SeedDispatchNoteFragment;
import com.example.pristineseed.ui.tfa_summary.approveTFA.ApproveTFADetailFragment;
import com.example.pristineseed.ui.tfa_summary.createTFA.CreateTFASummaryFragment;
import com.example.pristineseed.ui.tfa_summary.viewTFA.ViewTFADetailFragment;

import com.example.pristineseed.ui.travel.CreateTravelFragment;
import com.example.pristineseed.ui.travel.approveTravel.TDApproveFrament;
import com.example.pristineseed.ui.travel.viewtravel.ViewTravelDetailFragment;
import com.example.pristineseed.ui.vendor.BreederVisitorFragment;
import com.example.pristineseed.ui.vendor.CollectionFragment;
import com.example.pristineseed.ui.vendor.CustomerFragment;
import com.example.pristineseed.ui.vendor.FS_Indent_Requirements.FSIndentRequirementApprovalFragment;
import com.example.pristineseed.ui.vendor.fs_return_harvesting.FSRetrunHarvestingApprovalFragment;
import com.example.pristineseed.ui.vendor.fs_return_harvesting.FSReturnHarvestingFragment;
import com.example.pristineseed.ui.vendor.FS_Indent_Requirements.IndentFSRequirementFragment;
import com.example.pristineseed.ui.vendor.PearlMilletTrialSeedProductionFragment;
import com.example.pristineseed.ui.vendor.plough_down_list.PldownApproveListFragment;
import com.example.pristineseed.ui.vendor.plough_down_list.PloughDownFragment;
import com.example.pristineseed.ui.vendor.POG.ProductOnGroundFragment;
import com.google.android.material.tabs.TabLayout;

public class MenuTabAdapter extends FragmentStatePagerAdapter {
    private Context myContext;
    int totalTabs;
    TabLayout tabLayout;
    SessionManagement sessionManagement;
    String pass_data_to_child_fragment;
    String pass_key_to_child_fragment;
    public MenuTabAdapter(Context context, FragmentManager fm, TabLayout tabLayout, SessionManagement sessionManagement,String pass_key_to_child_fragment,String pass_data_to_child_fragment) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        myContext = context;
        this.totalTabs = tabLayout.getTabCount();
        this.tabLayout = tabLayout;
        this.sessionManagement=sessionManagement;
        this.pass_data_to_child_fragment=pass_data_to_child_fragment;
        this.pass_key_to_child_fragment=pass_key_to_child_fragment;
    }

    // this is for fragment tabs
    @NonNull
    @Override
    public Fragment getItem(int position) {
        String selected_tab_name = "";
        try {
            selected_tab_name = tabLayout.getTabAt(position).getText().toString();
        } catch (Exception e) {
            Log.e("exc", e.getMessage() + selected_tab_name);
        }
        Fragment fragment = null;
        switch ((sessionManagement.getSelectedGroupMenuName())) {
            case "App Setting":
                switch (selected_tab_name) {
                    case "Upgrade app":
                        fragment = new NotificationsFragment();
                        break;
                    case "Manual Sync":
                        fragment = new NotificationsFragment();
                        break;
                }
                break;

            case "General":
                switch (selected_tab_name) {
                    case "Employee Attendance":
                        fragment = new EmployeeAttendanceFragment();
                        break;
                    case "Leave Application":
                        fragment = new LeaveApplicationFragment();
                        break;

                    case "Leave Approval":
                        fragment = new LeaveApprovelPendingDetailFragment();
                        break;

                    case "View leave":
                        fragment = new ViewApprovelPendingDetailFragment();
                        break;

                    case "Add TA/DA Bill":
                        fragment = new CreateTravelFragment();
                        break;
                    case "TA/DA Approve":
                        fragment = new TDApproveFrament();
                        break;

                    case "View TA/DA List":
                        fragment = new ViewTravelDetailFragment();
                        break;

                    case "Add Daily Activity":
                        fragment = new DailyActivityFragment();
                        break;

                    case "Daily Activity List":
                        fragment = new ViewDailyActivityDetailFragment();
                        break;

                    case "Leave Applied":
                        fragment = new LeaveAppliedFragment();
                        break;

                    case "Employee Attendance Detail":
                        fragment = new EmployeeAttendanceDetailFragment();
                        break;
                }
                break;

            case "M&S":
                switch (selected_tab_name) {
                    case "Order Book":
                        fragment = new CreateBookingOrderRename_Fragment();
                        break;
                    case "Order Approve":
                        fragment = new OrderBookingApprovalFragment();
                        break;
                    /*case "Order List":
                        fragment = new PageNotFoundFragment(); // todo removed by instructions by navision side on (23-12-2021)..
                        break;*/
                    case "Collection Master":
                        fragment = new CollectionFragment();
                        break;
                    case "Distribution Master":
                        fragment = new DistributionMasterFragment();
                        break;
                    case "Dealer Master":
                        fragment = new DealerMaster_Fragment();
                        break;
                    case "Product On Ground":
                        fragment = new ProductOnGroundFragment();
                        break;
                    case "POG Approve":
                        fragment = new POGApprovalFragment();
                        break;
                    case "Create Event":
                        fragment = new CreateEventFragment();
                        break;
                    case "Approve Events":
                        fragment = new EventApprovalFragment();
                        break;
                    case "View Events":
                        fragment = new ViewEventDetailFragment();
                        break;
                    case "Create TFA":
                        fragment = new CreateTFASummaryFragment();
                        break;
                    case "Approve TFA":
                        fragment = new ApproveTFADetailFragment();
                        break;
                    case "View TFA":
                        fragment = new ViewTFADetailFragment();
                        break;
                    case "Farmer Master":
                        fragment = new FarmarMasterFragment();
                        break;
                    case "Customer Master":
                        fragment = new CustomerFragment();
                        break;
                    case "Marketing Indent":
                        fragment=new Marketing_Indent_Fragment();
                        break;
                    case "Marketing Indent Approval":
                        fragment=new MarketingApprovalFragment();
                        break;

                }
                break;

            case "FSP&HSP":
                switch (selected_tab_name) {
                    case "Planting List":
                        fragment = new PlantingDetailFragment();
                        break;
                    case "Scheduler": {
                        fragment = new SchedulerInspection();
                    }
                    break;
                    case "Create Inspection": {
                        fragment = new CreateInspectionFragment();
                    }
                    break;
                    case "Seed Dispatch Note":
                        fragment = new SeedDispatchNoteFragment();
                        break;
                    case "Visitor Remarks":
                        fragment = new BreederVisitorFragment();
                        break;
                    case "QA":
                        fragment =new SchedulerInspection();
                        break;
                }

                break;

            case "HSP":
                switch (selected_tab_name) {
                    case "Yield Estimates":
                        fragment = new YieldEstimatesFragment();
                        break;

                    case "Plough Down List":
                        fragment = new PloughDownFragment();
                        break;

                    case "Plough Down List Approve":
                        fragment = new PldownApproveListFragment();
                        break;

                    case "Land Selection":
                        fragment = new LandSelectionFragment();
                        break;

                    case "FS Return":
                        fragment = new FSReturnHarvestingFragment();
                        break;
                    case "FS Return Approval":
                        fragment = new FSRetrunHarvestingApprovalFragment();
                        break;

                    case "Indent for FS Requirement":
                        fragment = new IndentFSRequirementFragment();
                        break;

                    case "FS Requirement Approval":
                        fragment = new FSIndentRequirementApprovalFragment();
                        break;
                }
                break;
            /*case "FSP":
                switch (selected_tab_name) {
                    case "Breeder Visitor":
                        fragment = new BreederVisitorFragment();
                        break;}
                break;*/
            case "TSP":
                switch (selected_tab_name) {
                    case "Trial Seed Production":
                        fragment = new PearlMilletTrialSeedProductionFragment();
                        break;
                }
                break;

            case "Reports":
                switch (selected_tab_name){
                    case "Lots Due for Inspection":
                        fragment = new LotsDueforInspectionFragment();
                        break;
                    case "SDN Details":
                        fragment = new SDNDetailsFragment();
                        break;
                    case "Sown Acres":
                        fragment = new SownAcresFragment();
                        break;
                    case "Prod-Inspection Status":
                        fragment = new ProdInspectionStatusFragment();
                        break;
                    case"QA Inspection Status":
                        fragment = new QAInspectionStatusReportFragment();
                        break;
                    case "QA Inspection Flag Wise":
                        fragment = new QAInspectionFlagWiseFragment();
                        break;
                    case "Prod-Sown & PLD Acreage":
                        fragment = new PLDandSownAcreViewFragment();
                        break;
                    case "Zone Or Distributor wise Mkt Indent Details":
                        fragment = new ZoneOrDistributorwiseMktIndentDetailsFragment();
                        break;
                    case "Zone Or Distributor wise Order Details":
                        fragment = new ZoneOrDistributorwiseOrderDetailsFragment();
                        break;
                    case "Zone Or Distributor wise Supply Details":
                        fragment = new ZoneOrDistributorwiseSupplyDetailsFragment();
            }
            break;
           /* case "Getting Order":
                switch (selected_tab_name){
                    case "Marketing Indent":
                        fragment=new Marketing_Indent_Fragment();
                        break;
                    case "Marketing Indent Approval":
                        fragment=new MarketingApprovalFragment();
                        break;
                }
                break;
*/
        }
        if (fragment == null) {
            return new PageNotFoundFragment();
        } else {
            if(pass_key_to_child_fragment!=null && !pass_key_to_child_fragment.equalsIgnoreCase("")){
                Bundle bundle=new Bundle();
                bundle.putString(pass_key_to_child_fragment,pass_data_to_child_fragment);
                fragment.setArguments(bundle);
            }
            return fragment;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }

}