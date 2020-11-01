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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Arrays;
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

    ArrayList<String> selectedIds = new ArrayList<String>();
    public static final String DATE_DIALOG_1 = "datePicker1";
    static TextView txt_start;
    private static String selectedStartDate = "";
    private static String selectedEndDate = "";
    public Integer taskId;
    private static int mYearStart;
    private static int mMonthStart;
    private static int mDayStart;
    public static final String DATE_DIALOG_2 = "datePicker2";
    static TextView txt_end;
    private static int mYearEnd;
    private static int mMonthEnd;
    private static int mDayEnd;
    String impid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        final Executor ex = new Executor();
        String emp[] = {ex.FullName};
        saveBtn = findViewById(R.id.saveButton);
        final EditText title = (EditText) findViewById(R.id.task);
        final TextView employeeList = findViewById(R.id.employeeList);
        final EditText taskDescription = (EditText) findViewById(R.id.taskDescription);
        final MultiAutoCompleteTextView multi = (MultiAutoCompleteTextView) findViewById(R.id.multi);
//        final Button saveExecutor =(Button)  findViewById(R.id.saveExecutor);
        txt_start = findViewById(R.id.txt_date1);
        txt_start.setText(dateString);
        selectedStartDate = dateString;
        txt_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment newFragment1 = new DatePickerFragment1();
                //  newFragment1.show(getSupportFragmentManager(), DATE_DIALOG_1);
                newFragment1.show(getFragmentManager(), DATE_DIALOG_1);
            }
        });

        txt_end = findViewById(R.id.txt_date2);
        txt_end.setText(dateString);
        selectedEndDate = dateString;
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
                if (true) {
                    new GetDataAsync(task.this, multi, employeeList, impid, selectedIds).execute();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("letter", s.toString());
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
                String[] endDateArray = selectedEndDate.split("-");
                String[] startDateArray = selectedStartDate.split("-");
                int size = endDateArray.length;
                int[] startDateArrayInt = new int[size];
                int[] endDateArrayInt = new int[size];
                for (int i = 0; i < 3; i++) {
                    startDateArrayInt[i] = Integer.parseInt(startDateArray[i]);
                    endDateArrayInt[i] = Integer.parseInt(endDateArray[i]);
                }

                Log.i("dateee", Arrays.toString(startDateArray));
                if (task.title.trim().equals("")) {
                    Toast toast = Toast.makeText(task.this, "შეიყვანეთ სათაური", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.START, 450, -400);
                    toast.show();
                } else if (task.description.trim().equals("")) {
                    Toast toast = Toast.makeText(task.this, "შეიყვანეთ აღწერა", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.START, 450, -400);
                    toast.show();
                } else if (startDateArrayInt[0] > endDateArrayInt[0] || startDateArrayInt[1] > endDateArrayInt[1] || startDateArrayInt[2] > endDateArrayInt[2]) {
                    Toast toast = Toast.makeText(task.this, "საწყისი თარიღი არ უნდა აღემატებოდეს საბოლოო თარიღს", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.START, 450, -400);
                    toast.show();
                } else {
                    try {
                        taskId = new AddNewTaskAsync(task.this, selectedIds).execute(task).get();
                        Log.i("taskId", taskId.toString());

                        for (String id : selectedIds) {
                            Executor executor = new Executor();

                            executor.TaskId = taskId.toString();
                            executor.EmpId = id;

                            new AddExecutorAsync(task.this).execute(executor);

                            Intent intent = new Intent(task.this, MainActivity.class);
                        }
                    } catch (ExecutionException | InterruptedException ex) {
                        ex.printStackTrace();
                    }

                }
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
            selectedStartDate = Integer.toString(mYearStart) + "-" + decimalFormat.format(month + 1) + "-" + decimalFormat.format(day);


            txt_start.setText(new StringBuilder()
                    .append(mYearStart).append("-")
                    .append(mMonthStart).append("-")
                    .append(mDayStart).append(" "));
        }
    }

    public static class DatePickerFragment2 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

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
            selectedEndDate = Integer.toString(mYearEnd) + "-" + decimalFormat.format(month + 1) + "-" + decimalFormat.format(day);

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