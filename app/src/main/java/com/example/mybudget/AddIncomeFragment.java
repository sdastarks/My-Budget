package com.example.mybudget;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Fragment allows the user to enter
 * an income, that will affect the balance
 *
 * @author Daniel Beadleson
 *
 */
public class AddIncomeFragment extends Fragment {

    private static  final String TAG= "AddIncomeFragment";
    EditText mDescription;
    EditText mAmount;
    /*
     * Method creates the inititial state of the
     * fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_add_income, container, false);

        mDescription= (EditText) view.findViewById(R.id.description);
        mAmount = view.findViewById(R.id.amount);

        return view;

    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        Log.v(TAG, "onViewCreated inititialsed");
        FloatingActionButton saveButton= view.findViewById(R.id.floatingActionButton_saveIncome);
        saveButton.setOnClickListener(new View.OnClickListener() {
            /*
             * Method gets the description and amount of the income
             */
            @Override
            public void onClick(View v) {
                String description = mDescription.getText().toString();
                Log.v(TAG,"description: " +description);
                String sAmount= mAmount.getText().toString();

                if (description.isEmpty() | sAmount.isEmpty()){
                    Toast.makeText(getActivity(),"Both fields must be filled",Toast.LENGTH_SHORT).show();
                }
                else{
                    int amount= Integer.parseInt(sAmount);
                    Log.v(TAG, "amount: "+ amount);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        FloatingActionButton cancelButton = view.findViewById(R.id.floatingActionButton_cancelIncome);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "cancel button initialised");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
