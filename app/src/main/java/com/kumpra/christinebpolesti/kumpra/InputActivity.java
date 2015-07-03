package com.kumpra.christinebpolesti.kumpra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class InputActivity extends Activity implements InputView, AdapterView.OnItemClickListener {

    @Bind(R.id.edtItem)
    EditText mEdtItem;

    @Bind(R.id.edtQty)
    EditText mEdtQuantity;

    @Bind(R.id.list_item)
    ListView mListItem;

    @Bind(R.id.btnAddItem)
    Button mBtnAddItem;

    private InputPresenter presenter;

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mListItem.setOnItemClickListener(this);
        presenter = new InputPresenterImpl(this);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        mListItem.setAdapter(mAdapter);
    }

    @OnClick(R.id.btnAddItem)
    public void AddItem() {
        String item = mEdtItem.getText().toString();

        if(!TextUtils.isEmpty(item)) {
            mAdapter.add(item);
            mAdapter.notifyDataSetChanged();
            mEdtItem.setText("");
        } else {
            Toast.makeText(this, "Input an item to add", Toast.LENGTH_SHORT).show();
        }
    }

    @OnItemClick(R.id.list_item)
    public void SelectItemInList(int postion) {
        String item = mAdapter.getItem(postion);
        Toast.makeText(this, "Item Selected: "+item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setItemError() {
        mEdtItem.setError("Please input an item.");
    }

    @Override
    public void setQtyError() {
        mEdtQuantity.setError("Please a quantity.");
    }

    @Override
    public void navigateToHistory() {
        startActivity(new Intent(this, InputActivity.class));
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}