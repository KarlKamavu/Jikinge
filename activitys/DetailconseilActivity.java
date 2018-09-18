package com.roonit.jikinge.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.roonit.jikinge.R;
import com.roonit.jikinge.utils.BasicActivity;


public class DetailconseilActivity extends BasicActivity {

    public static final String EXTRA_POSITION = "position";

    private TextView titre;
    private TextView contenu;
    String t;
    String c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailconseil);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Conseil");

       t =getIntent().getStringExtra("titre");
       c =getIntent().getStringExtra("contenu");

        titre=(TextView)findViewById(R.id.titre_detail);
        contenu=(TextView)findViewById(R.id.contenu_detail);

        titre.setText(t);
        contenu.setText(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.partage){

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, t+"\n" +c);
                    shareIntent.setType("text/plain");
                    startActivity(Intent.createChooser(shareIntent, "Partager via:"));


        }
        return super.onOptionsItemSelected(item);
    }
}
