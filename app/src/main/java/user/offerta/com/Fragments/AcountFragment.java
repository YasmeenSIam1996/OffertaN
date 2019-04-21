package user.offerta.com.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseLocation;
import user.offerta.com.API.ResponseObject;
import user.offerta.com.API.ResponseUser;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Activities.MapsActivity;
import user.offerta.com.Activities.PaymentMethodActivity;
import user.offerta.com.Adspters.LocationAdapter;
import user.offerta.com.App.ApplicationController;
import user.offerta.com.Interfaces.DeleteLocationClickListener;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.LocationData;
import user.offerta.com.Module.User;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;


public class AcountFragment extends Fragment {

    private RadioGroup RadioGroupTap;
    private LinearLayout personal_lay, password_lay;
    private RadioButton PERSONAL, PASSWORD;
    private User user = ApplicationController.getInstance().getUser();
    private EditText First_Name, Email, Mobile, Old_Password, New_Password, Re_New_Password;
    private FrameLayout frameLoading;
    private Button save;
    private boolean isPersonalFragment = true;
    private LocationAdapter locationAdapter;
    private List<LocationData> locationDataList;
    private RecyclerView LocationRecycle;
    private TextView add_place;
    static String Data = "";

    public static AcountFragment newInstance(String s) {
        AcountFragment fragment = new AcountFragment();
        Data = s;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acount, container, false);
        intiViews(view);
        return view;
    }

    private void intiViews(View view) {
        add_place = view.findViewById(R.id.add_place);
        RadioGroupTap = view.findViewById(R.id.RadioGroupTap);
        personal_lay = view.findViewById(R.id.personal_lay);
        password_lay = view.findViewById(R.id.password_lay);
        PERSONAL = view.findViewById(R.id.PERSONAL);
        PASSWORD = view.findViewById(R.id.PASSWORD);
        frameLoading = view.findViewById(R.id.frameLoading);
        First_Name = view.findViewById(R.id.First_Name);
        Email = view.findViewById(R.id.Email);
        Mobile = view.findViewById(R.id.Mobile);
        save = view.findViewById(R.id.save);
        add_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MapsActivity.class));

            }
        });
        locationDataList = new Vector<>();
        Old_Password = view.findViewById(R.id.Old_Password);
        New_Password = view.findViewById(R.id.New_Password);
        Re_New_Password = view.findViewById(R.id.Re_New_Password);
        LocationRecycle = view.findViewById(R.id.LocationRecycle);
        First_Name.setText(user.getName());
        Email.setText(user.getEmail());
        Mobile.setText(user.getPhone());
        setUpLocationAdapter();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPersonalFragment) {
                    String name = First_Name.getText().toString().trim(), email = Email.getText().toString().trim(), mobile = Mobile.getText().toString().trim();
                    if (name.equals("")) {
                        Constants.showDialog(getActivity(), getResources().getString(R.string.NameNull));
                    } else if (mobile.equals("")) {
                        Constants.showDialog(getActivity(), getResources().getString(R.string.MobileNull));
                    } else if (email.equals("")) {
                        Constants.showDialog(getActivity(), getResources().getString(R.string.EmailNull));
                    } else if (!isValidEmail(email)) {
                        Constants.showDialog(getActivity(), getResources().getString(R.string.EmailValid));
                    } else {
                        updateProfile(name, email, mobile);
                    }
                } else {
                    String old_pass = Old_Password.getText().toString().trim(), new_pass = New_Password.getText().toString().trim(), re_pass = Re_New_Password.getText().toString().trim();
                    if (old_pass.equals("")) {
                        Constants.showDialog(getActivity(), getResources().getString(R.string.NewPassError));
                    } else if (new_pass.equals("")) {
                        Constants.showDialog(getActivity(), getResources().getString(R.string.OldPassError));
                    } else if (re_pass.equals("")) {
                        Constants.showDialog(getActivity(), getResources().getString(R.string.Re_New_Password));
                    } else {
                        updatePassword(old_pass, new_pass, re_pass);
                    }
                }
            }

        });

        RadioGroupTap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.PERSONAL:
                        personal_lay.setVisibility(View.VISIBLE);
                        password_lay.setVisibility(View.GONE);
                        if (Constants.getLanguage().equals("ar")) {
                            PERSONAL.setBackground(getResources().getDrawable(R.drawable.drawable_tab_shape2));
                            PASSWORD.setBackground(getResources().getDrawable(R.drawable.drawable_tab_shape_un2));
                        } else {
                            PERSONAL.setBackground(getResources().getDrawable(R.drawable.drawable_tab_shape));
                            PASSWORD.setBackground(getResources().getDrawable(R.drawable.drawable_tab_shape_un));
                        }
                        PERSONAL.setTextColor(Color.parseColor("#FFFFFF"));
                        PASSWORD.setTextColor(Color.parseColor("#00C8AA"));
                        isPersonalFragment = true;
                        break;
                    case R.id.PASSWORD:
                        password_lay.setVisibility(View.VISIBLE);
                        personal_lay.setVisibility(View.GONE);
                        if (Constants.getLanguage().equals("ar")) {
                            PASSWORD.setBackground(getResources().getDrawable(R.drawable.drawable_tab_shape));
                            PERSONAL.setBackground(getResources().getDrawable(R.drawable.drawable_tab_shape_un));
                        } else {
                            PASSWORD.setBackground(getResources().getDrawable(R.drawable.drawable_tab_shape2));
                            PERSONAL.setBackground(getResources().getDrawable(R.drawable.drawable_tab_shape_un2));
                        }

                        PASSWORD.setTextColor(Color.parseColor("#FFFFFF"));
                        PERSONAL.setTextColor(Color.parseColor("#00C8AA"));
                        isPersonalFragment = false;

                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        GetAddress();

    }

    private void updateProfile(String name, String email, String phone) {

        frameLoading.setVisibility(View.VISIBLE);

        new UserAPI().updateProfile(name, email, phone, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseUser responseObject = (ResponseUser) result;
                if (responseObject.isStatus()) {
                    ApplicationController.getInstance().loginUser(ApplicationController.getInstance().getUser());
                    ApplicationController.getInstance().loginUser(responseObject.getUser());
                    Constants.showDialog(getActivity(), responseObject.getMessage());
                    if (!Data.equals("")) {
                        getActivity().startActivity(new Intent(getActivity(),PaymentMethodActivity.class));
                    }
                } else {
                    Constants.showDialog(getActivity(), responseObject.getMessage());

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

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void updatePassword(String old_password, String new_password, String c_new_password) {

        frameLoading.setVisibility(View.VISIBLE);

        new UserAPI().updatePassword(old_password, new_password, c_new_password, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseObject = (ResponseObject) result;
                if (responseObject.isStatus()) {
                    Constants.showDialog(getActivity(), responseObject.getMessage());
                } else {
                    Constants.showDialog(getActivity(), responseObject.getMessage());
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


    private void setUpLocationAdapter() {
        locationAdapter = new LocationAdapter(locationDataList, getActivity(), true);
        LocationRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LocationRecycle.setItemAnimator(new DefaultItemAnimator());
        LocationRecycle.setAdapter(locationAdapter);
        LocationRecycle.setHasFixedSize(true);
        locationAdapter.setOnItemClickListener(new DeleteLocationClickListener() {
            @Override
            public void onItemClickListener(int position, View view, LocationData locationData) {
                deleteAddress(locationData.getId() + "", position);
            }
        });
    }

    private void GetAddress() {

        new UserAPI().GetAddress(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseLocation responseObject = (ResponseLocation) result;
                if (responseObject.isStatus()) {
                    locationDataList.clear();
                    locationDataList.addAll(responseObject.getLocationData());
                    locationAdapter.notifyDataSetChanged();
                } else {
                    Constants.showDialog(getActivity(), responseObject.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;

                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(getActivity(), message);
            }
        });
    }


    private void deleteAddress(String idLocation, final int position) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().deleteAddress(idLocation, position, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseObject = (ResponseObject) result;
                if (responseObject.isStatus()) {
                    locationDataList.remove(locationDataList.get(position));
                    locationAdapter.notifyDataSetChanged();
                } else {
                    Constants.showDialog(getActivity(), responseObject.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;

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
