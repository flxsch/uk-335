package ch.noseryoung.uek335.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ch.noseryoung.uek335.DashboardActivity;
import ch.noseryoung.uek335.R;
import ch.noseryoung.uek335.model.PasswordAuthentication;
import ch.noseryoung.uek335.model.User;
import ch.noseryoung.uek335.persistence.AppDatabase;
import ch.noseryoung.uek335.persistence.UserDAO;
import ch.noseryoung.uek335.register.RegisterFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private UserDAO mUserDao;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment;
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Button saveUserButton = getActivity().findViewById(R.id.button_submit_login);
        saveUserButton.setOnClickListener(mSaveUserOnClickListener);

        mUserDao = AppDatabase.getAppDb(getActivity().getApplicationContext()).getUserDao();
    }

    private View.OnClickListener mSaveUserOnClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View openActivityButton) {
            PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

            // Get Login contents
            EditText emailView = getView().findViewById(R.id.text_field_email_login);
            EditText passwordView = getView().findViewById(R.id.text_field_password_login);
            String password = passwordView.getText().toString();

            // Get Database user
            User user = mUserDao.getOne(emailView.getText().toString());

            // Authenticate
            if (user != null && passwordAuthentication.authenticate(password.toCharArray(), user.getPassword())) {
                Intent intent = new Intent(getActivity(), DashboardActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
