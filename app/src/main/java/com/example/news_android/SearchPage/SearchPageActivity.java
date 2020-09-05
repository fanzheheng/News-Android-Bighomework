package com.example.news_android.SearchPage;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.news_android.NewsList.NewsListAdapter;
import com.example.news_android.R;

public class SearchPageActivity extends AppCompatActivity {
    RecyclerView resultListView;
    Button searchButton;
    EditText editText;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        //Get views
        resultListView = findViewById(R.id.search_result_list);
        searchButton = findViewById(R.id.search_button);
        editText = findViewById(R.id.search_bar_edit);
        radioGroup = findViewById(R.id.search_type_radios);

        //Search
        resultListView.setLayoutManager(new LinearLayoutManager(this));
        resultListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editText.getText().toString();
                int searchTypeId = radioGroup.getCheckedRadioButtonId();
                RecyclerView.Adapter adapter = null;
                if(searchTypeId == R.id.news_radio) {
                    adapter = new NewsListAdapter(new String[]{"aa", "bb", "cc"});
                    //search news
                } else if (searchTypeId == R.id.entity_radio) {
                    //search entities
                }
                resultListView.setAdapter(adapter);
                if(adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}