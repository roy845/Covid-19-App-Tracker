package com.example.finalproject;



import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class QuarantineFragment extends Fragment {


    EditText dateEditText;
    EditText editText1;
    TextView quarantineText;
    static Calendar myCalendar;
    static Calendar myCalendarEndQuarantine;
    static Switch switchCoronaTest;
    CountriesViewModel countriesViewModel;
    Button clearBtn,callToReportBtn,reportOnSiteBtn;
    String data[];
    static boolean isDeleted;
    String FILENAME = "date.txt";
    static boolean flagClear;
    static boolean flagText;
    static boolean clearBtnVisibility;
    static boolean callToReportBtnVisibility;
    static boolean reportOnSiteBtnVisibility;
    static boolean switchVisibility;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        return inflater.inflate(R.layout.quarantine_frag, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        dateEditText = view.findViewById(R.id.picker);
        editText1 = view.findViewById(R.id.editTextDate);
        switchCoronaTest = view.findViewById(R.id.switchCoronaTest);
        myCalendar = Calendar.getInstance();
        myCalendarEndQuarantine = Calendar.getInstance();
        clearBtn = view.findViewById(R.id.clearBtn);
        quarantineText = view.findViewById(R.id.quarantinetTxtHeader);
        callToReportBtn = view.findViewById(R.id.callToReportButton);
        reportOnSiteBtn = view.findViewById(R.id.reportOnSiteButton);
        data = new String[]{};
        countriesViewModel = new CountriesViewModel(getActivity().getApplication(),getContext());
        initDatePickerDialog();

        flagText = countriesViewModel.getHeaderText();
        flagClear = countriesViewModel.getClearButtonState();
        clearBtn.setEnabled(flagClear);
        clearBtnVisibility = countriesViewModel.getClearBtnVisibility();
        callToReportBtnVisibility = countriesViewModel.getCallToReportBtnVisibility();
        reportOnSiteBtnVisibility = countriesViewModel.getReportOnSiteBtnVisibility();
        switchVisibility = countriesViewModel.getSwitchVisibility();
        clearBtn.setBackgroundColor(Color.parseColor("#ff0000"));
        callToReportBtn.setBackgroundColor(Color.parseColor("#44C052"));
        reportOnSiteBtn.setBackgroundColor(Color.parseColor("#2638DD"));

        if(clearBtnVisibility) {
            clearBtn.setVisibility(View.VISIBLE);
        }
        else {
            clearBtn.setVisibility(View.GONE);
        }

        if(callToReportBtnVisibility){
            callToReportBtn.setVisibility(View.VISIBLE);
        }

        else{
            callToReportBtn.setVisibility(View.GONE);
        }

        if(reportOnSiteBtnVisibility){
            reportOnSiteBtn.setVisibility(View.VISIBLE);
        }

        else{
            reportOnSiteBtn.setVisibility(View.GONE);
        }

        if(switchVisibility){
            switchCoronaTest.setVisibility(View.VISIBLE);
        }
        else{
            switchCoronaTest.setVisibility(View.GONE);
        }



        if(flagText)
        quarantineText.setText(R.string.happyQuarantineTxt);
        else
            quarantineText.setText("");

        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            view.setBackgroundColor(Color.parseColor("#424242"));
            clearBtn.setBackgroundColor(Color.parseColor("#6f6f6f"));
            callToReportBtn.setBackgroundColor(Color.parseColor("#6f6f6f"));
            reportOnSiteBtn.setBackgroundColor(Color.parseColor("#6f6f6f"));
        }
        else
        {
            view.setBackgroundColor(Color.WHITE);
            clearBtn.setBackgroundColor(Color.parseColor("#ff0000"));
            callToReportBtn.setBackgroundColor(Color.parseColor("#44C052"));
            reportOnSiteBtn.setBackgroundColor(Color.parseColor("#2638DD"));
        }



        //On clear button click
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateEditText.setText("");
                editText1.setText("");
                isDeleted =  getContext().deleteFile(FILENAME);
                switchCoronaTest.setChecked(false);
                switchCoronaTest.setEnabled(false);
                if(!isDeleted)
                    countriesViewModel.saveDataToFile();
                flagClear = false;
                flagText = false;
                clearBtnVisibility = false;
                callToReportBtnVisibility = false;
                reportOnSiteBtnVisibility = false;
                switchVisibility = false;
                clearBtn.setVisibility(View.GONE);
                callToReportBtn.setVisibility(View.GONE);
                reportOnSiteBtn.setVisibility(View.GONE);
                switchCoronaTest.setVisibility(View.GONE);
                    countriesViewModel.saveClearButtonState();
                    countriesViewModel.saveHeaderText();
                quarantineText.setText("");

                new AlertDialog.Builder(getContext())
                        .setTitle("Notification")
                        .setMessage("notification is canceled")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .create().show();

                Intent serviceIntent = new Intent(getContext(), ForegroundService.class);
                ContextCompat.startForegroundService(getContext(), serviceIntent);

                clearBtn.setEnabled(flagClear);
                System.out.println("file deleted? "+isDeleted);


            }
        });


        switchCoronaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDays();
                if(!isDeleted)
                    countriesViewModel.saveDataToFile();

                new AlertDialog.Builder(getContext())
                        .setTitle("Notification")
                        .setMessage("notification is set")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .create().show();

                getActivity().stopService(new Intent(getActivity(), ForegroundService.class));
                Intent serviceIntent = new Intent(getContext(), ForegroundService.class);
                ContextCompat.startForegroundService(getContext(), serviceIntent);

            }
        });

        try {

                      data = countriesViewModel.getDataFromFile();

                       if(data==null) {
                          isDeleted = true;
}
                        else
                       {
                          isDeleted=false;
                       }


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
                updateData();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        callToReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL); //setting the dial action
                intent.setData(Uri.parse("tel:"+"*5400")); //setting the number would appear
                try {
                    startActivity(intent); //Returns to the parent in the end with its status
                }
                catch (Exception ex){
                    ex.toString();
                }

            }
        });

        reportOnSiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGlobal = new Intent();
                intentGlobal.setAction(Intent.ACTION_VIEW);
                intentGlobal.addCategory(Intent.CATEGORY_BROWSABLE);
                intentGlobal.setData(Uri.parse("https://www.gov.il/he/service/quarantine-self-report"));
                startActivity(intentGlobal);

            }
        });


        super.onViewCreated(view,savedInstanceState);
    }

    public void addDays() {
        myCalendarEndQuarantine.setTime(myCalendar.getTime());

        if(switchCoronaTest.isChecked()) {
            myCalendarEndQuarantine.add(Calendar.DATE, 10);
        }
        else {
            myCalendarEndQuarantine.add(Calendar.DATE, 14);
        }
        updateEndQuarantineTime();
    }

    public void initDatePickerDialog()
    {

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                isDeleted=false;
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                myCalendarEndQuarantine.setTime(myCalendar.getTime());
                addDays();
                if(!isDeleted)
                countriesViewModel.saveDataToFile();

                flagClear = true;
                flagText = true;
                clearBtnVisibility = true;
                callToReportBtnVisibility = true;
                reportOnSiteBtnVisibility = true;
                switchVisibility = true;
                clearBtn.setVisibility(View.VISIBLE);
                callToReportBtn.setVisibility(View.VISIBLE);
                reportOnSiteBtn.setVisibility(View.VISIBLE);
                switchCoronaTest.setVisibility(View.VISIBLE);
                countriesViewModel.saveClearButtonState();
                countriesViewModel.saveHeaderText();
                clearBtn.setEnabled(flagClear);
                quarantineText.setText(R.string.happyQuarantineTxt);

                new AlertDialog.Builder(getContext())
                        .setTitle("Notification")
                        .setMessage("notification is set")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .create().show();

                getActivity().stopService(new Intent(getActivity(), ForegroundService.class));
                Intent serviceIntent = new Intent(getContext(), ForegroundService.class);
                ContextCompat.startForegroundService(getContext(), serviceIntent);

            }

        };

        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                isDeleted = false;
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                         myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy";
        TimeZone timeZone = TimeZone.getTimeZone("Israel");
        myCalendar.setTimeZone(timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEditText.setText(sdf.format(myCalendar.getTime()));
        switchCoronaTest.setEnabled(true);

    }

    public void updateEndQuarantineTime () {

        String myFormat = "dd/MM/yy";
        TimeZone timeZone = TimeZone.getTimeZone("Israel");
        myCalendarEndQuarantine.setTimeZone(timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText1.setText(sdf.format(myCalendarEndQuarantine.getTime()));

    }


    public void updateData() throws IOException {
        String myFormat = "dd/MM/yy";
        TimeZone timeZone = TimeZone.getTimeZone("Israel");
        myCalendar.setTimeZone(timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        data = countriesViewModel.getDataFromFile();
        if(dateEditText.getText().toString().isEmpty()||isDeleted){
            dateEditText.setText("");
            switchCoronaTest.setEnabled(false);
            getContext().openFileInput(FILENAME);

        }

        if(editText1.getText().toString().isEmpty()||isDeleted){
            editText1.setText("");
            switchCoronaTest.setEnabled(false);
            getContext().openFileInput(FILENAME);
        }

            try {
                Date sDate;
                if(isDeleted){
                    sDate = (sdf.parse(""));

                }
                else {

                    sDate = sdf.parse(data[0]);
                }
                myCalendar.setTime(sDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            updateLabel();
            updateEndQuarantineTime();
            boolean isChecked = data[2] == "true" ? true : false;
            switchCoronaTest.setChecked(isChecked);
            addDays();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!isDeleted)
            countriesViewModel.saveDataToFile();

        countriesViewModel.saveClearButtonState();
        countriesViewModel.saveHeaderText();
        countriesViewModel.saveClearBtnVisibility();
        countriesViewModel.saveCallToReportBtnVisibility();
        countriesViewModel.saveReportOnSiteBtnVisibility();
        countriesViewModel.saveSwitchVisibility();
        System.out.println("destroy!!");
    }
}
