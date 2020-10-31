package com.example.test1234;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.example.test1234.Services.AddExecutorAsync;
import com.example.test1234.Services.AddNewTaskAsync;
import com.example.test1234.Services.DeleteTaskAsync;
import com.example.test1234.Services.GetDataAsync;
import com.example.test1234.models.Comment;
import com.example.test1234.models.Executor;
import com.example.test1234.models.Task;
import com.example.test1234.models.TaskPost;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class task extends Activity {
    Button saveBtn;
    long date = System.currentTimeMillis();


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = sdf.format(date);

    public static final String DATE_DIALOG_1 = "datePicker1";
    static TextView txt_start;
    private static String selectedStartDate = "";
    private static String selectedEndDate = "";
    public  Integer taskId;
    private static int mYearStart;
    private static int mMonthStart;
    private static int mDayStart;
    public static final String DATE_DIALOG_2 = "datePicker2";
    static TextView txt_end;
    private static int mYearEnd;
    private static int mMonthEnd;
    private static int mDayEnd;
//    private static int mDayEnd;

    //private String[]  executor = {"nato","ნატო1",  "khatia"};
//
//    public task(int response) {
//        this.res = response;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        final Executor ex = new Executor();
        String emp[] = {ex.FullName};
        saveBtn = findViewById(R.id.saveButton);
        final EditText title = (EditText) findViewById(R.id.task);
        final EditText taskDescription = (EditText) findViewById(R.id.taskDescription);
        final MultiAutoCompleteTextView multi = (MultiAutoCompleteTextView) findViewById(R.id.multi);
        final Button saveExecutor =(Button)  findViewById(R.id.saveExecutor);
        //final EditText comments = (EditText)findViewById(R.id.comments);
        txt_start = findViewById(R.id.txt_date1);
        txt_start.setText(dateString);
        txt_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment newFragment1 = new DatePickerFragment1();
                //  newFragment1.show(getSupportFragmentManager(), DATE_DIALOG_1);
                newFragment1.show(getFragmentManager(), DATE_DIALOG_1);
            }
        });

        txt_end = findViewById(R.id.txt_date2);

        txt_end.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment newFragment2 = new DatePickerFragment2();
                newFragment2.show(getFragmentManager(), DATE_DIALOG_2);
            }
        });
        multi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("letter", s.toString());

                if (true) {
                    new GetDataAsync(task.this, multi).execute();
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Executor executor= new Executor();
                TaskPost task = new TaskPost();
                task.title = title.getText().toString();
                task.description = taskDescription.getText().toString();
                task.startDate = selectedStartDate;
                task.endDate = selectedEndDate;

                try {
                   taskId = new AddNewTaskAsync(task.this).execute(task).get();
                   Log.i("taskId", taskId.toString());

                } catch (ExecutionException | InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        });
        saveExecutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executor executor = new Executor();

                executor.TaskId = taskId;
                executor.EmpId = 10423;

                new AddExecutorAsync(task.this).execute(executor);
            }
        });
    }

    public static class DatePickerFragment1 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            //Date Time NOW
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), R.style.DatePicker1, this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // get selected date
            mYearStart = year;
            mMonthStart = month + 1;
            mDayStart = day;
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            int hour = calendar.get(Calendar.HOUR_OF_DAY) + 4;
            int minutes = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);
            int millSeconds = calendar.get(Calendar.MILLISECOND);
            DecimalFormat decimalFormat = new DecimalFormat("00");
            String transformedSeconds = decimalFormat.format(seconds);
            String transformedMinutes = decimalFormat.format(minutes);
            String time = Integer.toString(hour) + ":" + Integer.toString(minutes) + ":" + transformedSeconds + "." + Integer.toString(millSeconds);
           // selectedStartDate = Integer.toString(mYearStart) + "-" + Integer.toString(mMonthStart) + "-" + Integer.toString(mDayStart) + "T" + time;
            selectedStartDate = Integer.toString(mYearStart) + "-" + decimalFormat.format(month+1) + "-" + decimalFormat.format(day);


            txt_start.setText(new StringBuilder()
                    .append(mYearStart).append("-")
                    .append(mMonthStart).append("-")
                    .append(mDayStart).append(" "));
            Log.i("date", txt_start.toString());
        }
    }

    public static class DatePickerFragment2 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // set default date


            //Date Time FROM BEFORE
            String date = txt_end.getText().toString().trim();
            String[] data = date.split("-", 3);
            int year = Integer.parseInt(data[0]);
            int month = Integer.parseInt(data[1]) - 1;    //Because January is 0
            int day = Integer.parseInt(data[2]);
            return new DatePickerDialog(getActivity(), R.style.DatePicker2, this, year, month, day);
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {

            // get selected date
            mYearEnd = year;
            mMonthEnd = month + 1;
            mDayEnd = day;
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            int hour = calendar.get(Calendar.HOUR_OF_DAY) + 4;
            int minutes = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);
            int millSeconds = calendar.get(Calendar.MILLISECOND);
            DecimalFormat decimalFormat = new DecimalFormat("00");
            String transformedMinutes = decimalFormat.format(minutes);
            String transformedSeconds = decimalFormat.format(seconds);
            Log.i("123456", Integer.toString(seconds));
//            int millSeconds = Calendar.MILLISECOND;
            String time = Integer.toString(hour) + ":" + transformedMinutes + ":" + transformedSeconds + "." + Integer.toString(millSeconds);
           // selectedEndDate = Integer.toString(mYearEnd) + "-" + Integer.toString(mMonthEnd) + "-" + Integer.toString(mDayEnd) + "T" + time;
            selectedEndDate = Integer.toString(mYearEnd) + "-" + decimalFormat.format(month+1) + "-" + decimalFormat.format(day);

            txt_end.setText(new StringBuilder()
                    .append(mYearEnd).append("-")
                    .append(mMonthEnd).append("-")
                    .append(mDayEnd).append(" "));

            System.out.println(txt_end);
            Log.i("date1", txt_end.toString());
        }
//        new AddNewTaskAsync(this).execute(task);


    }
}