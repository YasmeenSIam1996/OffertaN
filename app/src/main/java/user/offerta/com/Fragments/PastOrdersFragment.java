package user.offerta.com.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;
import java.util.Vector;

import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponsePastOrders;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Adspters.OrderAdapter;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.Order;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;


public class PastOrdersFragment extends Fragment {
    private OrderAdapter orderAdapter;
    private RecyclerView ordersRecycler;
    private List<Order> orders;
    private FrameLayout frameLoading;

    public static PastOrdersFragment newInstance() {
        PastOrdersFragment fragment = new PastOrdersFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_past_orders, container, false);
        ordersRecycler = view.findViewById(R.id.ordersRecycler);
        frameLoading = view.findViewById(R.id.frameLoading);
        orders = new Vector<>();
        setUpLocationAdapter();
        getOrders();
        return view;
    }

    private void setUpLocationAdapter() {
        orderAdapter = new OrderAdapter(orders, getActivity());
        ordersRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ordersRecycler.setItemAnimator(new DefaultItemAnimator());
        ordersRecycler.setAdapter(orderAdapter);
        ordersRecycler.setHasFixedSize(true);
    }

    private void getOrders() {
        frameLoading.setVisibility(View.VISIBLE);

        new UserAPI().getPastOrders(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponsePastOrders responseObject = (ResponsePastOrders) result;
                if (responseObject.isStatus()) {
                    orders.addAll(responseObject.getOrderList());
                    orderAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(getActivity(), responseError.getMessage());

                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(getActivity(), message);
            }
        });
    }


}
