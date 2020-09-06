package com.example.news_android.SearchPage;

import android.content.Context;
import android.transition.Fade;
import android.transition.Transition;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.news_android.DataBase.Entity;
import com.example.news_android.DataBase.EntityRepo;
import com.example.news_android.DataBase.News;
import com.example.news_android.DataBase.NewsRepo;
import com.example.news_android.JsonGetter;
import com.example.news_android.NewsList.EntityListAdapter;
import com.example.news_android.NewsList.NewsListAdapter;
import com.example.news_android.R;
import com.example.news_android.Utils;

import java.util.ArrayList;

public class SearchPageActivity extends AppCompatActivity {
    RecyclerView resultListView;
    Button searchButton;
    EditText editText;
    RadioGroup radioGroup;
    RecyclerView.Adapter adapter;
    RelativeLayout searchFailedHintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        //Get views
        resultListView = findViewById(R.id.search_result_list);
        searchButton = findViewById(R.id.search_button);
        editText = findViewById(R.id.search_bar_edit);
        radioGroup = findViewById(R.id.search_type_radios);
        searchFailedHintView = findViewById(R.id.search_failed_hint);

        //Search
        resultListView.setLayoutManager(new LinearLayoutManager(this));
        resultListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = editText.getText().toString();
                    int searchTypeId = radioGroup.getCheckedRadioButtonId();
                    if(searchTypeId == R.id.news_radio) {
                        //search news
                        searchNews(editText.getText().toString());
                    } else if (searchTypeId == R.id.entity_radio) {
                        //search entities
                        searchEntities(editText.getText().toString());
                    }
                    System.out.println(adapter.getItemCount());
                    resultListView.setAdapter(adapter);
                    if(adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    InputMethodManager imm =(InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        //Cancel
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }

    private void searchNews(String searchText) {
        NewsRepo newsRepo = new NewsRepo(this);
        ArrayList<News> searchResult = newsRepo.getNewsBySearchInput(searchText);

        adapter=new NewsListAdapter(searchResult);
        updateSearchResult();
        return;
    }

    private void searchEntities(final String searchText) {
        final EntityRepo entityRepo = new EntityRepo(this);
        adapter = new EntityListAdapter(new ArrayList<Entity>());
        JsonGetter.JsonGetterFinishListener listener=new JsonGetter.JsonGetterFinishListener()
        {
            @Override
            public void OnFinish()
            {
                ArrayList<Entity> searchResult = entityRepo.getEntityBySearchInput(searchText);
                ((EntityListAdapter)adapter).setEntities(searchResult);
                updateSearchResult();
            }
        };
        Utils.UpdateEntityDatabase(this, searchText, listener);
    }
    private void updateSearchResult() {
        if(adapter == null || adapter.getItemCount() == 0) {
            resultListView.setVisibility(View.GONE);
            searchFailedHintView.setVisibility(View.VISIBLE);
        } else {
            if(resultListView.getVisibility() != View.VISIBLE) {
                resultListView.setVisibility(View.VISIBLE);
                searchFailedHintView.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        }
    }
}

