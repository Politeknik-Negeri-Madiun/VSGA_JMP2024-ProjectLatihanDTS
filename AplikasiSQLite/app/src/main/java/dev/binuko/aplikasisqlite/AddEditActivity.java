package dev.binuko.aplikasisqlite;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import dev.binuko.aplikasisqlite.helper.DBHelper;

public class AddEditActivity extends AppCompatActivity {
    EditText txtId, txtName, txtAddress;
    Button btnSubmit, btnCancel;
    DBHelper SQLite = new DBHelper(this);
    String id, name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        txtId = findViewById(R.id.txt_id);
        txtName = findViewById(R.id.txt_name);
        txtAddress = findViewById(R.id.txt_address);
        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);

        if (id == null || id.equals("")) {
            setTitle("Add Data");
        } else {
            setTitle("Edit Data");
            txtId.setText(id);
            txtName.setText(name);
            txtAddress.setText(address);
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtId.getText().toString().equals("")) {
                    save();
                } else {
                    edit();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blank();
                finish();
            }
        });
    }

    private void blank() {
        txtName.requestFocus();
        txtId.setText(null);
        txtName.setText(null);
        txtAddress.setText(null);
    }

    private void edit() {
        if (String.valueOf(txtName.getText()).equals("") ||
                String.valueOf(txtAddress.getText()).equals("")) {
            Toast.makeText(this, "Please input name or address ...", Toast.LENGTH_SHORT).show();

        } else {
            SQLite.update(Integer.parseInt(txtId.getText().toString().trim()), txtName.getText().toString().trim(),
                    txtAddress.getText().toString().trim());
            blank();
            finish();
        }
    }

    private void save() {
        if (String.valueOf(txtName.getText()).equals("") ||
                String.valueOf(txtAddress.getText()).equals("")) {
            Toast.makeText(this, "Please input name or address ...", Toast.LENGTH_SHORT).show();

        } else {
            SQLite.insert(txtName.getText().toString().trim(), txtAddress.getText().toString().trim());
            blank();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}