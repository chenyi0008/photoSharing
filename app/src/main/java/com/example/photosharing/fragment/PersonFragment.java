package com.example.photosharing.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photosharing.ModifyInformationActivity;
import com.example.photosharing.R;
import com.example.photosharing.ShareDetailActivity;
import com.example.photosharing.model.UserInfo;
import com.example.photosharing.util.ImageDownloader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView userAvater;
//    private TextView userID;
    private TextView userName;
    private TextView userGender;
    private TextView userBio;

    private Button editBtn;

    public PersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
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
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        userAvater = view.findViewById(R.id.userAvatar);
//        userID = view.findViewById(R.id.userID);
        userName = view.findViewById(R.id.username);
        userGender = view.findViewById(R.id.gender);
        userBio = view.findViewById(R.id.bio);
        editBtn = view.findViewById(R.id.bt_edit);

        System.out.println(UserInfo.getInstance().getAvatar());
        final Bitmap[] bitmap = {null};
        String image = UserInfo.getInstance().getAvatar();
        if(image == null || image.isEmpty())
            image = "https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2023/08/26/f1e9df8b-f12e-4015-acc0-20e5b139636b.png";
        new ImageDownloader(userAvater).execute(image);

        userAvater.setImageBitmap(bitmap[0]);
//        userID.setText("ID：" + UserInfo.getInstance().getId());
        userName.setText(" " + UserInfo.getInstance().getUsername());
        boolean sex = UserInfo.getInstance().getSex() == null || UserInfo.getInstance().getSex().equals("0");

        userGender.setText(" " + (sex ? "Male" : "Female"));
        userBio.setText(" " + UserInfo.getInstance().getIntroduce());

        editBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ModifyInformationActivity.class);
        startActivity(intent);
    }
}