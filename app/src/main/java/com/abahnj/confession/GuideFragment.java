package com.abahnj.confession;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GuideFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GuideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuideFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GuideFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuideFragment newInstance(String param1, String param2) {
        GuideFragment fragment = new GuideFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_guide, container, false);
        CardView cardView = (CardView) rootView.findViewById(R.id.faq);
        CardView cardView1 = (CardView) rootView.findViewById(R.id.asbp);
        CardView cardView2 = (CardView) rootView.findViewById(R.id.effc);
        CardView cardView3 = (CardView) rootView.findViewById(R.id.ccc);
        CardView cardView4 = (CardView) rootView.findViewById(R.id.htmagc);
        cardView.setOnClickListener(fragmentOnClick);
        cardView1.setOnClickListener(fragmentOnClick);
        cardView2.setOnClickListener(fragmentOnClick);
        cardView3.setOnClickListener(fragmentOnClick);
        cardView4.setOnClickListener(fragmentOnClick);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        AppBarLayout toolbar = (AppBarLayout) getActivity().findViewById(R.id.appbar);  // or however you need to do it for your code
        toolbar.setExpanded(false);
    }

    View.OnClickListener fragmentOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.faq:
                    mListener.onFragmentInteraction(1);
                    break;
                case R.id.asbp:
                    mListener.onFragmentInteraction(2);
                    break;
                case R.id.effc:
                    mListener.onFragmentInteraction(3);
                    break;
                case R.id.ccc:
                    mListener.onFragmentInteraction(4);
                    break;
                case R.id.htmagc:
                    mListener.onFragmentInteraction(5);
                    break;

            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int guideId);
    }
}
