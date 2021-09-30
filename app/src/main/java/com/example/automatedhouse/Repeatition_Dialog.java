package com.example.automatedhouse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Repeatition_Dialog extends AppCompatDialogFragment {

    public Spinner repeatition;
    Dialoglistner listner;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog,null);

        builder.setView(view)
                .setTitle("Repetition")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String repeat = repeatition.getSelectedItem().toString();
                        listner.applyText(repeat);

                    }
                });

        repeatition = view.findViewById(R.id.dropdown_list);

        ArrayAdapter<String> myadpt = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.times));
        myadpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        repeatition.setAdapter(myadpt);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listner  = (Dialoglistner) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + "Must Implement Dialog Box");
        }
    }

    public interface Dialoglistner  {
        void applyText(String repeat);
    }
}
