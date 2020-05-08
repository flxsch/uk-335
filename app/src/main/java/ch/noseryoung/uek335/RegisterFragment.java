package ch.noseryoung.uek335;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import ch.noseryoung.uek335.model.PasswordAuthentication;
import ch.noseryoung.uek335.model.User;
import ch.noseryoung.uek335.persistence.AppDatabase;
import ch.noseryoung.uek335.persistence.UserDAO;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private UserDAO mUserDao;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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

        // Inflate the layout for this fragment
        ;
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button saveUserButton = getActivity().findViewById(R.id.button_submit_register);
        saveUserButton.setOnClickListener(mSaveUserOnClickListener);

        mUserDao = AppDatabase.getAppDb(getActivity().getApplicationContext()).getUserDao();
    }

    private View.OnClickListener mSaveUserOnClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View openActivityButton) {
            PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

            // Get Register contents
            EditText firstNameView = getView().findViewById(R.id.text_field_firstname_register);
            EditText lastNameView = getView().findViewById(R.id.text_field_lastname_register);
            EditText emailView = getView().findViewById(R.id.text_field_email_register);
            EditText passwordView = getView().findViewById(R.id.text_field_password_register);

            // Create new User
            User user = new User();
            user.setFirstName(firstNameView.getText().toString());
            user.setLastName(lastNameView.getText().toString());
            user.setEmail(emailView.getText().toString());
            user.setPassword(passwordAuthentication.hash(passwordView.getText().toString().toCharArray()));

            mUserDao.insertOne(user);
        }
    };
}
