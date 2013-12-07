package me.lupino;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.Random;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

public class GuessNumber extends Activity
{
    private EditText inputNumber;
    private Button btn;
    private TableLayout l_result;
    private int count;
    private int count_A;
    private int count_B;
    private Random RNG;
    private int rnd_num[];
    private int input_num[];
    private TextView v_msg;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        inputNumber = (EditText) findViewById(R.id.inputNumber);
        v_msg = (TextView) findViewById(R.id.msg);
        l_result = (TableLayout) findViewById(R.id.result);
        btn = (Button) findViewById(R.id.btn);
        start();
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String num = inputNumber.getText().toString();
                inputNumber.setText("");
                String msg;
                if (check_num(num)) {
                    set_result(num, Integer.toString(count_A) + "A" + Integer.toString(count_B) + "B");
                    if (count_A == 4) {
                        msg = "";
                        alert("恭喜你答对了\n正确答案是：" + num);
                    } else if (count < 8) {
                        msg = "No. " + Integer.toString(count) + " " + num + " " + Integer.toString(count_A) + "A" + Integer.toString(count_B) + "B";
                    } else {
                        msg = "很不幸你答错了.\n正确答案是: ";
                        if(rnd_num[0] == 0)
                            msg += "0";
                        msg += Integer.toString(rnd_num[0] * 1000 + rnd_num[1] * 100 + rnd_num[2] * 10 + rnd_num[3]);

                        alert(msg);
                        msg = "";
                    }
                } else {
                    msg = "No. " + Integer.toString(count) + " Error";
                }
                v_msg.setText(msg);
            }
        });
    }

    private boolean check_num(String num) {
        if(num.length() != 4)
            return false;
        boolean flag = false;
        count_A = 0;
        count_B = 0;
        input_num = str2int(num);
        for(int i = 0; i <= 3; i++)
        {
            for(int k = i + 1; k <= 3; k++)
                if(input_num[i] == input_num[k])
                    return false;
        }

        for(int j = 0; j <= 3; j++)
        {
            for(int l = 0; l <= 3; l++)
                if(rnd_num[l] == input_num[j])
                    count_B++;

            if(rnd_num[j] == input_num[j])
            {
                count_A++;
                count_B--;
            }
        }

        return true;
    }

    private void set_result(String num, String result) {
        TableRow row = new TableRow(this);
        row.setId(0);

        count ++;
        TextView v_count = new TextView(this);
        v_count.setText(Integer.toString(count));
        v_count.setPadding(3, 3, 3, 3);

        TextView v_num = new TextView(this);
        v_num.setText(num);
        v_num.setPadding(3, 3, 3, 3);
        v_num.setGravity(Gravity.CENTER);

        TextView v_result = new TextView(this);
        v_result.setText(result);
        v_result.setPadding(3, 3, 3, 3);
        v_result.setGravity(Gravity.RIGHT);

        row.addView(v_count);
        row.addView(v_num);
        row.addView(v_result);

        l_result.addView(row);
    }

    public int[] str2int(String s_num)
    {
        int num[] = new int[4];
        int raw_num = Integer.parseInt(s_num);
        num[0] = raw_num / 1000;
        raw_num -= num[0] * 1000;
        num[1] = raw_num / 100;
        raw_num -= num[1] * 100;
        num[2] = raw_num / 10;
        raw_num -= num[2] * 10;
        num[3] = raw_num;
        return num;
    }

    public int[] rnd()
    {
        int num[] = new int[4];
        boolean flag;
        RNG = new Random();
        while(true)
        {
            flag = false;
            num[0] = Math.abs(RNG.nextInt(10));
            num[1] = Math.abs(RNG.nextInt(10));
            num[2] = Math.abs(RNG.nextInt(10));
            num[3] = Math.abs(RNG.nextInt(10));
            for(int i = 0; i <= 3; i++)
            {
                for(int j = i + 1; j <= 3; j++)
                    if(num[i] == num[j])
                        flag = true;

            }

            if(!flag)
                break;
        }
        return num;
    }

    public void start()
    {
        rnd_num = rnd();
        count = 0;
        l_result.removeAllViews();
    }

    public void alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle("提示");
        builder.setPositiveButton("开局", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                start();
            }
        });
        builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
