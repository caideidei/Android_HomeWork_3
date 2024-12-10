package com.example.t3;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int selectedPosition = -1; // 记录选中项的位置
    private ListView listView;
    private Button showNameButton;
    private View t3_1View; // t3_1的布局
    private TextView testContent; // t3_3中的测试文本
    private TextView selectedCountTextView; // 用于显示选中数量
    private int selectedCount = 0; // 记录选中项数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置Toolbar
        setContentView(R.layout.t3_4);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 判断是哪个布局
        t3_1View = findViewById(R.id.t3_1_layout);
        if (t3_1View != null) {
            setupT3_1();
        } else if (findViewById(R.id.test_content) != null) {
            setupT3_3();
        } else if (findViewById(R.id.item_list) != null) {
            setupT3_4();
        } else {
            setupT3_2();
        }
    }

    // t3_1 的初始化逻辑
    private void setupT3_1() {
        // 准备数据
        String[] animalNames = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
        int[] animalImages = {R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
                R.drawable.dog, R.drawable.cat, R.drawable.elephant};

        // 创建数据源
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < animalNames.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", animalNames[i]);
            item.put("image", animalImages[i]);
            data.add(item);
        }

        // 创建SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.list_item,
                new String[]{"name", "image"},
                new int[]{R.id.animal_name, R.id.animal_image}
        );

        // 绑定ListView
        listView = findViewById(R.id.animal_list);
        listView.setAdapter(adapter);

        showNameButton = findViewById(R.id.show_name_button);

        // 设置 ListView 项目的点击监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 更新选中项
                selectedPosition = position;
                showNameButton.setText(animalNames[position]);

                // 更新 ListView 行的背景色
                for (int i = 0; i < listView.getChildCount(); i++) {
                    View item = listView.getChildAt(i);
                    item.setBackgroundColor(i == position ? Color.RED : Color.WHITE);
                }
            }
        });
    }

    // t3_2 的初始化逻辑
    private void setupT3_2() {
        // 这里可以添加 t3_2 的初始化逻辑
    }

    // t3_3 的初始化逻辑
    private void setupT3_3() {
        testContent = findViewById(R.id.test_content); // 查找测试内容的TextView
    }


    // t4_4 的初始化逻辑
    private void setupT3_4() {
        // 准备数据
        String[] itemNames = {"One", "Two", "Three", "Four", "Five"};
        int[] itemImages = {R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android,
                R.drawable.ic_android, R.drawable.ic_android};

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < itemNames.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", itemNames[i]);
            item.put("image", itemImages[i]);
            data.add(item);
        }

        // 创建SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.list_item,
                new String[]{"name", "image"},
                new int[]{R.id.animal_name, R.id.animal_image}
        );

        listView = findViewById(R.id.item_list);
        listView.setAdapter(adapter);

        selectedCountTextView = findViewById(R.id.selected_count_text_view);
        selectedCountTextView.setText("0 selected"); // 初始化显示

        // 设置多选模式
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE); // 改为单选模式
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 更新选中项
                for (int i = 0; i < listView.getChildCount(); i++) {
                    View item = listView.getChildAt(i);
                    item.setBackgroundColor(i == position ? Color.BLUE : Color.WHITE);
                }
                selectedCount = 1; // 因为是单选，所以选中项数为1
                selectedCountTextView.setText(selectedCount + " selected");
            }
        });
    }


    // 设置菜单选项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.font_size) {
            // 显示字体大小选择
            showFontSizeDialog();
            return true;
        } else if (id == R.id.common_menu_item) {
            // 显示普通菜单项的提示
            Toast.makeText(this, "普通菜单项点击", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.font_color) {
            // 显示字体颜色选择
            showFontColorDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 模拟字体大小选择对话框
    private void showFontSizeDialog() {
        final String[] fontSizes = {"10sp", "16sp", "20sp"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择字体大小")
                .setItems(fontSizes, (dialog, which) -> {
                    if (testContent != null) {
                        switch (which) {
                            case 0:
                                testContent.setTextSize(10);
                                break;
                            case 1:
                                testContent.setTextSize(16);
                                break;
                            case 2:
                                testContent.setTextSize(20);
                                break;
                        }
                    }
                });
        builder.show();
    }

    // 模拟字体颜色选择对话框
    private void showFontColorDialog() {
        final String[] fontColors = {"红色", "蓝色", "黑色"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择字体颜色")
                .setItems(fontColors, (dialog, which) -> {
                    if (testContent != null) {
                        switch (which) {
                            case 0:
                                testContent.setTextColor(Color.RED);
                                break;
                            case 1:
                                testContent.setTextColor(Color.BLUE);
                                break;
                            case 2:
                                testContent.setTextColor(Color.BLACK);
                                break;
                        }
                    }
                });
        builder.show();
    }
}
