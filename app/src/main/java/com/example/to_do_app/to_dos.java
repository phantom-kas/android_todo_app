package com.example.to_do_app;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.to_do_app.adaptor.ToDoAdaptor;
import com.example.to_do_app.dbModels.ToDoModle;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link to_dos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class to_dos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "cid";

    private static final String ARG_PARAM3 = "title2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String mParam3;

    public ToDoAdaptor toDoAdaptor;

    private ListView tolist;

    public to_dos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment to_dos.
     */
    // TODO: Rename and change types and number of parameters
    public static to_dos newInstance(String param1, String param2 ,String param3) {
        to_dos fragment = new to_dos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        args.putString(ARG_PARAM3, param3);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
           // Toast.makeText(getContext(),mParam1,Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_dos, container, false);
        tolist =view.findViewById(R.id.todo_items);
        TextView cattitle = view.findViewById(R.id.to_cat_title);

        Bundle bundle = getArguments();
        if (bundle != null) {


        }
        else{
            Toast.makeText(getContext(),"No bundle",Toast.LENGTH_LONG).show();
        }

        Db db = new Db(getActivity());
        String[] params = {mParam1,mParam2,mParam3};
        List<ToDoModle> tds = db.getTodos(params);
        if(mParam1.equals("get_todo_in_cat")){
            cattitle.setText("Category "+ "{"+mParam3+"}");
        }


        toDoAdaptor = new ToDoAdaptor(getActivity(), tds,db,inflater);
        tolist.setAdapter(toDoAdaptor);

        //Toast.makeText(getActivity(),tds.toString(),Toast.LENGTH_SHORT).show();


        return view;
    }



}
